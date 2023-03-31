# PROJET DHT - INFO833

*Projet DHT réalisé par BOUCHARD Gaëtan et PRUVOST Jordan*

---

## Table des matières
  - [Introduction](#introduction)
  - [Première partie](#partie-i---première-version)
  - [Deuxième partie](#partie-ii---version-finale)
  - [Utilisation](#utilisation)

---
## Introduction

Le but est de concevoir une simulation de DHT fonctionnelle en language java.
Nous avons décider d'utiliser Java car c'est un language spécialisé dans la programmation orienté objet et que la conception d'une DHT est plus optimisé en Java.

---
## Partie I - Première version

Pour la première version, nous avons commencé par concevoir un système de classes composées des classes suivantes : `MyNode`, `Ring`, `DHTSimulator` et `Main`.
De chacune de ces classes a été attribué le role suivant :
 - `MyNode` : Définie un noeud qui a un 'ID' de type 'long' car utile si l'on effectue de grosse simulation, un noeud voisin à gauche et un noeud voisin à droite, tout les deux de type 'MyNode'.
  
 - `Ring` : Définie un anneau qui a également un 'ID' de type 'int' car nous ne pensons pas créer une importante quantité d'anneau. Cette classe possède égelement une liste 'ArrayList' d'objet 'MyNode', rangé dans l'ordre croissant de leur 'ID'. Nous avons définie ensuite pour chacun, un voisin de gauche et un voisin de droite, respectivement le noeud situé avant et après dans la liste. La seule exeption vient lors du premier et du dernier noeud. Le premier ayant le dernier pour voisin de gauche, et le dernier ayant donc le premier pour voisin de droite. Des méthodes `addNode()` et `removeNode()` ont été implémenté pour ajouter et supprimer un noeud de l'anneau mais également redéfinir ses voisins.
  
 - `DHTSimulator` : Définie les paramètres de simulation de DHT à partir des classes `MyNode` et `Ring`, et d'un fichier de configuration.

 - `Main` : Définie la classe Main pour lancer le code.


Nous nous sommes rendu compte par la suite que ce n'était pas ce qui était demander. Ayant d'autant plus mal compris l'utilisation de `PeerSim`, cela nous a fait perdre un peu de temps, mais nous a malgré tout permis de mieux prendre en main le sujet.

---

## Partie II - Version finale

Le nouveau code fourni est une meilleure implémentation d'un algorithme de Distributed Hash Table (DHT) utilisant PeerSim. PeerSim étant un simulateur de réseau peer-to-peer à événements discrets.

Le code implémente les fonctionnalités de base d'un réseau DHT, notamment l'ajout de noeuds au réseau, la suppression de noeuds du réseau, le stockage de données dans le réseau et la recherche de données dans le réseau.

Chaque noeud dans le réseau est représenté par une instance de la classe `HelloWorld`. La classe `HelloWorld` implémente l'interface `EDProtocol`, qui permet au simulateur d'appeler la méthode `processEvent` de chaque noeud lorsque des événements se produisent dans le réseau.

Le réseau utilise un protocole de couche transport implémenté dans la classe `HWTransport`. La classe `HelloWorld` utilise l'instance de cette classe pour envoyer et recevoir des messages avec les noeuds voisins.

Le réseau DHT utilise une structure de liste chaînée circulaire pour stocker les noeuds. Chaque noeud connaît ses voisins immédiats (à gauche et à droite) dans la liste. L'ajout et la suppression de noeuds dans le réseau sont gérés en mettant à jour les liens entre les noeuds voisins.

Les données sont stockées dans une structure appelée `Container` qui est attachée à chaque noeud. Lorsqu'un noeud reçoit une demande de stockage de données, il vérifie si le noeud est le plus proche voisin de gauche ou de droite de la clé de hachage associée aux données. Si c'est le cas, il stocke les données localement et les transmet également à ses deux voisins immédiats. Sinon, il transmet la demande à son voisin le plus proche.

Lorsqu'un noeud reçoit une demande de recherche de données, il vérifie s'il possède les données localement. Si c'est le cas, il renvoie les données. Sinon, il transmet la demande à son voisin le plus proche, et ainsi de suite.

---
## Utilisation

Le projet comporte trois fichier : 'dependencies', 'final' et 'old'.
 - 'dependencies' stock les dépendance lié au projet (tel que PeerSim).
 - 'final' comporte la version finale du projet DHT expliqué dans la [Partie 2](#partie-ii---version-finale).
 - 'old' comporte la première version du projet DHT expliqué dans la [Partie 1](#partie-i---première-version).

Pour la première version, il suffit de lancer le fichier `Main.java`. Il est également possible de jouer sur les paramètres de `configuration.txt`

Pour la version finale, il faut lancer `peersim.Simulator`. Il est également ici possible de jouer sur les paramètres de `configuration.txt`. Il faudra au préalable définir et préciser le ficher de configuration avant le lancement. Sur intelliJ, il faut ajouter 'config_file.cfg' en plus.

**NB :** Il est important de redéfinir les dépendances du projet lors de l'ouverture de ce dernier ('old' ou 'final') pour le bon fonctionnement de ce dernier. Les modules à prendre en compte sont : `djep-1.0.0.jar`, `jep-2.3.0.jar` et `peersim-1.0.5`
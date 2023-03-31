package helloWorld;

import java.util.ArrayList;
public class Container {

    private ArrayList<Data> container;

    public Container(){
        this.container = new ArrayList<Data>();
    }

    public ArrayList<Data> getContainer(){
        return this.container;
    }

    public void SetContainer(ArrayList<Data> container){
        this.container = container ;
    }

    public void addData(Data data){
        this.getContainer().add(data);
    }

    public Data getData(int i){
        return this.getContainer().get(i);
    }
}

package helloWorld;

public class Data {

    private int Id;
    private String data;

    public  Data(int Id, String data){
        this.Id = Id;
        this.data = data;
    }

    public int getId(){
        return this.Id;
    }

    public String getData(){
        return this.data;
    }
}

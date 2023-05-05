package Trial;
public class MyObject extends Object {
    private String name;

    public MyObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printObject() {
        System.out.println()
    }

    @Override
    public String toString() {
        return name ;
    }
}
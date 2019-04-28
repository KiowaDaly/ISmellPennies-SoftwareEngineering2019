
public class testdead {
    int five = 5;

    public  void emptyMeth(){

    }

    public int nothing(){
        return 5;
    }

    public int nothing2(){
        return 5;
    }

    public void something(){
        System.out.println("SOMETHING");
    }
    public static void main(String[] argc) throws Exception {
        String unused = "unused";

        test4 dead = new test4();
        test3 t3 = new test3();

        t3.getRed();
        dead.something();
        dead.something();
        dead.something();

        String text = "ezpzswagmaster";

        if(text.contains("ez")){
            System.out.println("contains ez");
        }

        System.out.println("printing method");
    }


}

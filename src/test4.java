
public class test4 {
    int four = 4;
    int five = 5;
    public int nothing(){
        return 5;
    }

    public int nothing2(){
        return 5;
    }

    public int getCinco(){
        return five;
    }

    public int getCuatro(){
        return four;
    }



    public void something(){
        System.out.println("SOMETHING");
    }
    public static void main(String[] argc) throws Exception {
        String unused = "unused_string";
        String used = "used_string";

        test4 dead = new test4();

        dead.something();
        dead.something();
        dead.something();

        String text = "ezpzswagmaster";

        if(text.contains("ez")){
            System.out.println("contains ez");
        }
    }
}

public class testDataonly {
    private String name;
    private Integer num;
    private Integer stillNum;
    private String unused;

    // Getter
    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public int getNothing(){

    return 0;
    }

    public int stillGetter(){

        return stillNum;
    }

    // Setter
    public void setName(String newName) {
        this.name = newName;
    }

    public void setNum(int newNum) {
        this.num = newNum;
    }

    public void stillSetter(int newStillNum){
        this.stillNum = newStillNum;
    }

    public void emptyMethod(){
     // System.out.println("HAHA NOT A DATA CLASS");
    }

    public String someString(){
        return "string of something";
    }

    public int returnNumSum(){

        return getNum()+getNum();
    }

    public void variables(){
        int three = 4;
        int six = three+three;
    }

}

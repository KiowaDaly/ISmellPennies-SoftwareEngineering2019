public interface refusedBequest{
    
    private int ignoredBehaviours;
    private int totalBehaviours;
    private boolean smell; //at construction of object do 
    //if total - ignored != 0; smell.
    //constructor should just 
    public boolean isSmell();
    private boolean isIgnored(MethodDeclaration subMethod, MethodDeclaration parentMethod);
    //go through the list of methods.
    private iterateThroughClass(ClassOrInterfaceDeclaration clase);
    //get the parent of the clase. used for iterateThroughClass
    private ClassOrInterfaceDeclaration getParentOfClass(ClassOrInterfaceDeclaration clase);
}
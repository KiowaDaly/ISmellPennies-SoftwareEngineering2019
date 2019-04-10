package ExcessiveCoupling;

public interface ExcessiveCoupling<T, U> {
    //FEATURE ENVY
    //  first thing to check for would be the number of times , inside a class, that instances
    //  of other classes are instantiated, however this alone is not enough...
    //  we then need to check how many times, from each class instance, a method from one
    //  of these separate classes is called.

    //  i.e. the RATIO of an instance class object to its method call(s). i.e. 1:some number of method calls
    //  a LARGE/WIDE ratio perhaps indicates feature envy.

    T getNumClassInstances(U u);
    T getNumMethodCalls(U u);


}

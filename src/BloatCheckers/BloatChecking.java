package BloatCheckers;





public interface BloatChecking<T,U>{

    T getNumLines(U md);
    T getNumComments(U md);
    T getNumFieldsOrVariables(U md);
    T[] getIDLengths(U md);


}

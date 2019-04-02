package BloatCheckers;





public interface BloatChecking<T,U>{

    T getNumLines(U md);
    T getNumComments(U md);
    T getNumFields(U md);
    T[] getIDLengths(U md);
    T getNumParameters(U md);
    T getNumMethods(U md);

}

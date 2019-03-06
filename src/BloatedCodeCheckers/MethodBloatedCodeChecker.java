package BloatedCodeCheckers;

import java.lang.reflect.Method;

public class MethodBloatedCodeChecker extends BloatedCodeChecker {

    protected Method method;
    private final int FIELD_THRESHOLD = 6;
    private final int PARAMETER_THRESHOLD = 4;

    public MethodBloatedCodeChecker(Method m){
        this.method = m;
    }

    public boolean methodHasTooManyParameters(){
        return method.getParameterCount() > PARAMETER_THRESHOLD;
    }

}

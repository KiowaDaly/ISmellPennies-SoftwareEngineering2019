package BloatCheckers;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import utility_classes.ThreatLevel;

import java.util.HashMap;
import java.util.List;

public class BloatedCodeAbuseCheck {

    private final double COMMENT_THRESHOLD = 1;
    private final int VARIABLE_THRESHOLD = 20;
    private ClassBloatChecks check_bloat = new ClassBloatChecks();
    private MethodBloatChecks method_bloat = new MethodBloatChecks();
    private List<ClassOrInterfaceDeclaration> classes;
    private HashMap<ClassOrInterfaceDeclaration,HashMap> classThreats = new HashMap<>();
    private HashMap<MethodDeclaration,HashMap> MethodThreats = new HashMap<>();

    public BloatedCodeAbuseCheck(List<ClassOrInterfaceDeclaration> classes){
            this.classes = classes;
    }

    public void performBloatChecks(){
        for(ClassOrInterfaceDeclaration cl:classes){
            classThreats.put(cl,CheckClass(cl));
        }
    }

    private HashMap CheckClass(ClassOrInterfaceDeclaration n){
            int threatValue = 0;
            int classLength = check_bloat.getNumLines(n);
            int CLASS_LENGTH_THRESHOLD = 200;
            threatValue += (classLength > CLASS_LENGTH_THRESHOLD)?1:0;
            if(classLength > 0)threatValue += ((classLength-check_bloat.getNumComments(n))/classLength < COMMENT_THRESHOLD) ? 1:0;
            threatValue += (check_bloat.getNumFieldsOrVariables(n) > VARIABLE_THRESHOLD) ? 1:0;
            int METHOD_THRESHOLD = 15;
            threatValue += (check_bloat.getNumMethods(n) > METHOD_THRESHOLD) ? 1:0;
            for (MethodDeclaration m:n.getMethods()) {
                MethodThreats.put(m,CheckMethod(m));
            }

            HashMap<ThreatLevel,Integer> threatPercentageHelper = new HashMap<>();
            int temp = threatValue;
            threatValue = (threatValue > 3) ? 3:threatValue;
            threatPercentageHelper.put(ThreatLevel.values()[threatValue],temp);
            return threatPercentageHelper;
    }

    private HashMap CheckMethod(MethodDeclaration m){
            int threatValue  = 0;
            int methodLines = method_bloat.getNumLines(m);
            int METHOD_LINE_THRESHOLD = 30;
            threatValue += (methodLines > METHOD_LINE_THRESHOLD) ? 1:0;
            threatValue += ((methodLines - method_bloat.getNumComments(m))/100 > COMMENT_THRESHOLD) ? 1:0;
            int PARAMETER_THRESHOLD = 5;
            threatValue += (method_bloat.getNumParameters(m) > PARAMETER_THRESHOLD) ? 1:0;
            threatValue += (method_bloat.getNumFieldsOrVariables(m) > VARIABLE_THRESHOLD) ? 1:0;
            HashMap<ThreatLevel,Integer> threatPercentageHelper = new HashMap<>();
            int temp = threatValue;
            threatValue = (threatValue > 3) ? 3:threatValue;
            threatPercentageHelper.put(ThreatLevel.values()[threatValue],temp);
        return threatPercentageHelper;
    }

    public HashMap getMethodThreats(){
            return MethodThreats;
    }
    public HashMap getClassThreats(){
            return classThreats;
    }

}

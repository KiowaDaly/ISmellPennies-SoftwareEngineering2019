package Lazies_Freeloader_walkingdead;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import java.util.ArrayList;
import java.util.List;

public class WalkingDeadChecks {

    public boolean isDataOnlyClass(ClassOrInterfaceDeclaration cl) {
        List<MethodDeclaration> nonDataMethods = new ArrayList<>();
        for (FieldDeclaration f : cl.getFields()) {
            for (MethodDeclaration md : cl.getMethods()) {
                for (VariableDeclarator v : f.getVariables()) {
                    if ((md.getNameAsString().toLowerCase().contains(("get"))
                            || (md.getNameAsString().toLowerCase().contains("set")))) {
                        continue;
                    } else nonDataMethods.add(md);
                }
            }
        }
        return nonDataMethods.size() == 0;

    }
}

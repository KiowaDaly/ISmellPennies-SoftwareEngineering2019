import BloatCheckers.ClassBloatChecks;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.junit.Test;
import utility_classes.CompilationUnitVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassBloatTests {
    File myfile = new File("C:/Users/kiowa/Desktop/SOFTWARE ENGINEERING/src/test.java");
    CompilationUnit cu = StaticJavaParser.parse(myfile);

    public ClassBloatTests() throws FileNotFoundException {
    }

    @Test
    public void GetLengthTest(){
        ClassBloatChecks cl = new ClassBloatChecks();
        List<ClassOrInterfaceDeclaration> list = new ArrayList<>();
        CompilationUnitVisitor myVisitor = new CompilationUnitVisitor();
       // list = myVisitor.visit(cu,list);

        assertEquals("there are two user defined methods",2.0,(double) cl.getNumMethods(list.get(0)),0.01);
    }
}

package parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.javaparser.Providers.provider;

public class JavaFileParser {
    private String classRegExp = "(((|public|final|abstract|private|static|protected)(\\s+))?(class)(\\s+)(\\w+)(<.*>)?(\\s+extends\\s+\\w+)?(<.*>)?(\\s+implements\\s+)?(.*)?(<.*>)?(\\s*))\\{$";
    private JavaParser ourParser = new JavaParser();

    public List parseFileToClassObject(File file) throws IOException {
        Class ParsedClass = null;
        CompilationUnit Cu = StaticJavaParser.parse(file);
        List<Class> myList = new ArrayList<>();
        Cu.findAll(ClassOrInterfaceDeclaration.class).stream().filter(c->!c.isInterface()&&!c.isAbstract()).forEach(c->{myList.add();});

        return myList;
   }
}

package Project_FileAnalyser;
import BloatCheckers.BloatedCodeAbuseCheck;
import ExcessiveCoupling.ExcessiveCouplingChecks;
import GodComplexes.GodClassCheck;
import Lazies_Freeloader_walkingdead.WalkingDeadChecks;
import ObjectOrientedAbusers.SwitchChecker;
import ObjectOrientedAbusers.TemporaryFields;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import utility_classes.ClassThreatLevels;
import utility_classes.CompilationUnitVisitor;
import utility_classes.ThreatLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
//testing it worked
public class SmellDetectorCalls {
    private static SmellDetectorCalls INSTANCE = null;
    //store the class and its corresponding threat levels in a hashmap
    private HashMap<ClassOrInterfaceDeclaration, ClassThreatLevels> detections = new HashMap<>();
    HashMap<ClassOrInterfaceDeclaration, HashMap> map;
    HashMap<ClassOrInterfaceDeclaration, HashMap> methodThreats;
    private List<CompilationUnit> units;
    int NumFiles;

    private SmellDetectorCalls() {

    }

    public static SmellDetectorCalls getInstance(){
        if(INSTANCE == null){
            throw new AssertionError("Initialize class first");
        }
        return INSTANCE;
    }

    public synchronized static SmellDetectorCalls init(){
        if (INSTANCE != null)
        {
            // in my opinion this is optional, but for the purists it ensures
            // that you only ever get the same instance when you call getInstance
            throw new AssertionError("You already initialized me");
        }

        INSTANCE = new SmellDetectorCalls();
        return INSTANCE;
    }
    //function below is where you call on the different classes
    public void AnalyseProject(List<CompilationUnit> list) {
        units = list;
        detections.clear();
        //loop through all the different compilation units and create a list of their classes
        for (CompilationUnit cu : units) {
            List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
            CompilationUnitVisitor compunitvisitor = new CompilationUnitVisitor();
            compunitvisitor.visit(cu, classes);
            //preform the various checks
            BloatedCodeAbuseCheck checkBloats = new BloatedCodeAbuseCheck(classes);
            SwitchChecker switchC = new SwitchChecker();
            checkBloats.performBloatChecks();
            map = checkBloats.getClassThreats();
            methodThreats = checkBloats.getMethodThreats();
            ExcessiveCouplingChecks fe = new ExcessiveCouplingChecks();
            WalkingDeadChecks wD = new WalkingDeadChecks();

            /* this for loop is only needed by the bloatAbuse check
            *  it loops through every key in our hashmap "map"
            *  it then loops through the inner hashmap that is the value of each key in "map"
            *  this is where the blaoted threat level is stored.
            *  we then put the class and its ClassThreatLevels into the detections hashmap
            */
            for (ClassOrInterfaceDeclaration cl : map.keySet()) {
                HashMap value = map.get(cl);
                Set<ThreatLevel> t = value.keySet();
                GodClassCheck Gc = new GodClassCheck(cl);
                for (ThreatLevel tl : t) {
                    //place the class name and all its threats in to the hashmap
                    getDetections().put(cl,new ClassThreatLevels(tl,switchC.complexityOfClass(cl),fe.checkExcessiveCoupling(cl),Gc.checkGodClass(),wD.overallWalkingDead(cl)));
                }
            }
        }
    }
//not used yet but will in future.
    public HashMap getAnalysisResults(){
        return getDetections();
    }

    public void setNumFiles(int numFiles) {
        this.NumFiles = numFiles;
    }
    public int getNumFiles(){
        return NumFiles;
    }

    public String printResults(){
        String string = "";
        for (ClassOrInterfaceDeclaration cl : detections.keySet()) {
         ClassThreatLevels value = detections.get(cl);
         string += "\nCLASS:  "+cl.getName();
         string += "Bloatedness: "+value.getBloatThreatLevel();
         string += "Complexity: "+value.getOOAbuseThreatLevel();
            TemporaryFields tf = new TemporaryFields(cl);
         System.out.println("Temporary Fields: "+tf.toString());
        }
        return string;
    }

    public Double[] getOverallThreatLevels(){
        int bloatedness = 0;
        int complexity = 0;
        int Ec = 0;
        int Gc = 0;
        int Wd = 0;
        for(ClassOrInterfaceDeclaration cl:getDetections().keySet()){
            bloatedness+=getDetections().get(cl).getBloatThreatLevel().ordinal();
            complexity +=getDetections().get(cl).getOOAbuseThreatLevel().ordinal();
            Ec += getDetections().get(cl).getExcessiveCouplingThreatLevel().ordinal();
            Gc += getDetections().get(cl).getGodObjectThreatLevel().ordinal();
            Wd += getDetections().get(cl).getWalkingDeadThreatLevel().ordinal();
        }
        Double bloat = bloatedness/(double)getDetections().keySet().size();
        Double c = complexity/(double)getDetections().keySet().size();
        Double e = Ec/(double)getDetections().keySet().size();
        Double g = Gc/(double)getDetections().keySet().size();
        Double w = Wd/(double)getDetections().keySet().size();


        Double[] total = {bloat*100,c*100,e*100,g*100,w*100};

        return total;
    }

    public HashMap<ClassOrInterfaceDeclaration, ClassThreatLevels> getDetections() {
        return detections;
    }

    public void setDetections(HashMap<ClassOrInterfaceDeclaration, ClassThreatLevels> detections) {
        this.detections = detections;
    }
}

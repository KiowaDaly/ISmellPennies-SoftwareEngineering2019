package Project_FileAnalyser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class JSONReport
{
  private JSONObject aggregateData() throws JSONException {
    int numberOfLines = 0;
    for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet())
      numberOfLines += (cl.getEnd().get().line - cl.getBegin().get().line - 1);

    JSONObject codeStats = new JSONObject();
    codeStats.put("projectFiles", SmellDetectorCalls.getInstance().getNumFiles());
    codeStats.put("projectFilelines", numberOfLines);

    JSONObject bloatStats = new JSONObject();
    bloatStats.put("totalPercentage", SmellDetectorCalls.getInstance().getOverallThreatLevels()[0]);
    JSONObject bloatStatsObj = new JSONObject();
    bloatStatsObj.put("Bloat", bloatStats);

    JSONObject ooa = new JSONObject();
    ooa.put("totalPercentage", SmellDetectorCalls.getInstance().getOverallThreatLevels()[1]);
    JSONObject ooaObj = new JSONObject();
    ooaObj.put("objectOrientedAbusers", ooa);

    JSONObject excessiveCoupling = new JSONObject();
    excessiveCoupling.put("totalPercentage", SmellDetectorCalls.getInstance().getOverallThreatLevels()[2]);
    JSONObject excessiveCouplingObj = new JSONObject();
    excessiveCouplingObj.put("excessiveCoupling", excessiveCoupling);

    JSONObject godClasses = new JSONObject();
    godClasses.put("totalPercentage", SmellDetectorCalls.getInstance().getOverallThreatLevels()[3]);
    JSONObject godClassesObj = new JSONObject();
    godClassesObj.put("godClasses", godClasses);

    JSONObject walkingDead = new JSONObject();
    walkingDead.put("totalPercentage", SmellDetectorCalls.getInstance().getOverallThreatLevels()[4]);
    JSONObject walkingDeadObj = new JSONObject();
    walkingDeadObj.put("walkingDead", godClasses);


    JSONObject report = new JSONObject();
    report.put("statistics", codeStats);
    report.put("codeSmell", bloatStatsObj);
    report.put("codeSmell", ooaObj);
    report.put("codeSmell", excessiveCouplingObj);
    report.put("codeSmell", godClassesObj);
    report.put("codeSmell", walkingDeadObj);

    return report;
  }

  public void toFile(File file2) throws JSONException {
    JSONObject report = aggregateData();
    try (FileWriter file = new FileWriter(file2))
    {
      file.write(report.toString());
      file.flush();
    } catch (IOException e) {}
  }

}

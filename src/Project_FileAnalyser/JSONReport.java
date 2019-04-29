package Project_FileAnalyser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class JSONReport
{
  private static DecimalFormat df2 = new DecimalFormat("#.##");

  private JSONObject aggregateData() throws JSONException {
    int numberOfLines = 0;
    for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet())
      numberOfLines += (cl.getEnd().get().line - cl.getBegin().get().line - 1);

    JSONObject codeStats = new JSONObject();
    codeStats.put("projectFiles", SmellDetectorCalls.getInstance().getNumFiles());
    codeStats.put("projectFilelines", numberOfLines);

    JSONObject bloatStats = new JSONObject();
    bloatStats.put("totalPercentage", String.format("%.2f", SmellDetectorCalls.getInstance().getOverallThreatLevels()[0]));

    JSONObject ooa = new JSONObject();
    ooa.put("totalPercentage", String.format("%.2f", SmellDetectorCalls.getInstance().getOverallThreatLevels()[1]));

    JSONObject excessiveCoupling = new JSONObject();
    excessiveCoupling.put("totalPercentage", String.format("%.2f", SmellDetectorCalls.getInstance().getOverallThreatLevels()[2]));

    JSONObject godClasses = new JSONObject();
    godClasses.put("totalPercentage", String.format("%.2f", SmellDetectorCalls.getInstance().getOverallThreatLevels()[3]));

    JSONObject walkingDead = new JSONObject();
    walkingDead.put("totalPercentage", String.format("%.2f", SmellDetectorCalls.getInstance().getOverallThreatLevels()[4]));

    JSONObject codeSmells = new JSONObject();
    codeSmells.put("bloat", bloatStats);
    codeSmells.put("objectOrientedAbusers", ooa);
    codeSmells.put("excessiveCoupling", excessiveCoupling);
    codeSmells.put("godClasses", godClasses);
    codeSmells.put("walkingDead", walkingDead);

    JSONObject report = new JSONObject();
    report.put("statistics", codeStats);
    report.put("codeSmell", codeSmells);

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

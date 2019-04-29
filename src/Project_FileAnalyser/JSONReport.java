package Project_FileAnalyser;

import org.json.simple.*;

public class JSONReport
{
  private void aggregateData()
  {
    int numberOfFiles = 0;
    for(ClassOrInterfaceDeclaration cl: SmellDetectorCalls.getInstance().getDetections().keySet())
      numberOfLines += (cl.getEnd().get().line - cl.getBegin().get().line - 1);

    JSONObject codeStats = new JSONObject();
    codeStats.put("projectLiles", SmellDetectorCalls.getInstance().getNumFiles());
    codeStats.put("projectFilelines", numberOfLines);

    JSONObject bloatStats = new JSONObject();
    bloatStats.add("totalPercentage:", SmellDetectorCalls.getInstance().getOverallThreatLevels()[0]);
    JSONObject bloatStatsObj = new JSONObject();
    bloatStatsObj.put("Bloat", bloatStats);

    JSONObject ooa = new JSONObject();
    ooa.add("totalPercentage:", SmellDetectorCalls.getInstance().getOverallThreatLevels()[1]);
    JSONObject ooaObj = new JSONObject();
    ooaObj.put("objectOrientedAbusers", ooa);

    JSONObject excessiveCoupling = new JSONObject();
    excessiveCoupling.add("totalPercentage:", SmellDetectorCalls.getInstance().getOverallThreatLevels()[2]);
    JSONObject excessiveCouplingObj = new JSONObject();
    excessiveCouplingObj.put("excessiveCoupling", excessiveCoupling);

    JSONObject godClasses = new JSONObject();
    godClasses.add("totalPercentage:", SmellDetectorCalls.getInstance().getOverallThreatLevels()[3]);
    JSONObject godClassesObj = new JSONObject();
    godClassesObj.put("godClasses", godClasses);

    JSONObject walkingDead = new JSONObject();
    walkingDead.add("totalPercentage:", SmellDetectorCalls.getInstance().getOverallThreatLevels()[4]);
    JSONObject walkingDeadObj = new JSONObject();
    walkingDeadObj.put("walkingDead", godClasses);


    JSONObject report = new JSONObject();
    report.put("statistics", codeStats);
    report.put("codeSmell", bloatStatsObj);
    report.put("codeSmell", ooa);
    report.put("codeSmell", excessiveCoupling);
    report.put("codeSmell", godClasses);
    report.put("codeSmell", walkingDead);
  }

  public void toFile(String path, String filename)
  {
    try (FileWriter file = new FileWriter(path+filename))
    {
      file.write(report.toJSONString());
      file.flush();
    } catch (IOException e) {}
  }

}

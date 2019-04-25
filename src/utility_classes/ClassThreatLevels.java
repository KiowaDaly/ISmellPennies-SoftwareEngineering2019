package utility_classes;

//A utility class that stores the values of te different smells detected
public class ClassThreatLevels {

    ThreatLevel bloatThreatLevel;
    ThreatLevel OOAbuseThreatLevel;
    ThreatLevel ExcessiveCouplingThreatLevel;
    public ClassThreatLevels(ThreatLevel bloat, ThreatLevel OOAbuse, ThreatLevel ec){
        this.bloatThreatLevel = bloat;
        this.OOAbuseThreatLevel = OOAbuse;
        this.ExcessiveCouplingThreatLevel = ec;
    }



//    ThreatLevel GodObjectThreatLevel;
//    ThreatLevel WalkingDeadThreatLevel;

    public ThreatLevel getBloatThreatLevel() {
        return bloatThreatLevel;
    }

    public void setBloatThreatLevel(ThreatLevel bloatThreatLevel) {
        this.bloatThreatLevel = bloatThreatLevel;
    }

    public ThreatLevel getOOAbuseThreatLevel() {
        return OOAbuseThreatLevel;
    }

    public void setOOAbuseThreatLevel(ThreatLevel OOAbuseThreatLevel) {
        this.OOAbuseThreatLevel = OOAbuseThreatLevel;
    }

    //to be added later with more checks finished.
    public ThreatLevel getExcessiveCouplingThreatLevel() {
        return ExcessiveCouplingThreatLevel;
    }
//
    public void setExcessiveCouplingThreatLevel(ThreatLevel excessiveCouplingThreatLevel) {
        ExcessiveCouplingThreatLevel = excessiveCouplingThreatLevel;
    }
//
//    public ThreatLevel getGodObjectThreatLevel() {
//        return GodObjectThreatLevel;
//    }
//
//    public void setGodObjectThreatLevel(ThreatLevel godObjectThreatLevel) {
//        GodObjectThreatLevel = godObjectThreatLevel;
//    }
//
//    public ThreatLevel getWalkingDeadThreatLevel() {
//        return WalkingDeadThreatLevel;
//    }
//
//    public void setWalkingDeadThreatLevel(ThreatLevel walkingDeadThreatLevel) {
//        WalkingDeadThreatLevel = walkingDeadThreatLevel;
//    }





}

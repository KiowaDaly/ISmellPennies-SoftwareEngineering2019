package utility_classes;

import java.util.Set;

//A utility class that stores the values of te different smells detected
public class ClassThreatLevels {

    ThreatLevel bloatThreatLevel;
    ThreatLevel OOAbuseThreatLevel;
    public ClassThreatLevels(ThreatLevel bloat, ThreatLevel OOAbuse){
        this.bloatThreatLevel = bloat;
        this.OOAbuseThreatLevel = OOAbuse;
    }


//    ThreatLevel ExcessiveCouplingThreatLevel;
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
//    public ThreatLevel getExcessiveCouplingThreatLevel() {
//        return ExcessiveCouplingThreatLevel;
//    }
//
//    public void setExcessiveCouplingThreatLevel(ThreatLevel excessiveCouplingThreatLevel) {
//        ExcessiveCouplingThreatLevel = excessiveCouplingThreatLevel;
//    }
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

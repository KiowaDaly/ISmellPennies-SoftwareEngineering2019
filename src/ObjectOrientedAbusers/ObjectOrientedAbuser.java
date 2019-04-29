package ObjectOrientedAbusers;

import utility_classes.ThreatLevel;

public class ObjectOrientedAbuser {
    private ThreatLevel DataHiding;
    private ThreatLevel RefusedBequest;
    private ThreatLevel SwitchChecker;
    private ThreatLevel TemporaryFields;
    public ObjectOrientedAbuser(ThreatLevel dh, ThreatLevel rb, ThreatLevel sc, ThreatLevel tf){
        DataHiding = dh;
        RefusedBequest = rb;
        SwitchChecker = sc;
        TemporaryFields = tf;
    }

    public ThreatLevel getOOAThreatLevel(){
        ThreatLevel complexity = ThreatLevel.NONE;

        double total = DataHiding.ordinal() + RefusedBequest.ordinal() + SwitchChecker.ordinal() + TemporaryFields.ordinal();
        total = (total/4);
        if(total<0.1)
            complexity = ThreatLevel.NONE;
        else if(total<0.3)
            complexity = ThreatLevel.LOW;
        else if(total<0.6)
            complexity = ThreatLevel.MEDIUM;
        else
            complexity = ThreatLevel.HIGH;

        return complexity;
    }
}

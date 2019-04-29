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
        total = Math.ceil((total)/4);
        if(total == 0)
            complexity = ThreatLevel.NONE;
        else if(total==1)
            complexity = ThreatLevel.LOW;
        else if(total==2)
            complexity = ThreatLevel.MEDIUM;
        else
            complexity = ThreatLevel.HIGH;
        return complexity;
    }
}

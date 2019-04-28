package utility_classes;

//utility enum to use in tandem with class threat LEVELS

public enum ThreatLevel {
    NONE,LOW,MEDIUM,HIGH{
    public ThreatLevel next() {
        return null; // see below for options for this line
    };
    };

    public ThreatLevel next() {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal() + 1];
        }
}


package utility_classes;

//utility enum to use in tandem with class threat LEVELS

public enum ThreatLevel {
    NONE,LOW,MEDIUM,HIGH{
        public ThreatLevel next() {
            return null;
        };
    };
    public ThreatLevel next() {
        return values()[ ordinal()+1];
    }
}
package arkhamDraft;

import java.util.Locale;

public enum Faction {
    GUARDIAN, SEEKER, ROGUE, MYSTIC, SURVIVOR, NEUTRAL;

    public String getFactionName() {
        switch(this)
        {
            case GUARDIAN:
                return "Guardian";
            case SEEKER:
                return "Seeker";
            case ROGUE:
                return "Rogue";
            case MYSTIC:
                return "Mystic";
            case SURVIVOR:
                return "Survivor";
            case NEUTRAL:
                return "Neutral";
            default:
                return "faction not found";
        }
    }

    public String getFactionCode() {
        return getFactionName().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return getFactionName();
    }
}

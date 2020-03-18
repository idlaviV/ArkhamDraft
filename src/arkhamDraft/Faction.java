package arkhamDraft;

import com.google.gson.annotations.SerializedName;

public enum Faction {
    @SerializedName("guardian")
    GUARDIAN,
    @SerializedName("seeker")
    SEEKER,
    @SerializedName("rogue")
    ROGUE,
    @SerializedName("mystic")
    MYSTIC,
    @SerializedName("survivor")
    SURVIVOR,
    @SerializedName("neutral")
    NEUTRAL
}

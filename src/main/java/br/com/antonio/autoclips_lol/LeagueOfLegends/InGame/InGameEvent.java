package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InGameEvent(
        @JsonProperty("EventID")
        Integer eventId,

        @JsonProperty("EventName")
        String eventName,

        @JsonProperty("EventTime")
        Double eventTime,

        @JsonProperty("KillerName")
        String killerName,

        @JsonProperty("VictimName")
        String victimName,

        @JsonProperty("Assisters")
        List<String> assisters,

        @JsonProperty("KillStreak")
        Integer killStreak,

        @JsonProperty("DragonType")
        String dragonType,

        @JsonProperty("Stolen")
        String stolen,

        @JsonProperty("TurretKilled")
        String turretKilled,

        @JsonProperty("InhibKilled")
        String inhibKilled,

        @JsonProperty("Acer")
        String acer,

        @JsonProperty("AcingTeam")
        String acingTeam
        ) {
}

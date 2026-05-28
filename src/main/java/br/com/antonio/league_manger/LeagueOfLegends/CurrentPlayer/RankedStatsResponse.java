package br.com.antonio.league_manger.LeagueOfLegends.CurrentPlayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RankedStatsResponse(

        @JsonProperty("queueMap")
        Map<String, CurrentPlayerRankedStats> queueMap
) {
    public CurrentPlayerRankedStats soloQueue() {
        if (queueMap == null) {
            return null;
        }

        return queueMap.get("RANKED_SOLO_5x5");
    }
}
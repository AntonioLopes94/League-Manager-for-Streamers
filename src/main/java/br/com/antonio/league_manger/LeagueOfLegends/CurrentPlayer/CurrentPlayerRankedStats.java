package br.com.antonio.league_manger.LeagueOfLegends.CurrentPlayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CurrentPlayerRankedStats (
        @JsonProperty("queueType")
        String queueType,

        @JsonProperty("tier")
        String tier,

        @JsonProperty("division")
        String division,

        @JsonProperty("leaguePoints")
        Integer leaguePoints,

        @JsonProperty("wins")
        Integer wins,

        @JsonProperty("losses")
        Integer losses
){
}

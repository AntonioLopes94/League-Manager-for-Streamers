package br.com.antonio.league_manger.LeagueOfLegends.CurrentPlayer;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentPlayerInfos(
        @JsonProperty("accountId")
        long accountId,

        @JsonProperty("displayName")
        String displayName,

        @JsonProperty("gameName")
        String gameName,

        @JsonProperty("tagLine")
        String tagLine,

        @JsonProperty("internalName")
        String internalName,

        @JsonProperty("puuid")
        String puuid,

        @JsonProperty("summonerId")
        long summonerId,

        @JsonProperty("summonerLevel")
        int summonerLevel,

        CurrentPlayerRankedStats rankedSolo
) {
}

package br.com.antonio.league_manger.LeagueOfLegends.InGame;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EventList(
        @JsonProperty("Events")
        List<InGameEvent> events
) {
}

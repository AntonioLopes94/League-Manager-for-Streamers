package br.com.antonio.league_manger.LeagueOfLegends.Client;

import java.util.List;

public record ChampSelectSession(
        int localPlayerCellId,
        List<List<ChampSelectAction>> actions
) {
}

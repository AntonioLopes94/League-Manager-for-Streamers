package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

import java.util.List;

public record ChampSelectSession(
        int localPlayerCellId,
        List<List<ChampSelectAction>> actions
) {
}

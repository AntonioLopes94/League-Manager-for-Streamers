package br.com.antonio.league_manger.LeagueOfLegends.Client;

public record ChampSelectAction(
        int id,
        int actorCellId,
        int championId,
        boolean completed,
        String type
) {
}

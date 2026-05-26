package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

public record ChampSelectAction(
        int id,
        int actorCellId,
        int championId,
        boolean completed,
        String type
) {
}

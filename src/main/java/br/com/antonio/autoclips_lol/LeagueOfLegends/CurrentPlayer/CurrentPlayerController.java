package br.com.antonio.autoclips_lol.LeagueOfLegends.CurrentPlayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrentPlayerController {

    private final  CurrentPlayerService currentPlayerService;

    public CurrentPlayerController(CurrentPlayerService currentPlayerService) {
        this.currentPlayerService = currentPlayerService;
    }

    @GetMapping(value = "/api/current-player/elo")
    public String currentPlayerElo() {
        CurrentPlayerInfos player = currentPlayerService.updateCurrentPlayerInfos();
        CurrentPlayerRankedStats ranked = player.rankedSolo();

        if (ranked == null) {
            return player.gameName() + "#" + player.tagLine() + " está sem dados de Ranked Solo.";
        }

        return player.gameName()
                + "#"
                + player.tagLine()
                + " está "
                + ranked.tier()
                + " "
                + ranked.division()
                + " com "
                + ranked.leaguePoints()
                + " LP. "
                + ranked.wins()
                + "W/"
                + ranked.losses()
                + "L.";
    }

}

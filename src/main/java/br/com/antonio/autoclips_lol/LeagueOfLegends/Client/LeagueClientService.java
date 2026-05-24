package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

import br.com.antonio.autoclips_lol.LeagueOfLegends.InGame.InGameService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Service
public class LeagueClientService{
    private final InGameService inGameService;
    private final RestClient leagueClientApi;
    private boolean readyCheckAccepted = false;

    public LeagueClientService(InGameService inGameService, @Qualifier("leagueClientApi") RestClient leagueClientApi) {
        this.inGameService = inGameService;
        this.leagueClientApi = leagueClientApi;
    }

    @Scheduled(fixedDelay = 5000)
    public void clientHandler(){
        String actualGameFlowPhase = gameFlowPhase();
        println("GameFlowPhase atual: [" + actualGameFlowPhase + "]");
        switch (actualGameFlowPhase){
            case GameFlowPhase.NONE ->  {
                readyCheckAccepted = false;
            }
            case GameFlowPhase.LOBBY -> {
                readyCheckAccepted = false;
                inGameService.resetLastEventById();
            }
            case GameFlowPhase.READY_CHECK ->  {
                if(!readyCheckAccepted){
                    acceptReadyCheck();
                    readyCheckAccepted = true;
                }
            }
//            case GameFlowPhase.CHAMP_SELECT ->

//            case GameFlowPhase.MATCHMAKING ->

            case GameFlowPhase.IN_PROGRESS -> {
                inGameService.InGameEventListener();
                inGameService.eventHandler(inGameService.getLastEvent());
            }

            default -> {
                // Lobby, Matchmaking, ChampSelect etc.
            }
        }
    }

    public String gameFlowPhase() {
        return leagueClientApi
                .get()
                .uri("/lol-gameflow/v1/gameflow-phase")
                .retrieve()
                .body(String.class)
                .replace("\"", "");
    }

    public void acceptReadyCheck() {
        try {
            leagueClientApi
                    .post()
                    .uri("/lol-matchmaking/v1/ready-check/accept")
                    //.uri("/lol-matchmaking/v1/ready-check/decline")
                    .retrieve()
                    .toBodilessEntity();
            println("Partida aceita");
        }catch (HttpServerErrorException | HttpClientErrorException e){
            println("Not ready to check " + e.getMessage());
        }
    }
}

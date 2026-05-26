package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

import br.com.antonio.autoclips_lol.LeagueOfLegends.CurrentPlayer.CurrentPlayerService;
import br.com.antonio.autoclips_lol.LeagueOfLegends.InGame.InGameService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Service
public class LeagueClientService{
    private final RestClient leagueClientApi;
    private final InGameService inGameService;
    private final CurrentPlayerService currentPlayerService;
    private final ChampSelectService champSelectService;
    private ReadyCheckService readyCheckService;
    private final RefreshScope refreshScope;

    public LeagueClientService(InGameService inGameService, @Qualifier("leagueClientApi") RestClient leagueClientApi, ReadyCheckService readyCheckService, RefreshScope refreshScope, CurrentPlayerService currentPlayerService, ChampSelectService champSelectService) {
        this.inGameService = inGameService;
        this.leagueClientApi = leagueClientApi;
        this.readyCheckService = readyCheckService;
        this.refreshScope = refreshScope;
        this.currentPlayerService = currentPlayerService;
        this.champSelectService = champSelectService;
    }

    @Scheduled(fixedDelay = 5000)
    public void clientHandler(){
        try {
            String actualGameFlowPhase = gameFlowPhase();
            println("GameFlowPhase atual: [" + actualGameFlowPhase + "]");
            switch (actualGameFlowPhase){
                case GameFlowPhase.NONE ->  {
                    currentPlayerService.updateCurrentPlayerInfos();
                }
                case GameFlowPhase.LOBBY, GameFlowPhase.MATCHMAKING -> {
                    readyCheckService.setReadyCheckAccepted(false);
                    inGameService.postGameVariablesReset();

                }
                case GameFlowPhase.READY_CHECK ->  {
                    if(!readyCheckService.isReadyCheckAccepted()){
                        readyCheckService.acceptReadyCheck();
                    }
                }
                case GameFlowPhase.CHAMP_SELECT -> {
                    readyCheckService.setReadyCheckAccepted(false);
                    try {
                        Integer actionId = champSelectService.findMyPickActionId(champSelectService.getChampSelectSession());
                        champSelectService.printChampSelectSession();
                        champSelectService.hoverBan(actionId);
                    } catch (NullPointerException _) {
                        println("Null no try do hover");
                    }
                }

    //            case GameFlowPhase.MATCHMAKING ->

                case GameFlowPhase.IN_PROGRESS -> {
                    inGameService.InGameEventListener();
                    inGameService.eventHandler(inGameService.getLastEvent());
                }

                default -> {
                    println("default");
                }
            }
        } catch (ResourceAccessException | HttpClientErrorException _) {
            println("Jogo nao esta aberto");
            refreshScope.refresh("leagueClientApi");
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
}

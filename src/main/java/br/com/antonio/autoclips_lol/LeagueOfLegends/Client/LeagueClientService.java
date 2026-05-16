package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

import br.com.antonio.autoclips_lol.LeagueOfLegends.InGame.InGameListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Service
public class LeagueClientService{
    private final InGameListener inGameListener;
    private final RestClient leagueClientApi;
    private boolean readyCheckAccepted = false;

    public LeagueClientService(InGameListener inGameListener, @Qualifier("leagueClientApi") RestClient leagueClientApi) {
        this.inGameListener = inGameListener;
        this.leagueClientApi = leagueClientApi;
    }

    @Scheduled(fixedRate = 1000)
    public String gameflowPhase() {
        return leagueClientApi
                    .get()
                    .uri("/lol-gameflow/v1/gameflow-phase")
                    .retrieve()
                    .body(String.class)
                    .replace("\"", "");//todo retirar depois
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
    @Scheduled(fixedDelay = 1000)
    public void clientHandler(){
        if("ReadyCheck".equalsIgnoreCase(gameflowPhase()) && !readyCheckAccepted){
            acceptReadyCheck();
            readyCheckAccepted = true;
        }

        if (!"ReadyCheck".equalsIgnoreCase(gameflowPhase())) {
            readyCheckAccepted = false;
        }

        if ("InProgress".equalsIgnoreCase(gameflowPhase())){
            inGameListener.eventListener();
        }
    }
}

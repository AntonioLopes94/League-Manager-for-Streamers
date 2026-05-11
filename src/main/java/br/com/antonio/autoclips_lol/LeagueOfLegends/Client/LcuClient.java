package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Service
public class LcuClient {

    private final RestClient leagueClientApi;
    private String estadoDoJogo;

    public LcuClient(@Qualifier("leagueClientApi") RestClient leagueClientApi) {
        this.leagueClientApi = leagueClientApi;
    }

    @Scheduled(fixedRate = 1000)
    public void getGameflowPhase(){
        println("getgameflow rodando");
        estadoDoJogo = leagueClientApi
                .get()
                .uri("/lol-gameflow/v1/gameflow-phase")
                .retrieve()
                .body(String.class)
                .replace("\"", "");//todo retirar depois
        println(estadoDoJogo);
    }

    @Scheduled(fixedRate = 5000)
    public void acceptReadyCheck() {
        println("acceptreadycheck rodando");//todo adicionar um check no timer e quando estiver 3 sec ele aceita
        if (estadoDoJogo.equals("ReadyCheck")) {
            leagueClientApi
                    .post()
                    .uri("/lol-matchmaking/v1/ready-check/accept")
                    .retrieve()
                    .toBodilessEntity();
        }
    }


}

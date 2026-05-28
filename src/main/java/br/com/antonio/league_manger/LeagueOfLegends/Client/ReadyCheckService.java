package br.com.antonio.league_manger.LeagueOfLegends.Client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Service
@Getter
public class ReadyCheckService {
    private final RestClient leagueClientApi;
    //@Setter
    //private boolean readyCheckAccepted = false;

    public ReadyCheckService(RestClient leagueClientApi) {
        this.leagueClientApi = leagueClientApi;
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
            //readyCheckAccepted = true;//todo confirmar se precisa desse check mesmo
        }catch (HttpServerErrorException | HttpClientErrorException e){
            println("Not ready to check " + e.getMessage());
        }
    }
}

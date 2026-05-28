package br.com.antonio.league_manger.Streambot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static java.lang.IO.println;

@Component
public class StreamerBotApiClient {

    private final RestClient streamerBotApi;

    public StreamerBotApiClient(@Qualifier("streamerBotApi") RestClient streamerBotApi) {
        this.streamerBotApi = streamerBotApi;
    }

    public ActionList getActions() {
        println("Get nas ações do Streamer bot");
        return streamerBotApi
                .get()
                .uri("/GetActions")
                .retrieve()
                .body(ActionList.class);
    }

    public void postDoAction(Action action){
        streamerBotApi
                .post()
                .uri("/DoAction")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("action", action))
                .retrieve()
                .toBodilessEntity();
    }
}

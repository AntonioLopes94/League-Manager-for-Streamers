package br.com.antonio.autoclips_lol.Streambot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class StreamerBotApiClient {

    private final RestClient streamerBotApi;

    public StreamerBotApiClient(@Qualifier("streamerBotApi") RestClient streamerBotApi) {
        this.streamerBotApi = streamerBotApi;
    }

    public ActionList getActions() {
        return streamerBotApi
                .get()
                .uri("/GetActions")
                .retrieve()
                .body(ActionList.class);
    }

    public void doAction(Action action){
        streamerBotApi
                .post()
                .uri("/DoAction")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("action", action))
                .retrieve()
                .toBodilessEntity();
    }
}

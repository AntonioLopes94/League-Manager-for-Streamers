package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InGameApiClient {

    private final RestClient inGameApi;
    public InGameApiClient(@Qualifier("inGameLeagueApi") RestClient inGameApi) {
        this.inGameApi = inGameApi;
    }

    public EventList getInGameEvents(){
        return inGameApi
                .get()
                .uri("/liveclientdata/eventdata")
                .retrieve()
                .body(EventList.class);
    }
}

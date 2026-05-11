package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EventService {

    private final RestClient inGameApi;

    public EventService(@Qualifier("inGameApi") RestClient inGameApi) {
        this.inGameApi = inGameApi;
    }

    public EventList getInGameEvents(){
        return inGameApi
                .get()
                .uri("/liveclientdata/eventdata")
                .retrieve()
                .body(EventList.class);
    }

    public void eventHandler(InGameEvent event){
        //todo adicionar um switch para que cada evento chame um metodo especifico de reacao ao evento
    }
}

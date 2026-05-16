package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import br.com.antonio.autoclips_lol.LeagueOfLegends.CurrentPlayer.CurrentPlayerService;
import br.com.antonio.autoclips_lol.Streambot.StreamerBotService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Service
public class EventService {

    private final RestClient inGameApi;
    private int lastEventById = -1;
    private CurrentPlayerService  currentPlayerService;
    private StreamerBotService  streamerBotService;
    public EventService(@Qualifier("inGameLeagueApi") RestClient inGameApi, CurrentPlayerService currentPlayerService, StreamerBotService streamerBotService) {
        this.inGameApi = inGameApi;
        this.currentPlayerService = currentPlayerService;
        this.streamerBotService = streamerBotService;
    }

    public EventList getInGameEvents(){
        return inGameApi
                .get()
                .uri("/liveclientdata/eventdata")
                .retrieve()
                .body(EventList.class);
    }

    public void eventListToInGameEvent(EventList eventList){
        if (eventList == null || eventList.events() == null) {
            return;
        }

        String currentPlayerName = currentPlayerService.playerInfos().displayName();


        for (InGameEvent event : eventList.events()) {

            if (event.eventId() <= lastEventById) {
                    continue;
                }

            lastEventById = event.eventId();

            if (event.eventName().equalsIgnoreCase("ChampionKill")
                    && event.killerName() != null
                    && event.killerName().contains(currentPlayerName)){
                println("funcionou de pegar a kill");
                streamerBotService.teste();
            }

            lastEventById = event.eventId();

            println(event.eventId() + ", " + event.eventName() + ", " + event.killerName() + ", " + event.victimName());
        }
    }

    private void eventHandler(InGameEvent event){
        //todo adicionar um switch para que cada evento chame um metodo especifico de reacao ao evento
    }
}

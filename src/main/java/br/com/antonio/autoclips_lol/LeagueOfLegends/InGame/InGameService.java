package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import br.com.antonio.autoclips_lol.LeagueOfLegends.CurrentPlayer.CurrentPlayerService;
import br.com.antonio.autoclips_lol.Streambot.StreamerBotService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.IO.println;

@Service
public class InGameService {

    private final InGameApiClient inGameApiClient;
    private final StreamerBotService streamerBotService;
    private final List<InGameEvent> currentMatchEvents = new ArrayList<>();
    private int lastEventById = -1;
    private CurrentPlayerService currentPlayerService;

    public InGameService(InGameApiClient inGameApiClient, CurrentPlayerService currentPlayerService, StreamerBotService streamerBotService) {
        this.inGameApiClient = inGameApiClient;
        this.currentPlayerService = currentPlayerService;
        this.streamerBotService = streamerBotService;
    }

    public void eventHandler(InGameEvent event){

        String currentPlayerName = currentPlayerService.getCurrentPlayerName();
        switch(event.eventName()){
            case DefaultEvents.CHAMPION_KILL,  DefaultEvents.ACE -> {
                if(event.killerName().equalsIgnoreCase(currentPlayerName)
                        || event.victimName().equalsIgnoreCase(currentPlayerName)
                        || event.assisters().contains(currentPlayerName)
                        || event.acer().contains(currentPlayerName)) {
//                    streamerBotService.doDefaultActions(DefaultActions.CLIP);
                    println("KILL CLIPADA");
                }
            }
            default -> println("default event");
        }
    }

    public InGameEvent getLastEvent(){
        return currentMatchEvents.getLast();
    }

    //todo escuta o evento -> se for novo salva na lista currentMatchEvents
    //todo metodo getLastEvent
    //todo metodo addToCurrentMatchEventsList
    //todo criar um Enum com os Eventos?
    //todo


    public void eventListener() {
        try{
            EventList eventList = inGameApiClient.getInGameEvents();
            eventListToCurrentMatchEventsList(eventList);
        } catch (ResourceAccessException resourceAccessException) {
            println("Sem partidas acontecendo");//todo voltar com esse print
        } catch (NullPointerException e){
            println("Null point exception no inGameListener");
        }catch (HttpClientErrorException httpClientErrorException) {

        }
    }

    public void eventListToCurrentMatchEventsList(EventList eventList){
        String currentPlayerName = currentPlayerService.getCurrentPlayerName();

        if (eventList == null || eventList.events() == null) {
            return;
        }
        for (InGameEvent event : eventList.events()) {
            if (event.eventId() <= lastEventById) {
                continue;
            }
            currentMatchEvents.add(event);
            lastEventById = event.eventId();
        }
    }
    public void resetLastEventById() {
        lastEventById = -1;
    }
}

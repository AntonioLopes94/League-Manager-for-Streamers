package br.com.antonio.league_manger.LeagueOfLegends.InGame;

import br.com.antonio.league_manger.LeagueOfLegends.CurrentPlayer.CurrentPlayerService;
import br.com.antonio.league_manger.Streambot.StreamerBotService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.IO.println;

@Service
public class InGameService {

    private final InGameApiClient inGameApiClient;
    private final StreamerBotService streamerBotService;
    private final List<InGameEvent> currentMatchEvents = new ArrayList<>();
    private int lastEventById = -1;
    private final Set<Integer> processedEventIds = new HashSet<>();
    private CurrentPlayerService currentPlayerService;

    public InGameService(InGameApiClient inGameApiClient, CurrentPlayerService currentPlayerService, StreamerBotService streamerBotService) {
        this.inGameApiClient = inGameApiClient;
        this.currentPlayerService = currentPlayerService;
        this.streamerBotService = streamerBotService;
    }

    public void eventHandler(InGameEvent event){
        String currentPlayerName = currentPlayerService.getCurrentPlayerName();
        if(!processedEventIds.add(event.eventId())){
            return;
        }
        switch(event.eventName()){
            case DefaultEvents.CHAMPION_KILL,
                 DefaultEvents.ACE,
                 DefaultEvents.MULTIKILL -> {
                if(currentPlayerName.equalsIgnoreCase(event.killerName())
                        || currentPlayerName.equalsIgnoreCase(event.victimName())
                        || currentPlayerName.equalsIgnoreCase(event.acer())
                        || event.assisters().contains(currentPlayerName)
                        //todo arrumar um jeito de adicionar o nome por array assisters
                        ) {

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

    public void InGameEventListener() {
        try{
            EventList inGameEvents = inGameApiClient.getInGameEvents();
            addNewEventToCurrentMatchList(inGameEvents);
        } catch (ResourceAccessException resourceAccessException) {
            println("Sem partidas acontecendo");//todo voltar com esse print
        } catch (NullPointerException e){
            println("Null point exception no inGameListener");
        } catch (HttpClientErrorException httpClientErrorException) {

        }
    }

    public void addNewEventToCurrentMatchList(EventList inGameEvents){
        if (inGameEvents == null || inGameEvents.events() == null) {
            return;
        }
        for (InGameEvent event : inGameEvents.events()) {
            if (event.eventId() <= lastEventById) {
                continue;
            }
            currentMatchEvents.add(event);
            lastEventById = event.eventId();
        }
    }


    public void postGameVariablesReset() {
        lastEventById = -1;
        currentMatchEvents.clear();
        processedEventIds.clear();

    }
}

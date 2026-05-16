package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static java.lang.IO.println;

@Component
public class InGameListener {

    private final EventService eventService;
    public InGameListener(EventService eventService) {
        this.eventService = eventService;
    }

    public void eventListener() {
        try{
            EventList eventList = eventService.getInGameEvents();
            eventService.eventListToInGameEvent(eventList);
        } catch (ResourceAccessException resourceAccessException) {
            println("Sem partidas acontecendo");//todo voltar com esse print
        } catch (NullPointerException e){
            println("Null point exception no inGameListener");
        }catch (HttpClientErrorException httpClientErrorException) {

        }
    }
}

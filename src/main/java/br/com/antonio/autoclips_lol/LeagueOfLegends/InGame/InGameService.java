package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static java.lang.IO.println;

@Service
public class InGameService {

    private final EventService eventService;
    public InGameService(EventService eventService) {
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
    private void eventHandler(InGameEvent event){
        //todo adicionar um switch para que cada evento chame um metodo especifico de reacao ao evento
    }
}

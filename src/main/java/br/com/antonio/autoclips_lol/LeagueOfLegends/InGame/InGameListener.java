package br.com.antonio.autoclips_lol.LeagueOfLegends.InGame;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import static java.lang.IO.print;
import static java.lang.IO.println;

@Component
public class InGameListener {

    private final EventService eventService;
    public InGameListener(EventService eventService) {
        this.eventService = eventService;
    }

    private int lastEventById = -1;

    @Scheduled(fixedRate = 1000)
    public void listenEvents() {
        try {
            EventList eventList = eventService.getInGameEvents();
            for (InGameEvent event : eventList.events()){
                if(event.eventId() <= lastEventById){
                    continue;
                }

                println("Evento: " + event.eventName());
                lastEventById = event.eventId();

                print(eventList.events());


            }

        }catch (ResourceAccessException resourceAccessException) {
            println("Sem partidas acontecendo");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                println("Sleep interrupted");
                Thread.currentThread().interrupt();
            }
        } catch (NullPointerException e){
            println("Null point exception");
        }
    }
}

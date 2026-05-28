package br.com.antonio.league_manger.Streambot;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static java.lang.IO.println;

@Service
public class StreamerBotService {

    private final StreamerBotApiClient streamerBotApiClient;
    private final Map<DefaultActions, Action> defaultActions = new EnumMap<>(DefaultActions.class);


    public StreamerBotService(StreamerBotApiClient streamerBotApiClient) {
        this.streamerBotApiClient = streamerBotApiClient;
    }

    @PostConstruct
    public void loadDefaultActions(){
        println("Carregando o defaultActions");
        try {
            println("Try no defaultActions");
            ActionList actionList = streamerBotApiClient.getActions();
            List<Action> actions = actionList.actions();

            for (Action action : actions) {
                println("Action encontrada: " + action.name() + " | id: " + action.id());
            }

            for  (DefaultActions defaultAction : DefaultActions.values()) {
                actions
                        .stream()
                        .filter(action ->
                                action.name().equals(defaultAction.getActionName()))
                        .findFirst()
                        .ifPresent(foundAction -> {
                            defaultActions.put(defaultAction, foundAction);
                            println("Action assimilada: "
                                    + defaultAction
                                    + " -> "
                                    + foundAction.name()
                                    + " | id: "
                                    + foundAction.id());
                        });
            }
        } catch (Exception e) {
            println("Error while loading default actions: " + e.getClass().getSimpleName());
            println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void doDefaultActions(DefaultActions defaultAction){
        Action action = defaultActions.get(defaultAction);
        streamerBotApiClient.postDoAction(action);
    }
}

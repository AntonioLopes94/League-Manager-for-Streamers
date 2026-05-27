package br.com.antonio.autoclips_lol.LeagueOfLegends.Client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static java.lang.IO.println;
@Service
public class ChampSelectService {
    private final RestClient leagueClientApi;
    private static final int RAMMUS_ID = 33;

    public ChampSelectService(RestClient leagueClientApi) {
        this.leagueClientApi = leagueClientApi;
    }

    public ChampSelectSession getChampSelectSession() {
        return leagueClientApi
                .get()
                .uri("/lol-champ-select/v1/session")
                .retrieve()
                .body(ChampSelectSession.class);
    }

    public Integer findMyBanActionId(ChampSelectSession session) {
        int localPlayerCellId = session.localPlayerCellId();

        for (List<ChampSelectAction> actionGroup : session.actions()) {
            for (ChampSelectAction action : actionGroup) {
                if (action.actorCellId() == localPlayerCellId
                        && action.type().equals("ban")//reiniciado com ban
                        && !action.completed()) {
                    return action.id();
                }
            }
        }
        return null;
    }

    public void hoverBan(int actionId) {
        leagueClientApi
                .patch()
                .uri("/lol-champ-select/v1/session/actions/{actionId}", actionId)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\n" +
                        "  \"championId\": 33,\n" +
                        "  \"completed\": true\n" +
                        "}")
                .retrieve()
                .toBodilessEntity();

        println("Rammus hovered for ban");
    }

    public void completeAction(int actionId) {
        println("POST complete URI: /lol-champ-select/v1/session/actions/" + actionId + "/complete");

        leagueClientApi
                .post()
                .uri("/lol-champ-select/v1/session/actions/{actionId}/complete", actionId)
                .retrieve()
                .toBodilessEntity();

        println("Complete action request finished");
    }

    public void printChampSelectSession() {
        String session = leagueClientApi
                .get()
                .uri("/lol-champ-select/v1/session")
                .retrieve()
                .body(String.class);

        println(session);
    }

}

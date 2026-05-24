package br.com.antonio.autoclips_lol.LeagueOfLegends.CurrentPlayer;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrentPlayerService {

    private final RestClient leagueClientApi;
    public CurrentPlayerService(@Qualifier("leagueClientApi") RestClient leagueClientApi) {
        this.leagueClientApi = leagueClientApi;
    }


    @Scheduled(fixedRate = 1000)
    public CurrentPlayerInfos playerInfos(){
        CurrentPlayerInfos infos = leagueClientApi
                .get()
                .uri("/lol-summoner/v1/current-summoner")
                .retrieve()
                .body(CurrentPlayerInfos.class);

        CurrentPlayerRankedStats rankedSolo = getRankedStatsByPuuid(infos.puuid());

        return new CurrentPlayerInfos(
                infos.accountId(),
                infos.displayName(),
                infos.gameName(),
                infos.tagLine(),
                infos.internalName(),
                infos.puuid(),
                infos.summonerId(),
                infos.summonerLevel(),
                rankedSolo);
    }

    public CurrentPlayerRankedStats getRankedStatsByPuuid(String puuid) {
        RankedStatsResponse response = leagueClientApi
                .get()
                .uri("/lol-ranked/v1/ranked-stats/"+ puuid)
                .retrieve()
                .body(RankedStatsResponse.class);

        if (response == null) {
            return null;
        }

        return response.soloQueue();
    }

    public String getCurrentPlayerName() {
        return playerInfos().gameName();
    }

}

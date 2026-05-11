package br.com.antonio.autoclips_lol.Config;

import br.com.antonio.autoclips_lol.LeagueOfLegends.Client.LockFile;
import br.com.antonio.autoclips_lol.LeagueOfLegends.InGame.EventList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    private GlobalVariables globalVariables;

    @Bean
    public RestClient.Builder restClientBuilder(){
        return RestClient.builder();
    }

    @Bean
    public RestClient leagueClientApi(RestClient.Builder restClientBuilder){
        LockFile lockFile = globalVariables.lockFile();

        String baseUrl = lockFile.protocol()
                + "://"
                + globalVariables.leagueOfLegendsBaseUrl()
                + ":"
                + lockFile.port();

        return  restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeaders(headers ->
                        headers.setBasicAuth("riot", lockFile.password()))
                .build();
    }

    @Bean
    public RestClient inGameLeagueApi(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl("https://"+globalVariables.leagueOfLegendsBaseUrl()+"2999")
                .build();
    }

    @Bean
    public RestClient streamerBotApi(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .baseUrl(globalVariables.streamerBotBaseUrl())
                .build();
    }



}

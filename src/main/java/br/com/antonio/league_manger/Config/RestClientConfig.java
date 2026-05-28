package br.com.antonio.league_manger.Config;

import br.com.antonio.league_manger.LeagueOfLegends.Client.LockFile;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import static java.lang.IO.println;

@Configuration
public class RestClientConfig {
    private final GlobalVariables globalVariables = GlobalVariables.fromJsonFile();

    @Bean
    public RestClient.Builder restClientBuilder(){
        return RestClient.builder();
    }

    @Bean
    @RefreshScope
    public RestClient leagueClientApi(RestClient.Builder restClientBuilder){
        println("Conectando ao leagueClient API");
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
        println("Conectando ao inGameLeagueApi");
        return restClientBuilder
                .baseUrl("https://"+globalVariables.leagueOfLegendsBaseUrl()+":2999")
                .build();
    }

    @Bean
    public RestClient streamerBotApi(RestClient.Builder restClientBuilder) {
        println("Conectando ao streamer bot");
        return restClientBuilder
                .baseUrl(globalVariables.streamerBotBaseUrl())
//                .baseUrl("http://127.0.0.1:7474")//todo voltar com a variavel global aqui
                .build();
    }

}

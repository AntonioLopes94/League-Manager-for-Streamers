package br.com.antonio.autoclips_lol;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static java.lang.IO.println;
@Component
public class StreambotApiRequester {

    public void conectandoStreamerBot(){
        println("rodando");
        String endpoint = "DoAction";
        println(endpoint);

        final HttpClient httpClient = HttpClient.newHttpClient();

//        Action action = new Action("184970e3-cafa-482f-8e52-d47ced640ced", "TESTE123");
        Action action = new Action("d7b92e38-4d44-4744-ad4e-04489e75d3fc", "FAHH");
        StringBuilder stringBuilder = new StringBuilder();

        JsonMapper jsonMapper = new JsonMapper();

        Map<String, Action> map = Map.of("action", action);
        String jsonAction = jsonMapper.writeValueAsString(map);
        println(map);
        println(jsonAction);



        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://192.168.178.74:7474/"+endpoint))
                .header("Content-Type",  "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonAction))
                .build();


        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            println(response.body());
            println(response.statusCode());
        } catch  (ConnectException e) {
            println("erro de conexão" + e.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}

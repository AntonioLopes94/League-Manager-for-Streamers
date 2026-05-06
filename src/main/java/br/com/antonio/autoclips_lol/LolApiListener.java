package br.com.antonio.autoclips_lol;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.IO.println;
@Component
public class LolApiListener {
    @Scheduled(fixedRate = 1000)
    public void getEventsEverySecond() throws InterruptedException {
        final HttpClient httpClient = HttpClient.newHttpClient();
        final ObjectMapper objectMapper = new ObjectMapper();

        final StreambotApiRequester streambotApiRequester = new StreambotApiRequester();


        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://127.0.0.1:2999/liveclientdata/eventdata"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            FileWriter fileWriter = new FileWriter("teste.txt");
            fileWriter.write(response.body());
            fileWriter.close();
            EventList eventMapped = objectMapper.readValue(response.body(), EventList.class);
            println(eventMapped.Events().getLast());
//todo resovler listas vazias com ifs? e null tambem
            if (eventMapped.Events().getLast().EventName().contains("DragonKill") ||
                    eventMapped.Events().getLast().KillerName().contains("Água Gourmet")){
                streambotApiRequester.conectandoStreamerBot();
                println("Antonio matou o dragão tipo " + eventMapped.Events().getLast().DragonType());
            }

        } catch (ConnectException e) {
            println("Sem partidas acontecendo");
            try {
                Thread.sleep(120);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){//todo confirmar se isso adianta para resolver o null exception
            println("Null point exception");
        }




    }
}

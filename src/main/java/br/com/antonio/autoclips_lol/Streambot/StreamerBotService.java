package br.com.antonio.autoclips_lol.Streambot;

import org.springframework.stereotype.Service;

import static java.lang.IO.println;

@Service
public class StreamerBotService {

    private final StreamerBotApiClient streamerBotApiClient;

    public StreamerBotService(StreamerBotApiClient streamerBotApiClient) {
        this.streamerBotApiClient = streamerBotApiClient;
    }

    public void teste(){
       println("entrou no teste do streamer bot connection");
        Action action = new Action("184970e3-cafa-482f-8e52-d47ced640ced", "TESTE123");
//        Action action = new Action("9d5cbfb1-5b49-4716-b3b9-fc021b52dd61", "!clip");
//        Action action = new Action("d7b92e38-4d44-4744-ad4e-04489e75d3fc", "FAHH");
        streamerBotApiClient.doAction(action);
    }

    public void doActionService(Action action){
        streamerBotApiClient.doAction(action);
    }
}

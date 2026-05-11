package br.com.antonio.autoclips_lol.Streambot;

public class StreamerBotService {

    StreamerBotApiClient streamerBotApiClient;
    public void teste(){
        Action action = new Action("d7b92e38-4d44-4744-ad4e-04489e75d3fc", "FAHH");
        streamerBotApiClient.doAction(action);
    }
}

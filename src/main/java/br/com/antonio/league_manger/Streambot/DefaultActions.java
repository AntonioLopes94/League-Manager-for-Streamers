package br.com.antonio.league_manger.Streambot;

import lombok.Getter;

@Getter
public enum DefaultActions {
    CLIPTWITCH("clipTwitch"),
    ELO("!elo"),
    TESTE("TESTE123");

    private final String actionName;
    DefaultActions(String actionName) {
        this.actionName = actionName;
    }
}

package br.com.antonio.autoclips_lol.Streambot;

import lombok.Getter;

@Getter
public enum DefaultActions {
    CLIP("!clip"),
    ELO("!elo"),
    TESTE("TESTE123");

    private final String actionName;
    DefaultActions(String actionName) {
        this.actionName = actionName;
    }
}

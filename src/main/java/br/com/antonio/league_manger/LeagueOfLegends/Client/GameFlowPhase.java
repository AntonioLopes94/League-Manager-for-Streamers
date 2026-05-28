package br.com.antonio.league_manger.LeagueOfLegends.Client;

public final class GameFlowPhase {

    private GameFlowPhase() {
    }

    public static final String NONE = "None";
    public static final String LOBBY = "Lobby";
    public static final String MATCHMAKING = "Matchmaking";
    public static final String READY_CHECK = "ReadyCheck";
    public static final String CHAMP_SELECT = "ChampSelect";
    public static final String GAME_START = "GameStart";
    public static final String IN_PROGRESS = "InProgress";
    public static final String WAITING_FOR_STATS = "WaitingForStats";
    public static final String PRE_END_OF_GAME = "PreEndOfGame";
    public static final String END_OF_GAME = "EndOfGame";
}
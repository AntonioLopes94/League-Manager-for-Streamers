package br.com.antonio.league_manger.LeagueOfLegends.Client;

public record LockFile(
        String name,
        String pid,
        String port,
        String password,
        String protocol
) {
    public static LockFile fromString(String content){
        String[] parts = content.split(":");

        return new LockFile(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4]
        );
    }

}

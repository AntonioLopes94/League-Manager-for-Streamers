package br.com.antonio.autoclips_lol.Config;

import br.com.antonio.autoclips_lol.LeagueOfLegends.Client.LockFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.IO.println;

public record GlobalVariables(
        @JsonProperty
        String clientPath,
        @JsonProperty
        String leagueOfLegendsBaseUrl,
        @JsonProperty
        String streamerBotBaseUrl
) {
    public static GlobalVariables fromJsonFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File("GlobalVariables.json"), GlobalVariables.class);
        }catch (Exception e){
            throw new RuntimeException("Erro ao mapear o Json", e);
        }
    }

    public LockFile lockFile() {
        Path lockfilePath = Path.of(clientPath, "lockfile");

        try {
            String content = Files.readString(lockfilePath).trim();
            return LockFile.fromString(content);
        } catch (IOException _) {
            return new LockFile("LeagueClientUx",
                    "0",
                    "29999",
                    "placeholder",
                    "https");
        }
    }
}

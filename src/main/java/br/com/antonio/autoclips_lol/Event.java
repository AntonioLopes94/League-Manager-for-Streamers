package br.com.antonio.autoclips_lol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Event(
        Integer EventID,
        List<String> Assisters,
        String EventName,
        Double EventTime,
        String KillerName,
        String VictimName,
        String DragonType,
        Boolean Stolen
) {
}

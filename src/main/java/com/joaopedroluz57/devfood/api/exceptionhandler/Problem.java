package com.joaopedroluz57.devfood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    private final Integer status;
    private final String type;
    private final String title;
    private final String detail;
    private final String userMessage;
    private final LocalDateTime timestamp;
    private final List<Field> fields;

    @Getter
    @Setter
    @Builder
    public static class Field {

        private String name;
        private String userMessage;
    }

}

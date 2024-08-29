package co.zelez.core.command.reader.entity;

import lombok.*;

@Getter
@Builder
public class Param {
    private String command;
    private String[] args;
}

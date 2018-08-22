package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationExecutionSatusDto {
    private final String status;

    public OperationExecutionSatusDto() {
        status = "ok";
    }
}

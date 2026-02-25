package com.intouchup.RevaToDo.reqDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotNull
    private LocalDateTime fromTime;
    @NotNull
    private LocalDateTime toTime;
}

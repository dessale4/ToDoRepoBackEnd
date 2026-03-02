package com.intouchup.RevaToDo.reqDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intouchup.RevaToDo.entity.Task;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubTaskDTO {
    @NotNull
    private Integer taskId;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime fromTime;
    @NotNull
    private LocalDateTime toTime;
}

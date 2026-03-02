package com.intouchup.RevaToDo.controller;

import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.reqDTO.SubTaskDTO;
import com.intouchup.RevaToDo.service.SubTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/subTask")
public class SubTaskController {
    private final SubTaskService subTaskService;
    @PostMapping("/addNewSubTask")
    public ResponseEntity<?> addNewSubTask(@RequestBody @Validated SubTaskDTO subTaskDTO){
        Task updatedTask = subTaskService.addSubTask(subTaskDTO);
        return ResponseEntity.ok(updatedTask);
    }
    @DeleteMapping("/deleteSubTask")
    public ResponseEntity<?> deleteSubTask(@RequestParam String subTaskId) {
        subTaskService.deleteSubTask(Integer.valueOf(subTaskId));
        return ResponseEntity.ok("SubTask Deleted Successfully");
    }
}

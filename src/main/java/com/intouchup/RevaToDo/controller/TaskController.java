package com.intouchup.RevaToDo.controller;

import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.reqDTO.TaskDTO;
import com.intouchup.RevaToDo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/toDoes")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping("/task")
    public ResponseEntity<?> addTask(@RequestBody @Validated TaskDTO taskDTO, Principal principal){

        List<Task> userTasks = taskService.addTask(taskDTO, principal);

        return ResponseEntity.ok(userTasks);
    }
    @GetMapping("/tasks")
    public ResponseEntity<?> getUserTasks(Principal principal){
        List<Task> userTasks = taskService.getUserTasks(principal);
        return ResponseEntity.ok(userTasks);
    }
}

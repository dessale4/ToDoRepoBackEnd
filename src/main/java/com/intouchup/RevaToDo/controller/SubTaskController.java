package com.intouchup.RevaToDo.controller;

import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.reqDTO.SubTaskDTO;
import com.intouchup.RevaToDo.reqDTO.TaskDTO;
import com.intouchup.RevaToDo.service.SubTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @PutMapping("/editSubTask")
    public ResponseEntity<?> editSubTask(@RequestBody @Validated SubTaskDTO subtaskDTO, @RequestParam String subTaskId, Principal principal) {
        subTaskService.editSubTask(subtaskDTO, Integer.valueOf(subTaskId), principal.getName());
        return ResponseEntity.ok("SubTask Edited Successfully");
    }
    @PostMapping("/toggleSubTaskCompletion")
    public ResponseEntity<?> toggleSubTaskCompletion(  @RequestParam Boolean subTaskStatus, @RequestParam String subTaskId, @RequestParam String taskId, Principal principal) {
        subTaskService.toggleSubTaskCompletion(subTaskStatus, Integer.valueOf(subTaskId), Integer.valueOf(taskId), principal.getName());
        return ResponseEntity.ok("SubTask Status Changed Successfully");
    }
    @DeleteMapping("/deleteSubTask")
    public ResponseEntity<?> deleteSubTask(@RequestParam String subTaskId) {
        subTaskService.deleteSubTask(Integer.valueOf(subTaskId));
        return ResponseEntity.ok("SubTask Deleted Successfully");
    }
}

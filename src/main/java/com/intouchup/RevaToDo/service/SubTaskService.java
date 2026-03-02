package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.entity.SubTask;
import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.repository.SubTaskRepository;
import com.intouchup.RevaToDo.repository.TaskRepository;
import com.intouchup.RevaToDo.reqDTO.SubTaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    public Task addSubTask(SubTaskDTO subTaskDTO){
        Task task = taskRepository.findById(subTaskDTO.getTaskId()).orElseThrow(() -> new RuntimeException("The required task does not exist."));
        SubTask newSubTask = SubTask.builder()
                .task(task)
                .name(subTaskDTO.getName())
                .done(false)
                .fromTime(subTaskDTO.getFromTime())
                .toTime(subTaskDTO.getToTime())
                .build();
        subTaskRepository.save(newSubTask);
        return taskRepository.findById(subTaskDTO.getTaskId()).get();

    }
    public void deleteSubTask(Integer subTaskId){
        SubTask subTaskTobeDeleted = subTaskRepository.findById(subTaskId).orElseThrow(() -> new RuntimeException("SubTask not found."));
        subTaskRepository.delete(subTaskTobeDeleted);
    }

}

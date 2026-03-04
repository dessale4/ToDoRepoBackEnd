package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.entity.SubTask;
import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.repository.SubTaskRepository;
import com.intouchup.RevaToDo.repository.TaskRepository;
import com.intouchup.RevaToDo.reqDTO.SubTaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;
    private final TaskRepository taskRepository;

    public Task addSubTask(SubTaskDTO subTaskDTO){
        Task task = taskRepository.findById(subTaskDTO.getTaskId()).orElseThrow(() -> new RuntimeException("The required task does not exist."));
        task.setDone(false);
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

    public void editSubTask(SubTaskDTO subtaskDTO, Integer subTaskId, String userEmail) {
        SubTask existingSubTask = subTaskRepository.findById(subTaskId).orElseThrow(()->new RuntimeException("SubTask Not Found"));
        existingSubTask.setName(subtaskDTO.getName());
        existingSubTask.setFromTime(subtaskDTO.getFromTime());
        existingSubTask.setToTime(subtaskDTO.getToTime());
        subTaskRepository.save(existingSubTask);
    }

    public void toggleSubTaskCompletion(Boolean subTaskStatus, Integer subTaskId, Integer taskId, String name) {
        Task existingTask = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not Found"));
        SubTask existingSubTask = subTaskRepository.findById(subTaskId).orElseThrow(() -> new RuntimeException("Sub task not found."));
        List<SubTask> noneCompletedSubtasks = existingTask.getSubTasks().stream().filter(st -> st.getId() != subTaskId)
                .filter(st -> !st.getDone()).collect(Collectors.toList());
        if(subTaskStatus && noneCompletedSubtasks.size() == 0){
            existingTask.setDone(true);
            taskRepository.save(existingTask);
        }else if(!subTaskStatus && noneCompletedSubtasks.size() == 0 && existingTask.getDone()){
            existingTask.setDone(false);
            taskRepository.save(existingTask);
        }
        existingSubTask.setDone(subTaskStatus);
        subTaskRepository.save(existingSubTask);
    }
}

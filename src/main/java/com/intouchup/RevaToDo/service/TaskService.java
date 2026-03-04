package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.repository.TaskRepository;
import com.intouchup.RevaToDo.repository.UserRepository;
import com.intouchup.RevaToDo.reqDTO.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> addTask(TaskDTO taskDTO, String email) {
        User loggedUser = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Account not Found"));
        Task newTask = Task.builder()
                .name(taskDTO.getName())
                .category(taskDTO.getCategory())
                .fromTime(taskDTO.getFromTime())
                .toTime(taskDTO.getToTime())
                .owner(loggedUser)
                .done(false)
                .build();
        taskRepository.save(newTask);
        return getUserTasks(email);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Task> getUserTasks(String userEmail) {
        List<Task> taskList = taskRepository.findByOwnerEmail(userEmail);
        return taskList;
    }
    public void deleteTask(Integer taskId){
        Task taskTobeDeleted = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found."));
        taskRepository.delete(taskTobeDeleted);
    }

    public void editTask(TaskDTO taskDTO, Integer taskId, String name) {
        Task existingTask = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
        existingTask.setName(taskDTO.getName());
        existingTask.setCategory(taskDTO.getCategory());
        existingTask.setFromTime(taskDTO.getFromTime());
        existingTask.setToTime(taskDTO.getToTime());
        taskRepository.save(existingTask);
    }
}

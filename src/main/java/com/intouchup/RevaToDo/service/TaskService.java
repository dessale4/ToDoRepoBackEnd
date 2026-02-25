package com.intouchup.RevaToDo.service;

import com.intouchup.RevaToDo.entity.Task;
import com.intouchup.RevaToDo.entity.User;
import com.intouchup.RevaToDo.repository.TaskRepository;
import com.intouchup.RevaToDo.repository.UserRepository;
import com.intouchup.RevaToDo.reqDTO.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> addTask(TaskDTO taskDTO, Principal principal) {
        User loggedUser = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Account not Found"));
        Task newTask = Task.builder()
                .name(taskDTO.getName())
                .category(taskDTO.getCategory())
                .fromTime(taskDTO.getFromTime())
                .toTime(taskDTO.getToTime())
                .owner(loggedUser)
                .build();
        taskRepository.save(newTask);
        return getUserTasks(principal);

    }

    public List<Task> getUserTasks(Principal principal){
        return taskRepository.findByOwnerEmail(principal.getName());
    }
}

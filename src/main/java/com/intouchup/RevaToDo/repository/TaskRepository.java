package com.intouchup.RevaToDo.repository;

import com.intouchup.RevaToDo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByOwnerEmail(String email);
}

package com.intouchup.RevaToDo.repository;

import com.intouchup.RevaToDo.entity.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Integer> {
}

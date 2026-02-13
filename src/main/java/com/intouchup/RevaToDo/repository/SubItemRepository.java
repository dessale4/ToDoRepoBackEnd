package com.intouchup.RevaToDo.repository;

import com.intouchup.RevaToDo.entity.SubItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubItemRepository extends JpaRepository<SubItem, Integer> {
}

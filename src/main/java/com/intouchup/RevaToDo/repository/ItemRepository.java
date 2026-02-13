package com.intouchup.RevaToDo.repository;

import com.intouchup.RevaToDo.entity.Item;
import com.intouchup.RevaToDo.entity.SubItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}

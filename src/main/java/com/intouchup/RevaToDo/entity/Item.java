package com.intouchup.RevaToDo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Item_TBL")
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "item")
//    @Column(name = "subItems")
    private List<SubItem> subItems;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
}

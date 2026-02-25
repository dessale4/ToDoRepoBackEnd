package com.intouchup.RevaToDo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Item_TBL")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
//    @JsonManagedReference //to prevent cyclic dependence
    private List<SubTask> subTasks;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private Boolean done;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime  createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime  lastModifiedDate;
}

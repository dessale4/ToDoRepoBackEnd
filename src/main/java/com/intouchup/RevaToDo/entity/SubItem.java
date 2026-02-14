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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SubItem_TBL")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class SubItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    private Item item;
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

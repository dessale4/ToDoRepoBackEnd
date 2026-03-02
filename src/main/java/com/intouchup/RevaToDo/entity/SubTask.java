package com.intouchup.RevaToDo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore//Prevents cyclic dependence during object serialization using @ResponseBody in an api return
    private Task task;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private Boolean done;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime  createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime  lastModifiedDate;
    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", done=" + done +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

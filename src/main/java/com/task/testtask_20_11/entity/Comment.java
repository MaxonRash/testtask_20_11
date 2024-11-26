package com.task.testtask_20_11.entity;

import com.task.testtask_20_11.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    public Comment(String text, Task task) {
        this.text = text;
        this.createdAt = Calendar.getInstance();
        this.task = task;
    }

    public Comment(String text) {
        this.text = text;
        this.createdAt = Calendar.getInstance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return id.equals(comment.id) && text.equals(comment.text) && createdAt.equals(comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createdAt);
    }
}

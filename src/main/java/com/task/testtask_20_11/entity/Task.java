package com.task.testtask_20_11.entity;

import com.task.testtask_20_11.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="heading")
    private String heading;
    @Column(name ="description")
    private String description;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;
    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
    @ManyToMany
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> implementers;

    public Task(String heading, String description, Priority priority, List<Comment> comments, List<User> implementers) {
        this.heading = heading;
        this.description = description;
        this.createdAt = Calendar.getInstance();
        this.status = Status.WAITING;
        this.priority = priority;
        this.comments = comments;
        this.author = null;
        this.implementers = implementers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return id.equals(task.id) && heading.equals(task.heading) && createdAt.equals(task.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heading, createdAt);
    }
}

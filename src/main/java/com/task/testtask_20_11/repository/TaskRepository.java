package com.task.testtask_20_11.repository;

import com.task.testtask_20_11.entity.Task;
import com.task.testtask_20_11.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findTasksByAuthor_Email(Pageable pageable, String email);
    Page<Task> findTasksByImplementersContainingIgnoreCase(Pageable pageable, User implementer);
    Page<Task> findTasksByAuthor_EmailAndImplementersContainingIgnoreCase (Pageable pageable, String authorEmail, User implementer);
}

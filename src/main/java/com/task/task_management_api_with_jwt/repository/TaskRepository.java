package com.task.task_management_api_with_jwt.repository;

import com.task.task_management_api_with_jwt.entity.Task;
import com.task.task_management_api_with_jwt.security.entity.User;
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

package com.task.testtask_20_11.security.service;

import com.task.testtask_20_11.security.entity.Role;
import com.task.testtask_20_11.security.entity.User;
import com.task.testtask_20_11.security.repository.UserRepository;
import com.task.testtask_20_11.exception.UserWithThisEmailAlreadyExistsException;
import com.task.testtask_20_11.exception.UserWithThisEmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save user
     *
     * @return saved {@link User}
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Create user
     *
     * @return created {@link User}
     */
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserWithThisEmailAlreadyExistsException();
        }
        return save(user);
    }

    /**
     * Get user by email
     *
     * @return {@link User}
     */
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserWithThisEmailNotFoundException::new);

    }

    /**
     * Get user
     * <p>
     * For Spring Security
     *
     * @return {@link UserDetailsService}
     */
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    /**
     * Get current User
     *
     * @return current {@link User}
     */
    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }


    /**
     * Grant admin role for current user
     * <p>
     * For demonstration
     */
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}

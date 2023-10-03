package org.marjoriekohn.diningreview.repository;

import org.marjoriekohn.diningreview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Manages database interactions for User entities.
 * UserRepository facilitates CRUD (Create, Read, Update, Delete) operations on User
 * entities within the database. By extending JpaRepository, it inherits a broad set of
 * database functionalities. This repository plays a crucial role in user management,
 * particularly during authentication, by allowing the retrieval of user details via their username.
 *
 * @see User
 * @see org.marjoriekohn.diningreview.service.UserService
 * @see org.marjoriekohn.diningreview.controller.UserController
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieves a User entity by username.
     * This method conducts a search in the database to locate a User entity associated
     * with the specified username. If the user doesn't exist, an empty Optional is returned.
     * This is particularly useful in authentication and user management operations.
     *
     * @param username the unique username of the user, required
     * @return an {@code Optional<User>} that may contain the found User entity or be empty if the user doesn't exist
     *
     * @see User
     * @see org.marjoriekohn.diningreview.service.UserService#findByUsername(String)
     */
    Optional<User> findByUsername(String username);
}
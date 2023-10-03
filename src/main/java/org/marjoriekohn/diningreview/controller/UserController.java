package org.marjoriekohn.diningreview.controller;

import jakarta.validation.Valid;
import org.marjoriekohn.diningreview.dto.UserDTO;
import org.marjoriekohn.diningreview.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Manages RESTful API endpoints for User-related CRUD operations.
 * The UserController class maps to the {@code /users} URI and provides endpoints for
 * creating, reading, and updating users. It leverages the UserService class for
 * business logic and data manipulation, thereby maintaining a clean separation of
 * concerns. The endpoints are designed to return appropriate HTTP status codes and
 * messages.
 *
 * @see org.marjoriekohn.diningreview.service.UserService
 * @see org.marjoriekohn.diningreview.dto.UserDTO
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Registers a new user.
     * This endpoint maps to a POST request at {@code /users/signup} and utilizes the UserService
     * to register a new user. Errors that happen during the registration process are handled by
     * the UserService.
     *
     * @param userDTO the Data Transfer Object containing user details, required
     * @return a string indicating the result of the operation {@code "User created successfully."}
     *
     * @see org.marjoriekohn.diningreview.service.UserService#createUser(UserDTO)
     */
    @PostMapping("signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }
    
    /**
     * Retrieves user details based on username.
     * This endpoint maps to a GET request at {@code /users/{username}} and utilizes the UserService
     * to fetch the user's details. Errors that happen during the retrieval process are handled by
     * the UserService.
     *
     * @param username the username of the user to retrieve, required
     * @return the user's details of the user with the specified username
     *
     * @see org.marjoriekohn.diningreview.service.UserService#findByUsername(String)
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        UserDTO foundUser = userService.findByUsername(username);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }
    
    /**
     * Updates an existing user's information.
     * This endpoint maps to a PUT request at {@code /users/{username}/update} and uses the UserService
     * to modify the user's details based on the provided UserDTO. Errors that happen during the update
     * process are handled by the UserService.
     *
     * @param username the username of the user to update, required
     * @param userDTO the Data Transfer Object containing the updated user details, required
     * @return the result of the operation {@code "User updated successfully."}
     *
     * @see org.marjoriekohn.diningreview.service.UserService#updateUser(String, UserDTO)
     */
    @PutMapping("/{username}/update")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        userService.updateUser(username, userDTO);
        return new ResponseEntity<>("User updated successfully.", HttpStatus.OK);
    }
}
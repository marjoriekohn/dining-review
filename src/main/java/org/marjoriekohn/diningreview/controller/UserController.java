package org.marjoriekohn.diningreview.controller;

import jakarta.validation.Valid;
import org.marjoriekohn.diningreview.dto.UserDTO;
import org.marjoriekohn.diningreview.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Manages RESTful API endpoints for User-related CRUD operations.
 * The UserController class maps to the "/users" URI and provides endpoints for
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
     * This endpoint maps to a POST request at "/users/signup" and utilizes the UserService
     * to register a new user. It accepts a UserDTO containing the user's details and returns
     * an HTTP response indicating the operation's result.
     *
     * @param userDTO the Data Transfer Object containing user details, required
     * @return a ResponseEntity indicating the result of the operation
     */
    @PostMapping("signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        userService.createUser(userDTO);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }
    
    /**
     * Retrieves user details based on username.
     * This endpoint maps to a GET request at "/users/{username}" and utilizes the UserService
     * to fetch the user's details. The returned UserDTO is encapsulated in an HTTP response.
     *
     * @param username the username of the user to retrieve, required
     * @return a ResponseEntity containing the UserDTO of the found user
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        UserDTO foundUser = userService.findByUsername(username);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }
    
    /**
     * Updates an existing user's information.
     * This endpoint maps to a PUT request at "/users/{username}/update" and uses the UserService
     * to modify the user's details based on the provided UserDTO. Upon successful update, it returns
     * an HTTP response indicating the operation's result.
     *
     * @param username the username of the user to update, required
     * @param userDTO the Data Transfer Object containing the updated user details, required
     * @return a ResponseEntity indicating the result of the operation
     */
    @PutMapping("/{username}/update")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        userService.updateUser(username, userDTO);
        return new ResponseEntity<>("User updated successfully.", HttpStatus.OK);
    }
}
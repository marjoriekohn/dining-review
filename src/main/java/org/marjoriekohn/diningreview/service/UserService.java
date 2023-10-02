package org.marjoriekohn.diningreview.service;

import org.marjoriekohn.diningreview.dto.UserDTO;
import org.marjoriekohn.diningreview.entity.User;
import org.marjoriekohn.diningreview.exception.DuplicateUserException;
import org.marjoriekohn.diningreview.exception.UserNotFoundException;
import org.marjoriekohn.diningreview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Manages operations related to User entities.
 * This class offers methods for creating, reading, and updating users. It communicates
 * with the UserRepository to access the database, acting as an intermediary between
 * the controller layer and the repository layer, ensuring a separation of concerns.
 * Additionally, it handles the conversion between User entities and UserDTOs.
 *
 * @see UserRepository
 * @see UserDTO
 * @see User
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Registers a new user.
     * This method processes a UserDTO, verifies if a user with an identical username
     * exists, and if absent, persists the new user to the database. Subsequently,
     * the saved user's details encapsulated in a UserDTO are returned.
     *
     * @param userDTO the Data Transfer Object encompassing user details., required
     * @throws DuplicateUserException if a user with the given username already exists
     * @see UserRepository#findByUsername(String)
     * @see #convertDTOToEntity(UserDTO)
     * @see #convertEntityToDTO(User)
     */
    @Transactional
    public void createUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isPresent()) {
            throw new DuplicateUserException(userDTO.getUsername());
        }
        User user = convertDTOToEntity(userDTO);
        userRepository.save(user);
        convertEntityToDTO(user);
    }
    
    /**
     * Retrieves details of a user based on their username.
     * Fetches a user from the database using the provided username and, if found,
     * returns the user's details inside a UserDTO.
     *
     * @param username the unique identifier for the user, required
     * @return the located user's details within a UserDTO
     * @throws UserNotFoundException if no user with the provided username exists
     *
     * @see UserRepository#findByUsername(String)
     * @see #convertEntityToDTO(User)
     */
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UserNotFoundException(username));
        return convertEntityToDTO(user);
    }
    
    /**
     * Modifies an existing user's details.
     * Takes a username and a UserDTO, retrieves the user from the database, updates
     * their details based on the given UserDTO, and then persists the updated user.
     *
     * @param username the unique identifier for the user, required
     * @param userDTO the Data Transfer Object encompassing user details, required
     * @throws UserNotFoundException if no user with the given username is found
     *
     * @see UserRepository#findByUsername(String)
     * @see #convertDTOToEntity(UserDTO)
     */
    @Transactional
    public void updateUser(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UserNotFoundException(username));
        user.setCity(userDTO.getCity());
        user.setState(userDTO.getState());
        user.setZipcode(userDTO.getZipcode());
        user.setHasPeanutAllergies(userDTO.getHasPeanutAllergies());
        user.setHasEggAllergies(userDTO.getHasEggAllergies());
        user.setHasDairyAllergies(userDTO.getHasDairyAllergies());
        userRepository.save(user);
    }
    
    /**
     * Transforms a User entity into a UserDTO.
     * This method takes a User entity as an input and converts its fields into a
     * UserDTO, which is more suitable for client-side operations. It is especially
     * useful when transmitting user details between the server and client.
     *
     * @param user the User entity fetched from the database, required
     * @return a UserDTO containing the details of the given User entity
     *
     * @see #convertDTOToEntity(UserDTO)
     * @see User
     * @see UserDTO
     */
    public UserDTO convertEntityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setCity(user.getCity());
        userDTO.setState(user.getState());
        userDTO.setZipcode(user.getZipcode());
        userDTO.setHasPeanutAllergies(user.getHasPeanutAllergies());
        userDTO.setHasEggAllergies(user.getHasEggAllergies());
        userDTO.setHasDairyAllergies(user.getHasDairyAllergies());
        return userDTO;
    }
    
    /**
     * Transforms a UserDTO into a User entity.
     * This method takes a UserDTO as an input and converts its fields into a
     * User entity, making it suitable for database operations. This is particularly
     * useful for persisting user details to the database.
     *
     * @param userDTO the Data Transfer Object containing user details from the client-side, required
     * @return a user containing the details of the given UserDTO
     * @throws IllegalArgumentException if the provided UserDTO is invalid
     *
     * @see #convertEntityToDTO(User)
     * @see User
     * @see UserDTO
     */
    public User convertDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setCity(userDTO.getCity());
        user.setState(userDTO.getState());
        user.setZipcode(userDTO.getZipcode());
        user.setHasPeanutAllergies(userDTO.getHasPeanutAllergies());
        user.setHasEggAllergies(userDTO.getHasEggAllergies());
        user.setHasDairyAllergies(userDTO.getHasDairyAllergies());
        return user;
    }
}



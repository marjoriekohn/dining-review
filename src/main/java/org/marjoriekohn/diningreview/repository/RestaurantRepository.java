package org.marjoriekohn.diningreview.repository;

import org.marjoriekohn.diningreview.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Manages database interactions for Restaurant entities.
 * The RestaurantRepository interface extends JpaRepository and offers custom query methods
 * for interacting with Restaurant entities. It provides various methods for searching restaurants
 * based on criteria such as zipcode, name, and allergy-specific scores. These methods enable
 * precise data retrieval to enhance the user's search experience.
 *
 * @see org.marjoriekohn.diningreview.entity.Restaurant
 * @see org.marjoriekohn.diningreview.service.RestaurantService
 * @see org.marjoriekohn.diningreview.controller.RestaurantController
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    /**
     * Retrieves all restaurants located in a specific zipcode.
     * This method fetches all Restaurant entities that are situated in the given zipcode.
     *
     * @param zipCode the target zipcode, required
     * @return a list of Restaurant entities located in the specified zipcode
     */
    @Query("SELECT r FROM Restaurant r WHERE r.address.zipCode = :zipCode")
    List<Restaurant> findByZipCode(String zipCode);
    
    /**
     * Searches for restaurants that match a given name.
     * This method fetches Restaurant entities that have the specified name.
     *
     * @param name the restaurant name, required
     * @return a list of Restaurant entities that have the given name
     */
    @Query("SELECT r FROM Restaurant r WHERE r.name = :name")
    List<Restaurant> findByName(String name);
    
    /**
     * Retrieves restaurants that match a given name and are located in a specific zipcode.
     * This method fetches Restaurant entities that have the specified name and are situated
     * in the given zipcode.
     *
     * @param name the restaurant name, required
     * @param zipCode the target zipcode, required
     * @return a list of Restaurant entities that meet the specified criteria
     */
    @Query("SELECT r FROM Restaurant r WHERE r.name = :name AND r.address.zipCode = :zipCode")
    List<Restaurant> findByNameAndZipCode(String name, String zipCode);
}


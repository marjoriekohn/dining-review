package org.marjoriekohn.diningreview.controller;

import jakarta.validation.Valid;
import org.marjoriekohn.diningreview.entity.Restaurant;
import org.marjoriekohn.diningreview.exception.DuplicateRestaurantException;
import org.marjoriekohn.diningreview.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Manages RESTful API endpoints for Restaurant-related CRUD operations.
 * The RestaurantController class is mapped to the "/restaurants" URI and provides
 * endpoints for adding new restaurants, searching restaurants based on specific criteria,
 * and retrieving restaurant details by ID. It interacts with the RestaurantService to
 * carry out these operations.
 *
 * @see org.marjoriekohn.diningreview.service.RestaurantService
 */
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    @Autowired
    RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    /**
     * Adds a new restaurant to the database.
     * This endpoint maps to a POST request at "/restaurants/new" and creates a new restaurant based on
     * the provided Restaurant. The RestaurantService is used to validate and save the new restaurant.
     *
     * @param restaurant an object containing restaurant details, required
     * @return a ResponseEntity containing the created Restaurant
     * @throws DuplicateRestaurantException if a restaurant with the same name and zipcode already exists
     */
    @PostMapping("/new")
    public ResponseEntity<?> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }
    
    /**
     * Retrieves a restaurant by its unique identifier.
     * This endpoint maps to a GET request at "/restaurants/{id}" and utilizes the RestaurantService
     * to find the restaurant by its ID. The found Restaurant is returned in the HTTP response.
     *
     * @param id the Long ID of the restaurant, required
     * @return a ResponseEntity containing the found Restaurant
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
    
    /**
     * Searches restaurants based on zipcode and allergy criteria.
     * This endpoint maps to a GET request at "/restaurants/search?zipcode=12345&allergy=peanut" and returns a list of restaurants
     * that meet the specified zipcode and allergy criteria. The RestaurantService is used to perform
     * the search and filtering.
     *
     * @param zipcode the target zipcode, required
     * @param allergy the allergy criteria, required
     * @return a ResponseEntity containing a list of restaurants that meet the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<?> getRestaurantsByZipcodeAndAllergy(@RequestParam("zipcode") String zipcode, @RequestParam("allergy") String allergy) {
        List<Restaurant> restaurants = restaurantService.searchByZipCodeAndAllergy(zipcode, allergy);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
}
package org.marjoriekohn.diningreview.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

/**
 * Represents a review entity in the application.
 * The Review class captures various attributes of a review, including scores for different
 * types of allergies, comments, and its approval status. It is uniquely identified by an
 * autogenerated ID and is associated with a User and a Restaurant.
 *
 * @see org.marjoriekohn.diningreview.entity.User
 * @see org.marjoriekohn.diningreview.entity.Restaurant
 */

@Entity
@Table(name="review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {
    /**
     * The unique identifier of the review. It is generated automatically when a review is persisted.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The user who created the review. It's a many-to-one relationship between review and user.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    @JsonBackReference(value="user-reviews")
    private User user;
    
    /**
     * The restaurant to which the review pertains. It establishes a many-to-one relationship between review and restaurant.
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference(value="restaurant-reviews")
    private Restaurant restaurant;
    
    /**
     * Score indicating the level of peanuts in the restaurant, from 1 (low) to 5 (high).
     */
    @Column(name = "peanut_score")
    @Range(min = 1, max = 5)
    private Integer peanutScore;
    
    /**
     * Score indicating the level of egg in the restaurant, from 1 (low) to 5 (high).
     */
    @Column(name = "egg_score")
    @Range(min = 1, max = 5)
    private Integer eggScore;
    
    /**
     * Score indicating the level of dairy in the restaurant, from 1 (low) to 5 (high).
     */
    @Column(name = "dairy_score")
    @Range(min = 1, max = 5)
    private Integer dairyScore;
    
    /**
     * Additional comments provided by the user about the restaurant.
     */
    @Column(name = "comments", length = 500)
    private String comments;
    
    /**
     * The current status of the review, it can be either PENDING, APPROVED, or REJECTED.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'PENDING'")
    private Status status = Status.PENDING;
    
    /**
     * Represents the status of a review. The status can be either PENDING, APPROVED, or REJECTED.
     */
    public enum Status {
        
        /**
         * indicates that the review has not been approved or rejected by the admin, default status.
         */
        PENDING,
        /**
         * indicates that the review has been approved by the admin and is visible.
         */
        APPROVED,
        /**
         * indicates that the review has been rejected by the admin and is not visible.
         */
        REJECTED
    }
}
package org.marjoriekohn.diningreview.dto;
import lombok.*;

/**
 * AdminReviewAction is a Data Transfer Object that holds information about the
 * administrator's actions on a review. It contains a review ID and the action status
 * (either APPROVED or REJECTED). This DTO is specifically used when an admin
 * decides to approve or reject a review.
 *
 * @see org.marjoriekohn.diningreview.entity.Review
 * @see org.marjoriekohn.diningreview.service.ReviewService
 */

@Data
@AllArgsConstructor
public class AdminReviewAction {
    private Long reviewId;
    private Status actionStatus;
    
    /**
     * Represents the status of a review. The admin can update the status to APPROVED or REJECTED.
     */
    public enum Status {
        /**
         *  indicates that the review has been approved by the admin and is visible.
         */
        APPROVED,
        /**
         * indicates that the review has been rejected by the admin and is not visible.
         */
        REJECTED
    }
}
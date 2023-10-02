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
public class AdminReviewAction {
    private Long reviewId;
    private Status actionStatus;
    public enum Status {
        APPROVED, REJECTED
    }
}
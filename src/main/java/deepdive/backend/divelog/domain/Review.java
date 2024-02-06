package deepdive.backend.divelog.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Review {

    @Enumerated(value = EnumType.STRING)
    private ReviewType reviewType;
    private String comment;

    public static Review of(String reviewType, String comment) {
        Review review = new Review();
        review.reviewType = ReviewType.valueOf(reviewType);
        review.comment = comment;

        return review;
    }
}

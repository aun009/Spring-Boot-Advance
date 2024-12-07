package com.arun.JobApplication.review;

import com.arun.JobApplication.review.impl.ReviewServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {
    private final ReviewServiceImpl reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(
            @PathVariable Long companyId
    ) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(
            @PathVariable Long companyId,
            @RequestBody Review review
    ) {
        boolean isReviewSaved = reviewService.addReview(companyId, review);
        if (isReviewSaved) {
            return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review Not Saved", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("review/{reviewId}")
    public ResponseEntity<Review> getReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId
    ) {
        return new ResponseEntity<>(reviewService.getReview(companyId, reviewId), HttpStatus.OK);
    }

    @PutMapping("review/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId,
            @RequestBody Review review
    ) {
        boolean isReviewUpdated = reviewService.updateReview(companyId, reviewId, review);
        if(isReviewUpdated) {
            return new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long companyId,
            @PathVariable Long reviewId
    ) {
        boolean isDeleated = reviewService.deleteReview(companyId, reviewId);

        if(isDeleated)
            return new ResponseEntity<>("Review Deleated Successfully", HttpStatus.OK);
        else return new ResponseEntity<>("Review Not deleated", HttpStatus.NOT_FOUND);
    }
}

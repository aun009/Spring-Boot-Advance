package com.arun.reviewms.review.impl;


import com.arun.reviewms.review.Review;
import com.arun.reviewms.review.ReviewRepository;
import com.arun.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        if(companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
         else return false;
    }

    @Override
    public Review getReview(Long reviewId) {
        Review review = reviewRepository.getReviewById(reviewId);
        return review;
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review != null) {
            updatedReview.setId(reviewId);
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(updatedReview);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        // Use `existsById` to check if the review exists
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId); // Use the JpaRepository method `deleteById`
            return true;
        }
        return false; // Return false if the review doesn't exist
    }


}

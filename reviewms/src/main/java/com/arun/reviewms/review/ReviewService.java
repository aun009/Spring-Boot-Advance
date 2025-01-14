package com.arun.reviewms.review;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    boolean addReview(Long companyId, Review review);

    Review getReview(Long reviewId);

    boolean updateReview(Long reviewId, Review updatedReview);

    boolean deleteReview(Long reviewId);

//    boolean deleteReview(Long companyId, Long reviewId);

//    boolean deleteReview(Long reviewId);
}

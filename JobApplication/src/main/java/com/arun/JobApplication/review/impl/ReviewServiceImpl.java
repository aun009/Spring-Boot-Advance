package com.arun.JobApplication.review.impl;

import com.arun.JobApplication.company.Company;
import com.arun.JobApplication.company.impl.CompanyServiceImpl;
import com.arun.JobApplication.review.Review;
import com.arun.JobApplication.review.ReviewRepository;
import com.arun.JobApplication.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CompanyServiceImpl companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyServiceImpl companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }


    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(company != null) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
         else return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        if(companyService.getCompanyById(companyId) != null) {
            updatedReview.setId(reviewId);
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setRating(updatedReview.getRating());
            reviewRepository.save(updatedReview);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        // Check if the company exists
        Company company = companyService.getCompanyById(companyId);
        if (company == null) {
            return false; // Company doesn't exist
        }

        // Check if the review exists
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null || !review.getCompany().getId().equals(companyId)) {
            return false; // Review doesn't exist or doesn't belong to the company
        }

        // Remove review from the company's review list (if bidirectional relationship exists)
        company.getReviews().remove(review);
        review.setCompany(null);

        // Delete the review from the repository
        reviewRepository.delete(review);
        return true;
    }

}

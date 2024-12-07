package com.arun.jobms.job.client;

import com.arun.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE", url = "review-service")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReview(@RequestParam("companyId") Long companyId);
}

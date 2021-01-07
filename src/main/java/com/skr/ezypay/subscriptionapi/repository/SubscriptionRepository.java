package com.skr.ezypay.subscriptionapi.repository;

import com.skr.ezypay.subscriptionapi.dto.SubscriptionDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Subscription details crud
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionDetailsDto, Long> {
}
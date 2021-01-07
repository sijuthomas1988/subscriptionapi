package com.skr.ezypay.subscriptionapi.service;

import com.skr.ezypay.subscriptionapi.exception.ServiceException;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsRequest;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsResponse;

/**
 * Subscription Service class
 */
public interface SubscriptionService {

    /**
     * Creates a subscription order for a customer
     *
     * @param subscriptionDetailsRequest
     *     request object
     * @return subscription response object
     * @throws ServiceException
     *exception thrown
     */
    SubscriptionDetailsResponse createSubscription(SubscriptionDetailsRequest subscriptionDetailsRequest) throws ServiceException;
}

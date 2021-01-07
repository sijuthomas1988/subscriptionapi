package com.skr.ezypay.subscriptionapi.mapper;

import com.skr.ezypay.subscriptionapi.dto.PriceDetailsDto;
import com.skr.ezypay.subscriptionapi.dto.SubscriptionDetailsDto;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsRequest;
import com.skr.ezypay.subscriptionapi.model.SubscriptionStatus;
import com.skr.ezypay.subscriptionapi.model.SubscriptionType;


import java.sql.Date;

import static com.skr.ezypay.subscriptionapi.util.SubscriptionUtil.parseDateFromString;

/**
 * Class that enables to map from request to dto object
 */
public class SubscriptionMapper {

    /**
     * Converts request to dto object
     *
     * @param request
     *     request object
     * @param subscriptionId
     *     subscription id
     * @param subscriptionStatus
     *     subscription status
     * @return dto object
     */
    public static SubscriptionDetailsDto convertSubscriptionRequestToDto(SubscriptionDetailsRequest request,
                                                                         String subscriptionId, SubscriptionStatus subscriptionStatus) {
        SubscriptionDetailsDto subscriptionDetailsDto = new SubscriptionDetailsDto();
        PriceDetailsDto priceDetailsDto = new PriceDetailsDto();
        priceDetailsDto.setAmount(request.getAmount());
        priceDetailsDto.setSubscriptionType(SubscriptionType.valueOf(request.getSubscriptionType()));
        priceDetailsDto.setSubscriptionDetailsDto(subscriptionDetailsDto);
        priceDetailsDto.setStatus(subscriptionStatus.name());
        priceDetailsDto.setInterval(request.getInterval());
        subscriptionDetailsDto.setSubscriptionId(subscriptionId);
        subscriptionDetailsDto.setStartDate(Date.valueOf(parseDateFromString(request.getStartDate())));
        subscriptionDetailsDto.setEndDate(Date.valueOf(parseDateFromString(request.getEndDate())));
        subscriptionDetailsDto.setPriceDetailsDto(priceDetailsDto);
        return subscriptionDetailsDto;
    }
}

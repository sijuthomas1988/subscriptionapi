package com.skr.ezypay.subscriptionapi.service.implementation;

import com.skr.ezypay.subscriptionapi.dto.SubscriptionDetailsDto;
import com.skr.ezypay.subscriptionapi.exception.ServiceException;
import com.skr.ezypay.subscriptionapi.mapper.SubscriptionMapper;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsRequest;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsResponse;
import com.skr.ezypay.subscriptionapi.model.SubscriptionErrorCode;
import com.skr.ezypay.subscriptionapi.model.SubscriptionStatus;
import com.skr.ezypay.subscriptionapi.repository.SubscriptionRepository;
import com.skr.ezypay.subscriptionapi.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.skr.ezypay.subscriptionapi.util.SubscriptionUtil.getSubscriptionDates;
import static com.skr.ezypay.subscriptionapi.util.SubscriptionUtil.validateFromAndToDateRange;

@Service
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionDetailsResponse createSubscription(SubscriptionDetailsRequest subscriptionDetailsRequest) throws ServiceException {
        if (validateFromAndToDateRange(subscriptionDetailsRequest.getStartDate(), subscriptionDetailsRequest.getEndDate(), "3")) {
            String subscriptionId = UUID.randomUUID().toString();
            /**
             * If we need to include recurring payment, we need to have the payment details of the customer
             * and that would determine if the customer can start the subscription or not.
             * {@link SubscriptionStatus} would determine if the transaction is {@link SubscriptionStatus.COMPLETED} or {@link SubscriptionStatus.PENDING}
             * or some other reasons
             * For Instance {@link SubscriptionStatus.PENDING} can be set if the payment is not successful and we can give the customer an option
             * to change his/her payment details
             *
             * For Now, We have set the {@link SubscriptionStatus} to Completed.
             */

            SubscriptionStatus subscriptionStatus = SubscriptionStatus.COMPLETED;

            if (subscriptionStatus.equals(SubscriptionStatus.PENDING)) {
                // Need to get the log info from the Payment module
                String dummyMessage = "Unable to process subscription. Please check Payment details";
                log.info(dummyMessage);
                throw new ServiceException(SubscriptionErrorCode.UNABLE_TO_PROCESS_SUBSCRIPTION_ORDER.getCode(), SubscriptionStatus.CANCELLED.name(), dummyMessage);
            }
            SubscriptionDetailsDto subscriptionDetailsDto = SubscriptionMapper.convertSubscriptionRequestToDto(subscriptionDetailsRequest, subscriptionId, subscriptionStatus);
            List<String> subscriptionDates = getSubscriptionDates(subscriptionDetailsRequest.getStartDate(),
                    subscriptionDetailsRequest.getEndDate(), subscriptionDetailsRequest.getSubscriptionType(),
                    subscriptionDetailsRequest.getInterval());
            subscriptionDetailsDto.getPriceDetailsDto().setIntervalCount(String.valueOf(subscriptionDates.size()));
            subscriptionDetailsDto.setSubscriptionDates(subscriptionDates.toArray(new String[subscriptionDates.size()]));
            if (subscriptionDates.size() > 0) {
                try {
                    this.subscriptionRepository.save(subscriptionDetailsDto);
                    return new SubscriptionDetailsResponse(subscriptionId, subscriptionDetailsRequest.getSubscriptionType(), subscriptionStatus.name(), Arrays.asList(subscriptionDetailsDto.getSubscriptionDates()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new ServiceException(SubscriptionErrorCode.REPOSITORY_ERROR.getCode(), SubscriptionStatus.CANCELLED.name(), e.getMessage());
                }
            } else {
                String message = "Unable to process subscription as we are unable to find any subscription dates";
                log.info(message);
                throw new ServiceException(SubscriptionErrorCode.INTERNAL_SERVICE_ERROR.getCode(), SubscriptionStatus.CANCELLED.name(), message);
            }
        } else {
            String message = "Unable to process subscription order. Please check from and to dates";
            log.info(message);
            throw new ServiceException(SubscriptionErrorCode.UNABLE_TO_PROCESS_SUBSCRIPTION_ORDER.getCode(), SubscriptionStatus.CANCELLED.name(), message);
        }
    }
}

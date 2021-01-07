package com.skr.ezypay.subscriptionapi.service.implementation;

import com.skr.ezypay.subscriptionapi.dto.SubscriptionDetailsDto;
import com.skr.ezypay.subscriptionapi.exception.ServiceException;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsRequest;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsResponse;
import com.skr.ezypay.subscriptionapi.model.SubscriptionStatus;
import com.skr.ezypay.subscriptionapi.repository.SubscriptionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.session.SessionRepositoryUnavailableException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
public class SubscriptionServiceImplTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void testCreateSubscription_Success() throws ServiceException {
        SubscriptionDetailsRequest subscriptionDetailsRequest = createOrderRequestObject();
        Mockito.when(this.subscriptionRepository.save(any())).thenReturn(Mockito.mock(SubscriptionDetailsDto.class));

        SubscriptionDetailsResponse subscriptionDetailsResponse =
                this.subscriptionService.createSubscription(subscriptionDetailsRequest);
        Assert.assertNotNull(subscriptionDetailsResponse);
        Assert.assertEquals(subscriptionDetailsResponse.getSubscriptionStatus(), SubscriptionStatus.COMPLETED.name());
        Assert.assertNotNull(subscriptionDetailsResponse.getSubscriptionId());
    }

    @Test(expected = ServiceException.class)
    public void testCreateSubscription_RepositoryException() throws ServiceException {
        SubscriptionDetailsRequest subscriptionDetailsRequest = createOrderRequestObject();
        Mockito.when(this.subscriptionRepository.save(any())).thenThrow(SessionRepositoryUnavailableException.class);

        SubscriptionDetailsResponse subscriptionDetailsResponse =
                this.subscriptionService.createSubscription(subscriptionDetailsRequest);
    }

    @Test(expected = ServiceException.class)
    public void testCreateSubscription_EmptySubscriptionDates() throws ServiceException {
        SubscriptionDetailsRequest subscriptionDetailsRequest = createOrderRequestObject();
        subscriptionDetailsRequest.setStartDate("10/08/2020");
        subscriptionDetailsRequest.setEndDate("10/08/2020");
        SubscriptionDetailsResponse subscriptionDetailsResponse =
                this.subscriptionService.createSubscription(subscriptionDetailsRequest);
    }

    @Test(expected = ServiceException.class)
    public void testCreateSubscription_ValidateDatesException() throws ServiceException {
        SubscriptionDetailsRequest subscriptionDetailsRequest = createOrderRequestObject();
        subscriptionDetailsRequest.setStartDate("10/03/2020");
        subscriptionDetailsRequest.setEndDate("10/08/2020");
        SubscriptionDetailsResponse subscriptionDetailsResponse =
                this.subscriptionService.createSubscription(subscriptionDetailsRequest);

    }

    private SubscriptionDetailsRequest createOrderRequestObject() {
        SubscriptionDetailsRequest subscriptionDetailsRequest = new SubscriptionDetailsRequest();
        subscriptionDetailsRequest.setAmount("10");
        subscriptionDetailsRequest.setSubscriptionType("MONTHLY");
        subscriptionDetailsRequest.setEndDate("10/10/2020");
        subscriptionDetailsRequest.setStartDate("10/08/2020");
        subscriptionDetailsRequest.setInterval("20");
        return subscriptionDetailsRequest;
    }
}
package com.skr.ezypay.subscriptionapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skr.ezypay.subscriptionapi.model.ErrorDetails;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsRequest;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsResponse;
import com.skr.ezypay.subscriptionapi.model.SubscriptionStatus;
import com.skr.ezypay.subscriptionapi.model.SubscriptionType;
import com.skr.ezypay.subscriptionapi.repository.SubscriptionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionRepositoryUnavailableException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubscriptionControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SubscriptionRepository subscriptionRepository;


    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void createSubscription_Success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String exampleOrderInfo = objectMapper.writeValueAsString(createOrderRequestObject());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/v1/createSubscription")
            .accept(MediaType.APPLICATION_JSON).content(exampleOrderInfo)
            .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        SubscriptionDetailsResponse subscriptionDetailsResponse = objectMapper.readValue(response.getContentAsString(), SubscriptionDetailsResponse.class);
        Assert.assertEquals("200 http status returned", 201, status);
        Assert.assertNotNull(response);
        Assert.assertEquals(subscriptionDetailsResponse.getSubscriptionType(), SubscriptionType.MONTHLY.name());
        Assert.assertEquals(subscriptionDetailsResponse.getSubscriptionStatus(), SubscriptionStatus.COMPLETED.name());
    }


    @Test
    public void createSubscription_Failure() throws Exception {
        doThrow(SessionRepositoryUnavailableException.class).when(this.subscriptionRepository).save(any());
        ObjectMapper objectMapper = new ObjectMapper();
        String exampleOrderInfo = objectMapper.writeValueAsString(createOrderRequestObject());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/v1/createSubscription")
                .accept(MediaType.APPLICATION_JSON).content(exampleOrderInfo)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        ErrorDetails errorDetails = objectMapper.readValue(response.getContentAsString(), ErrorDetails.class);
        Assert.assertEquals(403, status);
        Assert.assertNotNull(response);
        Assert.assertEquals(errorDetails.getSubscriptionStatus(), SubscriptionStatus.CANCELLED.name());
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
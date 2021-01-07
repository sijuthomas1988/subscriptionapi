package com.skr.ezypay.subscriptionapi.controller;

import com.skr.ezypay.subscriptionapi.exception.ServiceException;
import com.skr.ezypay.subscriptionapi.model.ErrorDetails;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsRequest;
import com.skr.ezypay.subscriptionapi.model.SubscriptionDetailsResponse;
import com.skr.ezypay.subscriptionapi.service.SubscriptionService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class
 */
@RestController
@RequestMapping("/v1")
@Api(value = "Subscription API")
@Slf4j
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @ApiOperation(value = "Saves an object of subscription details of a user in the database",
            notes = "Saves an object of subscription details of a user in the database", tags = {})
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully saved the subscription details object"),
            @ApiResponse(code = 304, message = "Resource was not updated", response = ErrorDetails.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorDetails.class),
            @ApiResponse(code = 403, message = "Resource you are trying to reach is not found/available", response = ErrorDetails.class)})
    @RequestMapping(value = "/createSubscription", produces = {"application/json"}, method = RequestMethod.POST)
    @Timed
    public ResponseEntity<SubscriptionDetailsResponse> saveBookingDetails(@RequestBody SubscriptionDetailsRequest request) throws ServiceException {
        SubscriptionDetailsResponse subscriptionDetailsResponse = this.subscriptionService.createSubscription(request);
        log.info(subscriptionDetailsResponse.getSubscriptionId());
        return new ResponseEntity<SubscriptionDetailsResponse>(subscriptionDetailsResponse, HttpStatus.CREATED);
    }
}
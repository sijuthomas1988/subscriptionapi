package com.skr.ezypay.subscriptionapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum SubscriptionErrorCode {

    UNEXPECTED_ERROR(                       "SP1001", "Unexpected Error Occurred"),
    REPOSITORY_ERROR(                       "SP1002", "Repository Exception Occurred"),
    UNABLE_TO_PROCESS_SUBSCRIPTION_ORDER(   "SP2001", "Unable to Process Subscription Order"),
    INTERNAL_SERVICE_ERROR(                 "SP1003", "Internal Service Error");

    @Getter
    private String code;
    @Getter
    private String message;
}

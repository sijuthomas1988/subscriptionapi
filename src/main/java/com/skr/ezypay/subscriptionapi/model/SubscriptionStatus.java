package com.skr.ezypay.subscriptionapi.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum SubscriptionStatus {

    ONGOING,
    COMPLETED,
    CANCELLED,
    PENDING
}

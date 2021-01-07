package com.skr.ezypay.subscriptionapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "SubscriptionDetailsRequest Model")
public class SubscriptionDetailsRequest {

    @ApiModelProperty(notes = "Subscription Amount")
    private String amount;

    @ApiModelProperty(notes = "Subscription Type")
    private String subscriptionType;

    @ApiModelProperty(notes = "Subscription Interval")
    private String interval;

    @ApiModelProperty(notes = "Subscription Start Date in the format of dd-MM-YYYY")
    private String startDate;

    @ApiModelProperty(notes = "Subscription End Date in the format of dd-MM-YYYY")
    private String endDate;
}

package com.skr.ezypay.subscriptionapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "SubscriptionDetailsResponse Model")
public class SubscriptionDetailsResponse {

    @ApiModelProperty(notes = "Subscription Id")
    private String subscriptionId;

    @ApiModelProperty(notes = "Subscription Type")
    private String subscriptionType;

    @ApiModelProperty(notes = "Subscription Status")
    private String subscriptionStatus;

    @ApiModelProperty(notes = "List of dates that invoices generated for that subscription")
    private List<String> invoiceDates;
}

package com.skr.ezypay.subscriptionapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Model Class for Error Object to be returned during response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Response model for error returned")
public class ErrorDetails {

    /**
     * Time stamp at which error occurred
     */
    @ApiModelProperty(notes = "timestamp at which the error occurred")
    private Date timeStamp;
    /**
     * message to be given as response
     */
    @ApiModelProperty(notes = "error message")
    private String code;
    /**
     * Details of the error message like status code, uri
     */
    @ApiModelProperty(notes = "Details of the error message ")
    private String details;

    @ApiModelProperty(notes = "subscription status")
    private String subscriptionStatus;
}

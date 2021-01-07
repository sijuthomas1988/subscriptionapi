package com.skr.ezypay.subscriptionapi.dto;

import com.skr.ezypay.subscriptionapi.model.SubscriptionType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PRICE_DETAILS")
@Data
public class PriceDetailsDto implements Serializable {

    @Id
    @Column(name = "price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;

    @Column(name = "interval")
    private String interval;

    @Column(name = "interval_count")
    private String intervalCount;

    @Column(name = "amount")
    private String amount;

    @Column(name = "pricing_status")
    private String status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    private SubscriptionDetailsDto subscriptionDetailsDto;
}

package com.skr.ezypay.subscriptionapi.dto;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@TypeDefs({@TypeDef(name = "stringArray", typeClass = StringArrayType.class)})
@Entity
@Table(name = "SUBSCRIPTION_DETAILS")
@Data
public class SubscriptionDetailsDto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Type(type = "stringArray")
    @Column(name = "subscription_dates", columnDefinition = "ARRAY")
    private String[] subscriptionDates;

    @OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "subscriptionDetailsDto")
    private PriceDetailsDto priceDetailsDto;
}

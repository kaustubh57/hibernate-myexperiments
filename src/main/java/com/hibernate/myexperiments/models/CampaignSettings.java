/*
 * Licensed Materials - Property of IBM
 * IBM Experience Designer
 * © Copyright IBM Corporation 2011, 2016
 * U.S. Government Users Restricted Rights:  Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
/**
 * Created by kaustubh on 2/29/16.
 */
package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "campaign_settings")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignSettings implements Tenant, XdModel {

    private static final long serialVersionUID = 7628691094763936564L;

    @Id
    @SequenceGenerator(name = "campaign_settings_seq", sequenceName = "campaign_settings_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_settings_seq")
    private Long id;

    /*
     * we can't just declare ``private Account account;`` in super class because of
     * https://hibernate.atlassian.net/browse/HHH-7635
     * thus all the copy paste
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "campaign_url", length = 1024)
    private String campaignURL;

    @Column(name = "campaign_partition", length = 256)
    private String campaignPartition;

    @Transient
    private String imsPlatformUser;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;
}

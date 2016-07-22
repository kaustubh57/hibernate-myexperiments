package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@ToString(exclude = { "account" })
@Getter
@Setter
@EqualsAndHashCode(of = { "account", "engageOrgId", "engageOrgId2", "ibmCustomerId", "oAuthRefreshToken" })
@Entity
@Table(name = "account_authentication")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountAuthentication implements XdModel {

    private static final long serialVersionUID = -1978491503488549271L;

    /*
     * we can't just declare ``private Account account;`` in super class because of
     * https://hibernate.atlassian.net/browse/HHH-7635
     * thus all the copy paste
     */
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @EmbeddedId
    private Account account;

    /**
     * This is the Silverpop Engage UUID for the Organization.
     * When clients call XD, Silverpop returns this value in the token validation response.
     */
    @Column(name = "organization_id", length = 256)
    @Nullable
    private String engageOrgId;

    @Column(name = "organization_id2")
    @Nullable
    private Long engageOrgId2;

    /**
     * Refresh token used to make calls to Engage.
     */
    @Column(name = "refresh_token", length = 256)
    private String refreshToken;

    @Column(name = "ibm_customer_id")
    @Nullable
    private String ibmCustomerId;

    /**
     * Refresh token used when Journey Designer is acting as OAuth Provider.
     */
    @JsonIgnore // ignore because I don't want anybody to accidentally see what the value is
    @Column(name = "oauth_refresh_token", length = 256)
    @Nullable
    private String oAuthRefreshToken;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

}

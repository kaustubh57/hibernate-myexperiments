package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@ToString(exclude = { "users", "storyboards", "authentication" })
@Getter
@Setter
@EqualsAndHashCode(of = { "accountId" })
@Entity
@Table(name = "account")
@NamedQueries({ @NamedQuery(name = "com.hibernate.myexperiments.models.Account.getByOrganizationId",
        query = "select a from Account a inner join a.authentication as auth where auth.engageOrgId = :organizationId"),
    @NamedQuery(name = "com.hibernate.myexperiments.models.Account.getByOrganizationId2",
        query = "select a from Account a inner join a.authentication as auth where auth.engageOrgId2 = :organizationId2"),
    @NamedQuery(name = "com.hibernate.myexperiments.models.Account.getByIbmCustomerId",
        query = "select a from Account a inner join a.authentication as auth where auth.ibmCustomerId = :ibmCustomerId"),
    @NamedQuery(name = "com.hibernate.myexperiments.models.Account.getByOAuthRefreshToken",
        query = "select a from Account a inner join a.authentication as oauth where oauth.oAuthRefreshToken = :oAuthRefreshToken")})
public class Account implements XdModel {

    private static final long serialVersionUID = 6770813687073065236L;

    @Id
    @NotNull
    @Column(name = "account_id")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    private Long accountId;

    @NotNull
    @Column(name = "name", length = 256)
    private String name;

    //we cannot use "locale" as column name in DB2.
    @Column(name = "language")
    private String language;

    @Column(name = "timezone")
    private String timeZone;

    @Column(name = "currency")
    private String currency;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum accountType;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    @Cascade({ CascadeType.SAVE_UPDATE })
    private AccountAuthentication authentication;

    /**
     * We have to use this pattern of adding @JsonIgnore to getter, and @JsonProperty to setter, in order to implement write-only attributes.
     * This is to prevent returning AccountAuthentication information back to the client, yet allow properly authorized requests to update the value.
     */
    @JsonIgnore
    public AccountAuthentication getAuthentication() {
        return this.authentication;
    }

    @JsonProperty("authentication")
    public void setAuthentication(final AccountAuthentication newAuthentication) {
        this.authentication = newAuthentication;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    private List<AccountEngageSubscription> subscriptions;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Storyboard> storyboards;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

}

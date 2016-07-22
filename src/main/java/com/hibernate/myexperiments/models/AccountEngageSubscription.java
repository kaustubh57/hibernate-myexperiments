package com.hibernate.myexperiments.models;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by pchitre on 7/28/15.
 */
@ToString(exclude = {"account"})
@Getter
@Setter
@EqualsAndHashCode(of = {"account"})
@Entity
@Table(name = "account_engage_subscription")
public class AccountEngageSubscription implements XdModel {

    private static final long serialVersionUID = -197849150784549271L;

    @Id
    @SequenceGenerator(name = "account_engage_seq", sequenceName = "account_engage_subscription_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_engage_seq")
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    @Column(name = "event_type_code",
            length = 256)
    private String eventTypeCode;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts",
            insertable = false,
            updatable = false)
    private Timestamp createTs;

}

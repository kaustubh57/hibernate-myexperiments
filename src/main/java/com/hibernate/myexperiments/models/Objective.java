package com.hibernate.myexperiments.models;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@ToString(exclude = { "account", "storyboard" })
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "objective")
public class Objective implements Tenant, XdModel {

    private static final long serialVersionUID = 8168786009246705243L;

    @Id
    @SequenceGenerator(name = "objective_seq", sequenceName = "objective_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objective_seq")
    private Long id;

    /*
     * we can't just declare ``private Account account;`` in super class because of
     * https://hibernate.atlassian.net/browse/HHH-7635
     * thus all the copy paste
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storyboard_id")
    private Storyboard storyboard;

    @NotNull
    @Column(name = "name", length = 256)
    private String name;

    // TODO turn into enums
    @Column(name = "objective_type_id")
    private Long objectiveTypeId;

    @Column(name = "threshold_min")
    private Integer thresholdMin;

    @Column(name = "threshold_max")
    private Integer thresholdMax;

    @Column(name = "actual")
    private Integer actual;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

}

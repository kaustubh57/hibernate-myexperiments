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
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@ToString(exclude = "account")
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "interaction_type")
public class InteractionType implements Tenant, XdModel {

    private static final long serialVersionUID = 8365691094763609364L;

    @Id
    @SequenceGenerator(name = "interaction_type_seq", sequenceName = "interaction_type_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interaction_type_seq")
    private Long id;

    /*
     * we can't just declare ``private Account account;`` in super class because of https://hibernate.atlassian.net/browse/HHH-7635 thus all the copy paste
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @NotEmpty
    @Column(name = "name", length = 256)
    private String name;

    @NotEmpty
    @Column(name = "file_url", length = 1024)
    private String fileUrl;

    @Column(name = "url", length = 1024)
    private String url;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

    @Override
    public Timestamp getCreateTs() {
        // TODO Auto-generated method stub
        return null;
    }

}

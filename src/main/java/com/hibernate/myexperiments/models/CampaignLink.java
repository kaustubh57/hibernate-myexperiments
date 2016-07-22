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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@ToString(exclude = { "account"})
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "campaign_link")
@NamedQueries({ @NamedQuery(name = "com.hibernate.myexperiments.models.CampaignLink.getByConnectorId",
query = "select count(*) from CampaignLink cl, Interaction it where it.connectorId = :connectorId and cl.linkId = it.id  and cl.linkType = 'Interaction'")})
public class CampaignLink implements Tenant, XdModel {

    private static final long serialVersionUID = -5507549036482840991L;

    @Id
    @SequenceGenerator(name = "campaign_link_seq", sequenceName = "campaign_link_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_link_seq")
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

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @NotNull
    @Column(name = "campaign_id")
    private Long campaignId;

    @NotNull
    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "link_type")
    @Enumerated(EnumType.STRING)
    private CampaignLinkType linkType;

    @Column(name = "canvas_id")
    private String canvasId;

    @Column(name = "storyboard_id")
    private Long storyboardId;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;
}

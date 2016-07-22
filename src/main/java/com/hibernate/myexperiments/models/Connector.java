package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hibernate.myexperiments.utl.XdDateDeserializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by kaustubh on 3/16/16.
 */
@ToString(exclude = {"account", "creator", "storyboard"})
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "account"})
@Entity
@Table(name = "connector")
@JsonIgnoreProperties({"referenceType", "attachmentType", "attachments", "storyboard", "interactions"})
public class Connector implements AttachmentAware<ConnectorAttachment>, Tenant, XdModel {

    private static final long serialVersionUID = 8736691094451809364L;

    @Id
    @SequenceGenerator(name = "connector_seq", sequenceName = "connector_id_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connector_seq")
    private Long id;

    /*
     * we can't just declare ``private Account account;`` in super class
     * because of https://hibernate.atlassian.net/browse/HHH-7635 thus all the copy paste
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storyboard_id", insertable = false, updatable = false)
    private Storyboard storyboard;

    @NotNull
    @Column(name = "storyboard_id")
    private Long storyboardId;

    @NotEmpty
    @Column(name = "canvas_id", length = 256)
    private String canvasId;

    @NotEmpty
    @Column(name = "name", length = 256)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    // enums from ConnectorType
    @NotEmpty
    @Column(name = "connector_type", length = 8)
    private String connectorType;

    @Column(name = "interactions_count")
    private Integer interactionsCount;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "connector", fetch = FetchType.LAZY)
    private List<Interaction> interactions;

    @Lob
    @Column(name = "connector_data")
    private String connectorData;

    @Column(name = "in_market_date")
    @Type(type = "date")
    @JsonDeserialize(using = XdDateDeserializer.class)
    private Date inMarketDate;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "connector", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ConnectorAttachment> attachment;

    @Column(name = "engage_object_id")
    private Long engageObjectId;

    @Column(name = "engage_api_object_url")
    private String engageApiObjectUrl;

    @Column(name = "engage_app_object_url")
    private String engageAppObjectUrl;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "draft_creator_id")
    private User draftCreator;

    @Override
    public List<Attachment> getAttachments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addAttachments(final List<Attachment> newAttachments) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAttachments(final List<Attachment> delmes) {
        // TODO Auto-generated method stub
    }

}

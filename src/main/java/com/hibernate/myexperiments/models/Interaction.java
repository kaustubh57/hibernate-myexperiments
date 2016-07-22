package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hibernate.myexperiments.utl.XdDateDeserializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.val;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@ToString(exclude = {"account", "creator", "storyboard", "connector"})
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "account"})
@Entity
@Table(name = "interaction")
@JsonIgnoreProperties({"referenceType", "attachmentType", "attachments", "connector"})
public class Interaction implements AttachmentAware<InteractionAttachment>, Tenant, XdModel {

    private static final long serialVersionUID = 8365691094763609364L;

    @Id
    @SequenceGenerator(name = "interaction_seq", sequenceName = "interaction_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interaction_seq")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storyboard_id")
    private Storyboard storyboard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connector_id", insertable = false, updatable = false)
    private Connector connector;

    @Column(name = "connector_id")
    private Long connectorId;

    @NotEmpty
    @Column(name = "name", length = 256)
    private String name;

    @NotEmpty
    @Column(name = "canvas_id", length = 256)
    private String canvasId;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "in_market_date")
    @Type(type = "date")
    @JsonDeserialize(using = XdDateDeserializer.class)
    private Date inMarketDate;

    @Column(name = "due_date")
    @Type(type = "date")
    @JsonDeserialize(using = XdDateDeserializer.class)
    private Date dueDate; // http://blog.progs.be/550/java-time-hibernate

    @Column(name = "end_date")
    @Type(type = "date")
    @JsonDeserialize(using = XdDateDeserializer.class)
    private Date endDate;

    // TODO turn into enums
    @Column(name = "repeat_type_id")
    private Long repeatTypeId;

    @Column(name = "duration")
    private Integer duration;

    // TODO turn into enums
    @Column(name = "duration_units_id")
    private Long durationUnitsId;

    @Lob
    @Column(name = "creative_description")
    private String creativeDescription;

    @Lob
    @Column(name = "offer_description")
    private String offerDescription;

    @Column(name = "link", length = 2048)
    private String link;

    @Column(name = "interaction_type")
    private String interactionType;

    @Column(name = "engage_object_id")
    private Long engageObjectId;

    @Column(name = "engage_api_object_url")
    private String engageApiObjectUrl;

    @Column(name = "engage_app_object_url")
    private String engageAppObjectUrl;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "draft_creator_id")
    private User draftCreator;

    @Column(name = "draft_status")
    private String draftStatus;

    @Column(name = "integration_type")
    @Enumerated(EnumType.STRING)
    private IntegrationType integrationType;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interaction", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<InteractionAttachment> attachment;

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

    /**
     * Create a clone of just the simple attributes, aka attributes that don't refer to other models.
     */
    public Interaction createShadowClone() {
        val clone = new Interaction();
        clone.setName(this.getName());
        clone.setCanvasId(this.getCanvasId());
        clone.setDescription(this.getDescription());
        clone.setInMarketDate(this.getInMarketDate());
        clone.setDueDate(this.getDueDate());
        clone.setEndDate(this.getEndDate());
        clone.setRepeatTypeId(this.getRepeatTypeId());
        clone.setDuration(this.getDuration());
        clone.setDurationUnitsId(this.getDurationUnitsId());
        clone.setCreativeDescription(this.getCreativeDescription());
        clone.setOfferDescription(this.getOfferDescription());
        clone.setLink(this.getLink());
        clone.setInteractionType(this.getInteractionType());
        clone.setEngageObjectId(this.getEngageObjectId());
        clone.setEngageApiObjectUrl(this.getEngageApiObjectUrl());
        clone.setEngageAppObjectUrl(this.getEngageAppObjectUrl());
        return clone;
    }
}

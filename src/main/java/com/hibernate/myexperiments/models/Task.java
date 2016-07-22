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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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

@ToString(exclude = { "account", "creator", "storyboard" })
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "task")
@JsonIgnoreProperties({ "referenceType", "attachmentType", "attachments" })
public class Task implements AttachmentAware<TaskAttachment>, Tenant, XdModel {

    private static final long serialVersionUID = 8365691094763609364L;

    @Id
    @SequenceGenerator(name = "task_seq", sequenceName = "task_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
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
    @Column(name = "task_type", length = 50)
    private String taskType;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "storyboard_id")
    private Long storyboardId;

    @Column(name = "due_date")
    @Type(type = "date")
    @JsonDeserialize(using = XdDateDeserializer.class)
    private Date dueDate;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "subject", length = 256)
    private String subject;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

    // updatable is true because we're leaving it up to the app to update the timestamp
    @Column(name = "update_ts", insertable = false, updatable = true)
    private Timestamp updateTs;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task", orphanRemoval = true)
    private List<TaskAttachment> attachment;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task", orphanRemoval = true)
    private List<TaskScope> taskScope;

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

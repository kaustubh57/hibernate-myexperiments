package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@ToString(exclude = { "account", "creator", "parent", "replies" })
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "comments") // because 'comment' is a reserved word in db2
@JsonIgnoreProperties("referenceType")
public class Comment implements AttachmentAware<CommentAttachment>, Tenant, XdModel {

    private static final long serialVersionUID = 7719245864062185011L;

    @Id
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> replies;

    @Lob
    @Column(name = "body")
    private String body;

    @NotNull
    @Column(name = "context_id")
    private Long contextId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "context_id",  insertable = false, updatable = false)
    private Storyboard storyboard;

    @NotNull
    @Column(name = "context_type", length = 30)
    private String contextType;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

    @Transient
    private int replyCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comment", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CommentAttachment> attachment;

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

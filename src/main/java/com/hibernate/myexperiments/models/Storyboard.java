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
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotEmpty;

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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@ToString(exclude = { "account", "creator", "storyboardData",
                      "interactions", "connectors", "comments",
                     "segment", "budget", "goal", "createTs", "updateTs"})
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "storyboard")
@JsonIgnoreProperties({ "contextType", "goals", "segments", "interactions", "comments", "connectors" })
public class Storyboard implements Tenant, XdModel, CommentAware {

    private static final long serialVersionUID = 1156936859018871737L;

    @Id
    @SequenceGenerator(name = "storyboard_seq", sequenceName = "storyboard_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storyboard_seq")
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

    @NotNull
    @Column(name = "is_public")
    private Boolean isPublic;

    @Setter(AccessLevel.NONE)
    // make protected/public when needed
    @OneToMany(mappedBy = "storyboard", fetch = FetchType.LAZY)
    private List<Interaction> interactions;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "storyboard", fetch = FetchType.LAZY)
    private List<Connector> connectors;

    @NotEmpty
    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "segment", length = 256)
    private String segment;

    @Column(name = "goal", length = 256)
    private String goal;

    @Column(name = "budget", length = 256)
    private String budget;

    @Lob
    @Column(name = "storyboard_data")
    private String storyboardData;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

    // updatable is true because we're leaving it up to the app to update the timestamp
    @Column(name = "update_ts", insertable = false, updatable = true)
    private Timestamp updateTs;

    @Override
    public ContextType getContextType() {
        return ContextType.STORYBOARD;
    }

    @OneToMany(mappedBy = "storyboard", fetch = FetchType.LAZY)
    private Set<Comment> comments;

//    @Transient
    @Formula("(select min(i.in_market_date) from xd.interaction i where i.storyboard_id = id)")
//    @Transient
//    @OneToOne
//    @JoinColumnsOrFormulas({
//        @JoinColumnOrFormula(formula = @JoinFormula(value = "(select min(i.inMarketDate) FROM Interaction i " +
//            "WHERE i.storyboard.id = id))", referencedColumnName = "id"))
//    })
//    @JoinColumnOrFormula(formula = @JoinFormula(value = "(select min(i.inMarketDate) FROM Interaction i " +
//        "WHERE i.storyboard_id = id)", referencedColumnName = "id"))
    private Date startDate;

    @Formula("(select count(c.context_id) from xd.comments c where c.context_id=id group by c.context_id)")
    private Long commentCount;

    public Long getCommentCount() {
        if (commentCount == null) {
            commentCount = 0L;
        }
        return commentCount;
    }

    @Transient
    private Date endDate;
}

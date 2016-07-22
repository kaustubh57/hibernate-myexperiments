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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@ToString(exclude = { "account", "oAuthRefreshToken" })
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "accountId", type = "long"))
@Filters(@Filter(name = "tenantFilter", condition = "account_id=:accountId"))
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "account" })
@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "com.hibernate.myexperiments.models.User.getByIbmUniqueId",
                    query = "select u from User u where u.ibmUniqueId = :ibmUniqueId"),
                @NamedQuery(name = "com.hibernate.myexperiments.models.User.getByIbmUserId",
                    query = "select u from User u where u.ibmUserId = :ibmUserId"),
                @NamedQuery(name = "com.hibernate.myexperiments.models.User.getByOAuthRefreshToken",
                    query = "select u from User u where u.oAuthRefreshToken = :oAuthRefreshToken")})
public class User implements Tenant, XdModel {

    private static final long serialVersionUID = 4631553729339798053L;

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    /*
     * we can't just declare ``private Account account;`` in super class because of
     * https://hibernate.atlassian.net/browse/HHH-7635
     * thus all the copy paste
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @NotEmpty
    @Email
    @Column(name = "email", length = 256)
    @Size(min = 3, max = 256)
    private String email;

    @NotEmpty
    @Column(name = "first_name", length = 128)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", length = 128)
    private String lastName;

    @Nullable
    @Column(name = "engage_id", length = 128)
    private Long engageId;

    @Nullable
    @Column(name = "ibm_unique_id", length = 256)
    private String ibmUniqueId;

    // When JD is acting as the OAuth provider, this is the User's refresh token
    @Nullable
    @Column(name = "oauth_refresh_token", length = 256)
    private String oAuthRefreshToken;

    @Nullable
    @Column(name = "ibm_user_id", length = 128)
    private Long ibmUserId;

    @Nullable
    @Column(name = "ims_platform_user_id", length = 256)
    private String imsPlatformUser;

    @Setter(AccessLevel.NONE)
    // insertable and updatable set to false so only the db can control what is stored in there
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Timestamp createTs;

}

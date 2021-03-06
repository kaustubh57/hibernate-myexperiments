package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COMMENT")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties("comment")
public class CommentAttachment extends Attachment {
    private static final long serialVersionUID = 4413531530538249975L;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID", nullable = true)
    private Comment comment;
}

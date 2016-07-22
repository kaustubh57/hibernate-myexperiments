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
@DiscriminatorValue("MESSAGE")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties("message")
public class MessageAttachment extends Attachment {
    private static final long serialVersionUID = 5035860224193965402L;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID", nullable = true)
    private Message message;
}

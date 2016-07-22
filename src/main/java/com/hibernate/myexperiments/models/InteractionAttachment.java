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
@DiscriminatorValue("INTERACTION")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties("interaction")
public class InteractionAttachment extends Attachment {
    private static final long serialVersionUID = -1141926110356900390L;

    @ManyToOne
    @JoinColumn(name = "INTERACTION_ID", nullable = true)
    private Interaction interaction;
}

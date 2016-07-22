package com.hibernate.myexperiments.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by kaustubh on 3/16/16.
 */
@Entity
@DiscriminatorValue("CONNECTOR")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties("connector")
public class ConnectorAttachment extends Attachment {
    private static final long serialVersionUID = -1141926110356900390L;

    @ManyToOne
    @JoinColumn(name = "CONNECTOR_ID", nullable = true)
    private Connector connector;
}

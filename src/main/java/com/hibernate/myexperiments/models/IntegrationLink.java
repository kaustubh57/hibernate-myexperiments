package com.hibernate.myexperiments.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class IntegrationLink {

    private String canvasId;
    private IntegrationType linkType;
    private long remoteLinkId;

    public IntegrationLink(final Object t) {
        if (t instanceof CampaignLink) {
            CampaignLink cl = (CampaignLink) t;
            this.setCanvasId(cl.getCanvasId());
            this.setRemoteLinkId(cl.getCampaignId());
            this.setLinkType(IntegrationType.campaign);
        } else if (t instanceof Interaction) {
            Interaction interaction = (Interaction) t;
            this.setCanvasId(interaction.getCanvasId());
            this.setRemoteLinkId(interaction.getEngageObjectId());
            //if the engage integration type exists (for program) use it else use interaction type for the email,sms,mobile_push
            if (interaction.getIntegrationType() != null) {
                this.setLinkType(interaction.getIntegrationType());
            } else {
                this.setLinkType(IntegrationType.valueOf(interaction.getInteractionType()));
            }
        } else {
            log.error("The object passed is not of the supported type {}", t);
        }
    }
}

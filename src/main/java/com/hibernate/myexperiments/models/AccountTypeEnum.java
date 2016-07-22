package com.hibernate.myexperiments.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * All the possible values that can go into the Account.accountType field.
 */
public enum AccountTypeEnum {
    TRIAL_IBM,
    TRIAL_ENGAGE,
    BASE,
    ADVANCED,
    SUSPENDED;

    private static HashMap<AccountTypeEnum, List<Feature>> featureMap = new HashMap<>();

    static {
        featureMap.put(ADVANCED, getAdvancedFeatures());
        featureMap.put(BASE, getBaseFeatures());
        featureMap.put(TRIAL_ENGAGE, Arrays.asList(Feature.LOGOUT));
        featureMap.put(TRIAL_IBM, Arrays.asList(Feature.MANAGE_USERS, Feature.FEEDBACK));

    }

    private static List<Feature> getAdvancedFeatures() {
        return Arrays.asList(Feature.ENGAGE_INTEGRATION, Feature.APPROVAL_REQUESTS,
                             Feature.WORK_REQUESTS, Feature.MARKUP, Feature.LOGOUT, Feature.CAMPAIGN_INTEGRATION);
    }

    private static List<Feature> getBaseFeatures() {
        return Arrays.asList(Feature.ENGAGE_INTEGRATION, Feature.LOGOUT,
                             Feature.APPROVAL_REQUESTS, Feature.WORK_REQUESTS, Feature.CAMPAIGN_INTEGRATION);
    }

    public boolean isFeatureEnabled(final Feature feature) {
        return (!featureMap.get(this).contains(feature));
    }

    public List<Feature> getEnabledFeatures() {
        return featureMap.get(this);
    }
}

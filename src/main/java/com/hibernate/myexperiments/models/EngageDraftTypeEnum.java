package com.hibernate.myexperiments.models;

public enum EngageDraftTypeEnum {
     EMAIL (0L) ,
     SMS (1L) ,
     MOBILE_PUSH (2L);

    private final long value;

    EngageDraftTypeEnum(final long value) {
        this.value = value;
    }

    public long longValue() {
        return value;
    }
}

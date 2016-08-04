package com.hibernate.myexperiments.custom.convertor;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by kaustubh on 7/22/16.
 */
public class SQLConverterWithSchema {

    @PreUpdate
    @PrePersist
    public Object convert(Object value) {
        return (value);
    }

}

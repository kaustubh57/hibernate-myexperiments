package com.hibernate.myexperiments.models;

import java.io.Serializable;
import java.sql.Timestamp;


public interface XdModel extends Serializable {
    Timestamp getCreateTs();
}

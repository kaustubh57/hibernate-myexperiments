package com.hibernate.myexperiments.models;


public interface Tenant {
    Account getAccount();
    void setAccount(final Account newAccount);
}

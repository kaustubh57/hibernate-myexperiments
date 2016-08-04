package com.hibernate.myexperiments.utl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * Created by kaustubh on 7/22/16.
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class CriteriaOptions {

    protected int firstResult;
    protected String order;
    protected String orderBy;
    protected String searchTxt;
    protected String searchColName;
    protected String filters;
    protected List<String> subFiltMarketSegValues = new ArrayList<>();
    protected List<String> subFiltGoalValues = new ArrayList<>();
    protected List<String> columns = new ArrayList<>();
    protected boolean cardView;

    public static final String ASCENDING = "ASC";
    public static final String DESCENDING = "DESC";

    public CriteriaOptions(final int firstResult, final String order, final String orderBy, final String searchText, final String searchColName,
                           final List<String> columns, final String filters, final List<String> subFiltMarketSegValues, final List<String> subFiltGoalValues,
                           final boolean cardView) {
        this.firstResult = firstResult;
        this.order = order;
        this.orderBy = orderBy;
        this.searchTxt = searchText;
        this.searchColName = searchColName;
        this.filters = filters;
        this.columns = columns;
        this.subFiltMarketSegValues = subFiltMarketSegValues;
        this.subFiltGoalValues = subFiltGoalValues;
        this.cardView = cardView;
    }

    public boolean isEmpty() {
        return (Strings.isNullOrEmpty(order) && Strings.isNullOrEmpty(orderBy) && Strings.isNullOrEmpty(searchTxt) && Strings.isNullOrEmpty(searchColName)
                && Strings.isNullOrEmpty(filters) && columns.isEmpty());
    }

    public boolean isSearchingForText() {
        return !(Strings.isNullOrEmpty(getSearchColName()) || Strings.isNullOrEmpty(getSearchTxt()));
    }
}

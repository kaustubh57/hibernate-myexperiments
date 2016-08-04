package com.hibernate.myexperiments.utl;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustubh on 7/22/16.
 */
public final class CriteriaUtil {

    public static List<String> getColumns(final String columnString) {
        List<String> columns = new ArrayList<String>();
        String[] cols = columnString.split(",");
        for (int i = 0; i < cols.length; i++) {
            if (!Strings.isNullOrEmpty(cols[i])) {
                columns.add(cols[i].trim());
            }
        }
        return columns;

    }
}

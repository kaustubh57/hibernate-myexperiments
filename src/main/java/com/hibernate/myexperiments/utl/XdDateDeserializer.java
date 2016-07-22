package com.hibernate.myexperiments.utl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * XdDateDeserializer will convert json date in YYYY-MM-DD format to java.util.Date not considering timezone
 *
 */
public class XdDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        Date d = null;
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            if (str.length() == 0) {
                return null;
            }
            d = parseDate(str);
        } else if (t == JsonToken.VALUE_NUMBER_INT) {
            d = new Date(jp.getLongValue());
        }
        return d;
    }

    public static Date parseDate(final String str) {
        // date format is 2015-04-15
        String[] date = str.substring(0, 10).split("-");
        Integer year = Integer.valueOf(date[0]);
        Integer month = Integer.valueOf(date[1]);
        Integer day = Integer.valueOf(date[2]);
        Date d = new Date(year.intValue() - 1900, month.intValue() - 1, day.intValue());
        return d;
    }
}

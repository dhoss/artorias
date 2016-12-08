package com.artorias.util;

/**
 * Created by devin on 12/3/16.
 */

import org.joda.time.DateTime;
import org.jooq.Converter;

import java.sql.Timestamp;

/**
 * Shamelessly stolen from https://github.com/benjamin-bader/droptools/tree/master/dropwizard-jooq
 */
public class JooqJodaDateTimeConverter implements Converter<Timestamp, DateTime> {
    @Override
    public DateTime from(Timestamp timestamp) {
        return new DateTime(timestamp.getTime());
    }

    @Override
    public Timestamp to(DateTime dateTime) {
        return new Timestamp(dateTime.getMillis());
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<DateTime> toType() {
        return DateTime.class;
    }
}

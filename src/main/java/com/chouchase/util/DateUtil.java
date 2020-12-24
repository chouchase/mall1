package com.chouchase.util;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }
    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date, DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Shanghai")));
        return dateTime.toString(formatStr);
    }
    public static Date strToDate(String dateTimeStr) {
        return strToDate(dateTimeStr,STANDARD_FORMAT);
    }
    public static String dateToStr(Date date){
        return dateToStr(date,STANDARD_FORMAT);
    }


}

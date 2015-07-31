package com.pfaff.tyler.gitup.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tylerpfaff on 7/31/15.
 */
public class DateUtil {
    private DateUtil() {
    }

    /**
    *Get the github api formatted date
     * @param daysAgo Get the formatted date, this many days back.
     * @return The formatted date
     */
    public static String getFormattedDate(int daysAgo) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -daysAgo);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }
}

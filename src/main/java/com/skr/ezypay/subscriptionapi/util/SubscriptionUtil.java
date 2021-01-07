package com.skr.ezypay.subscriptionapi.util;

import com.skr.ezypay.subscriptionapi.model.SubscriptionType;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class SubscriptionUtil {

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/MM/yyyy");

    /**
     * Function to parse date from string
     *
     * @param dateString
     *     date string value
     * @return date
     */
    public static LocalDate parseDateFromString(String dateString) {
        return LocalDate.parse(dateString, fmt);
    }

    /**
     * Function to get the subscription dates based on start, end and interval
     *
     * @param startDate
     *     start date
     * @param endDate
     *     end date
     * @param subscriptionType
     *     subscription type i.e Weekly, Monthly, Daily
     * @param interval
     *     interval of the subscription
     * @return list of subscription dates
     */
    public static List<String> getSubscriptionDates(String startDate, String endDate, String subscriptionType, String interval)  {
        List<LocalDate> listOfDays;
        LocalDate start = parseDateFromString(startDate);
        LocalDate stop = parseDateFromString(endDate);
        long numOfDaysBetween = ChronoUnit.DAYS.between(start, stop);

        listOfDays =  IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> start.plusDays(i))
                .collect(Collectors.toList());

        switch (SubscriptionType.valueOf(subscriptionType)) {
            case MONTHLY:
                listOfDays = listOfDays.stream().filter(date -> date.getDayOfMonth() == Integer.parseInt(interval)).collect(Collectors.toList());
                break;
            case WEEKLY:
                LocalDate nextOrSame = start.with( TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(interval)));
                List<LocalDate> tempList = new ArrayList<>();
                while ((null != nextOrSame) & (! nextOrSame.isAfter (stop))) {
                    tempList.add (nextOrSame);
                    nextOrSame = nextOrSame.plusWeeks(1);
                }
                listOfDays = tempList;
                break;
            case DAILY:
                break;
        }
        return listOfDays.stream().map(d -> fmt.format(d)).collect(Collectors.toList());
    }

    /**
     * Function to validate date range
     *
     * @param startDate
     *     start date
     * @param endDate
     *     end date
     * @param months
     *     months to get the difference
     * @return returns true or false for that date range
     */
    public static boolean validateFromAndToDateRange(String startDate, String endDate, String months) {
        LocalDate start = parseDateFromString(startDate);
        LocalDate stop = parseDateFromString(endDate);
        Period period = Period.between(start, stop);
        if (period.getMonths() <= Integer.parseInt(months)) {
            return true;
        } else {
            return false;
        }
    }
}

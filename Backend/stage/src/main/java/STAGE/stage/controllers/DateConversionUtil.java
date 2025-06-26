package STAGE.stage.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConversionUtil {

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
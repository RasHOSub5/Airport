package Utils;

import java.time.LocalDate;

public class DateValidator {

    public static boolean isBirthDateValid(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now().minusYears(1));
    }

    public static boolean isFlightDateValid(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }
}
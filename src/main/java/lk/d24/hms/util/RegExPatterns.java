package lk.d24.hms.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class RegExPatterns {
    private static final Pattern namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");
    private static final Pattern telPattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]{5,}$");
    private static final Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9]{5,}$");
    private static final Pattern addressPattern = Pattern.compile("^[a-zA-Z0-9]{5,}$");
    private static final Pattern amountPattern = Pattern.compile("^(?:0|[1-9]\\d{0,4})(?:\\.\\d{1,2})?$");
    private static final Pattern userIDPattern = Pattern.compile("^U-[0-9]{3}$");
    private static final Pattern reservationNoPattern = Pattern.compile("^RE-[0-9]{4}$");
    private static final Pattern roomNoPattern = Pattern.compile("^RM-[0-9]{4}$");
    private static final Pattern studentIDPattern = Pattern.compile("^S-[0-9]{4}$");
    private static final Pattern qtyPattern = Pattern.compile("^(?:0[0-9]?|[1-9][0-9]?)$");
    private static final Pattern typePattern = Pattern.compile("^[a-zA-Z /'.-]{2,}$");

    public static Pattern getNamePattern(){return namePattern;}
    public static Pattern getTelPattern(){return telPattern;}
    public static Pattern getUsernamePattern(){return usernamePattern;}
    public static Pattern getPasswordPattern(){return passwordPattern;}
    public static Pattern getAmountPattern(){return amountPattern;}
    public static Pattern getUserIDPattern(){return userIDPattern;}
    public static Pattern getReservationNoPattern(){return reservationNoPattern;}
    public static Pattern getRoomNoPattern(){return roomNoPattern;}
    public static Pattern getAddressPattern(){return addressPattern;}
    public static Pattern getStudentIDPattern(){return studentIDPattern;}
    public static Pattern getQtyPattern() {return qtyPattern;}
    public static Pattern getTypePattern() {return typePattern;}
    public static boolean birthdayPattern(LocalDate dateOfBirth){
        LocalDate today = LocalDate.now();
        LocalDate nineteenYearsAgo = today.minusYears(19);
        return !dateOfBirth.isAfter(nineteenYearsAgo);
    }
}
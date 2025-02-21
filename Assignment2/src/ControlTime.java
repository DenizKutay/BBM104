import javax.naming.NameNotFoundException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;


public class ControlTime {
    public static LocalDateTime currentTime;


    /**
     *
     * @param date LocalDateTime
     * @return date in String format
     */
    public static String timeToString (LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }

    /**
     *
     * @param time time in yyyy-M-d_H:m:s format
     * @return  time but as a LocalDateTime Object
     * @throws ParseException when format is incorrect
     */
    public static LocalDateTime stringToDate(String time) throws ParseException {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-M-d_H:m:s")
                .toFormatter();
        return LocalDateTime.parse(time, format);
    }

    /**
     * It changes the current time
     * @param time time as a String Object
     * @throws ParseException when format is incorrect
     * @throws IllegalArgumentException when the time is earlier than currentTime
     */
    public static void SetCurrentTime(String time) throws ParseException {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-M-d_H:m:s")
                .toFormatter();
        LocalDateTime currentTime = LocalDateTime.parse(time, format);
        int result = ControlTime.currentTime.compareTo(currentTime);
        if (result > 0) {
            throw new IllegalArgumentException();
        } else {
            ControlTime.currentTime = currentTime;
        }
    }

    /**
     * Add minutes to currentTime
     * @param minutes minutes will be added
     * @throws Error when minutes less than zero
     */
    public static void skipMinutes(int minutes) {
        if (minutes < 0) {
            throw new Error();
        } else {
            currentTime = currentTime.plusMinutes(minutes);
        }
    }

    /**
     * set initialTime to first switchTime
     * @throws ParseException when format is incorrect
     * @throws NameNotFoundException coming from nameToObject method
     */
    public static void nop() throws ParseException, NameNotFoundException {
        ArrayList<LocalDateTime> timeArraylist = new ArrayList<>();
        for (String name : DeviceLists.names) {
            Device obj = (Device) DeviceLists.nameToObject(name);
            assert obj != null;
            LocalDateTime time = findSwitchTime(obj.getStatusOnDate(),obj.getStatusOffDate());
            if (time == null) {
                continue;
            } else {
                timeArraylist.add(time);
            }
        }
        if (timeArraylist.size() == 0) {
            throw new NullPointerException();
        }
        Collections.sort(timeArraylist);
        currentTime = timeArraylist.get(0);
    }

    /**
     * Finding the device's switchTime
     * @param onDate statusOnDate of a device
     * @param offDate statusOffDate of a device
     * @return null if device has no switchTime else returns switchTime
     */
    public static LocalDateTime findSwitchTime (LocalDateTime onDate,LocalDateTime offDate) {
        if (!(onDate == null)) {
            if (onDate.isAfter(currentTime)) {
                return onDate;
            } else {
                return null;
            }
        }
        else if (!(offDate == null)) {
            if (offDate.isAfter(currentTime)){
                    return offDate;
            } else {
                return null;
            }
        } return null;
    }
}

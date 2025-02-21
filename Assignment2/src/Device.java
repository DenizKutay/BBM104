import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class Device {
    private String name;
    private String status = "Off";
    private LocalDateTime statusOnDate = null;
    private LocalDateTime statusOffDate = null;
    private boolean switchTime = false;

    /**
     * @param name name of the device
     * @throws IllegalStateException when name is already taken
     */
    public void setName(String name) {
        if (DeviceLists.names.contains(name)) {
            throw new IllegalStateException();
        } else {
            this.name = name;
        }

    }

    public String getName() {
        return name;
    }


    /**
     * @param status status of the device
     * @throws Error when input is invalid (not On or Off)
     */
    public void setStatus(String status) {
        if (status.equals("On") || status.equals("Off")) {
            this.status = status;
        } else {
            throw new Error();
        }
    }

    public String getStatus() {
        return status;
    }

    public boolean isSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(boolean switchTime) {
        this.switchTime = switchTime;
    }

    public void setStatusOffDate(String statusOffDate) {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-M-d_H:m:s")
                .toFormatter();
        this.statusOffDate = LocalDateTime.parse(statusOffDate, format);
    }

    public LocalDateTime getStatusOffDate() {
        return statusOffDate;
    }

    public void setStatusOnDate(String statusOnDate) {
        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-M-d_H:m:s")
                .toFormatter();
        this.statusOnDate = LocalDateTime.parse(statusOnDate, format);
    }

    public LocalDateTime getStatusOnDate() {
        return statusOnDate;
    }
}



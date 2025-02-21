public class SmartCamera extends Megabyte {
    public SmartCamera(String name, String megabytes) {
        setName(name);
        setMegabytesPerRecords(megabytes);
        if (getStatus().equals("Off")) {
            setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
        } else if (getStatus().equals("On")) {
            setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
        }
    }
    public SmartCamera(String name, String megabytes, String status) {
        setName(name);
        setMegabytesPerRecords(megabytes);
        setStatus(status);
        if (status.equals("Off")) {
            setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
        } else if (status.equals("On")) {
            setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
        }
    }
    private String switchTimeToString() {
        try {
            return ControlTime.timeToString(ControlTime.findSwitchTime(getStatusOnDate(), getStatusOffDate()));
        } catch (NullPointerException e) {
            return "null";
        }
    }

    public String toString() {

        return "Smart Camera " + getName() + " is " + getStatus().toLowerCase() + " and used " + getTotalMegabyte() + " MB of storage so far (excluding current device), and its time to switch its status is " + switchTimeToString() + ".";
    }
}

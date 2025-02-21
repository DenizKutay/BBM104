public class SmartPlug extends PlugVoltage {
    public SmartPlug(String name) {
        setName(name);
        setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
    }
    public SmartPlug(String name, String status) {
        setName(name);
        setStatus(status);
        if (status.equals("Off")) {
            setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
        } else if (status.equals("On")) {
            setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
        }

    }
    public SmartPlug(String name, String status, String ampere) {
        setName(name);
        setStatus(status);
        if (status.equals("Off")) {
            setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
        } else if (status.equals("On")) {
            setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
        }
        setAmpere(ampere);
        setPlug(true);
    }

    private String switchTimeToString() {
        try {
            return ControlTime.timeToString(ControlTime.findSwitchTime(getStatusOnDate(), getStatusOffDate()));
        } catch (NullPointerException e) {
            return "null";
        }
    }

    public String toString() {
        return "Smart Plug " + getName() + " is " + getStatus().toLowerCase() + " and consumed " + String.format("%.2f",getConsumedVoltage()) + "W so far (excluding current device), and its time to switch its status is " + switchTimeToString() + ".";
    }
}


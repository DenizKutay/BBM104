public class SmartLampWithColor extends ColorMode {
    public SmartLampWithColor(String name) {
        setName(name);
    }
    public SmartLampWithColor(String name, String status) {
        setName(name);
        setStatus(status);
    }
    public SmartLampWithColor(String name, String status, String kelvinOrColorCode, String brightness) {
        setName(name);
        setStatus(status);
        if (kelvinOrColorCode.contains("0x")) {
            setColorMode(kelvinOrColorCode);
        } else {
            setKelvin(kelvinOrColorCode);
        }
        setBrightness(brightness);
    }
    private String switchTimeToString() {
         try {
             return ControlTime.timeToString(ControlTime.findSwitchTime(getStatusOnDate(), getStatusOffDate()));
         } catch (NullPointerException e) {
             return "null";
         }
    }

    public String toString() {
        if (colorCode == null) {
            return "Smart Color Lamp " + getName() + " is " + getStatus().toLowerCase() +" and its color value is " + getKelvin() + "K with " + getBrightness() + "% brightness, and its time to switch its status is " + switchTimeToString() + ".";
        } else {
            return "Smart Color Lamp " + getName() + " is " + getStatus().toLowerCase() +" and its color value is 0x" + getColorCode() + " with " + getBrightness() + "% brightness, and its time to switch its status is " + switchTimeToString() + ".";
        }
    }
}

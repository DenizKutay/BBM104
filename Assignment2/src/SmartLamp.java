public class SmartLamp extends KelvinBrightness {
    private int kelvin = 4000;
    private int brightness = 100;

    public void setKelvin(String kelvin) {
        int intKelvin = Integer.parseInt(kelvin);
        if (2000 <= intKelvin && intKelvin <= 4000) {
            this.kelvin = intKelvin;
        } else {
            throw new IllegalArgumentException();
        }

    }
    public void setBrightness(String brightness){

        int intBrightness = Integer.parseInt(brightness);
        if (0 <= intBrightness && intBrightness <=100) {
            this.brightness = intBrightness;
        }
        else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public SmartLamp(String name){
        setName(name);
    }
    public SmartLamp(String name, String status) {
        setName(name);
        setStatus(status);
    }
    public SmartLamp(String name, String status, String kelvin ) {
        setName(name);
        setStatus(status);
        setKelvin(kelvin);
    }
    public SmartLamp(String name, String status, String kelvin, String brightness) {
        setName(name);
        setStatus(status);
        setKelvin(kelvin);
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
        return "Smart Lamp " + getName() + " is " + getStatus().toLowerCase() + " and its kelvin value is " + kelvin  + "K with " + brightness + "% brightness, and its time to switch its status is " + switchTimeToString()  + ".";
    }
}

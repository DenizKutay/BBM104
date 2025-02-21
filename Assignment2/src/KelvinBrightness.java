public class KelvinBrightness extends Device {
    private int kelvin = 4000;
    private int brightness = 100;

    /**
     *
     * @param kelvin value of the kelvin
     * @throws IllegalArgumentException when kelvin is out of 2000 to 6500 array
     */
    public void setKelvin(String kelvin) {
            int intKelvin = Integer.parseInt(kelvin);
            if (2000 <= intKelvin && intKelvin <= 6500) {
                this.kelvin = intKelvin;
           } else {
                throw new IllegalArgumentException();
            }

    }

    public int getKelvin() {
        return kelvin;
    }

    /**
     *
     * @param brightness value of the brightness
     * @throws ArrayIndexOutOfBoundsException when brightness is out of 0 to 100 array
     */
    public void setBrightness(String brightness){

            int intBrightness = Integer.parseInt(brightness);
            if (0 <= intBrightness && intBrightness <=100) {
                this.brightness = intBrightness;
            }
            else {
                throw new ArrayIndexOutOfBoundsException();
            }
    }

    public int getBrightness() {
        return brightness;
    }
}

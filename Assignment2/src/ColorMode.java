public class ColorMode extends KelvinBrightness {
    protected String colorCode = null;

    public String getColorCode() {
        return colorCode;
    }

    /**
    *@param colorCode 0x000000 format
     * @throws IllegalArgumentException when colorcode is out of 0x000000 and 0xFFFFFF array
     */
    public void setColorMode(String colorCode) {
            colorCode = colorCode.substring(2);
            if (0<=Integer.parseInt(colorCode,16) && Integer.parseInt(colorCode,16) <=Integer.parseInt("FFFFFF",16)) {
                this.colorCode = colorCode;
            } else {
                throw new IllegalArgumentException();
            }
    }
}


public class Megabyte extends Device {
    private float megabytesPerRecords = 0;
    private float totalMegabyte = 0;


    /**
     *
     * @param megabytesPerRecords megabyte that camera consumes in 1 minute
     * @throws IllegalArgumentException when meganytesPerRecords is negative value
     */
    public void setMegabytesPerRecords(String megabytesPerRecords) {
        float floatMegabytesPerRecords = Float.parseFloat(megabytesPerRecords);
        if (0 < floatMegabytesPerRecords) {
            this.megabytesPerRecords = floatMegabytesPerRecords;
        } else {
            throw new IllegalArgumentException();
        }

    }
    public float getMegabytesPerRecords() {
        return megabytesPerRecords;
    }

    public float getTotalMegabyte() {
        return totalMegabyte;
    }

    public void setTotalMegabyte(float totalMegabyte) {
        this.totalMegabyte = totalMegabyte;
    }

    /**
     * calculates the total megabyte that device has consumed
     */
    public void calculateTotalMegabyte() {
        try {
            if (getStatusOnDate() == null) {
                totalMegabyte += (getStatusOffDate().getSecond() - ControlTime.currentTime.getSecond()) * megabytesPerRecords * 60;
            } else {
                totalMegabyte += (getStatusOffDate().getMinute() - getStatusOnDate().getMinute()) * megabytesPerRecords;

            }
        } catch (NullPointerException e) {
                totalMegabyte += 0;
            }
        }
    }


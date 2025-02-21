import java.time.Duration;
import java.time.LocalDateTime;

public class PlugVoltage extends Device {
    private float consumedVoltage = 0;
    private float ampere;
    private boolean plug = false;

    public boolean isPlug() {
        return plug;
    }

    public void setPlug(boolean plug) {
        this.plug = plug;
    }

    public float getAmpere() {
        return ampere;
    }

    /**
     *
     * @param ampere ampere value
     * @throws IllegalArgumentException when ampere is negative
     */
    public void setAmpere(String ampere) {
        float floatAmpere = Float.parseFloat(ampere);
        if (0 < floatAmpere) {
            this.ampere = floatAmpere;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public float getConsumedVoltage() {
        return consumedVoltage;
    }


    /**
     * calculate the total voltage that plug has consumed
     */
    public void calculateConsumedVoltage() {
      try {
          Duration duration;
          if (getStatusOnDate() == null) {
              duration = Duration.between(ControlTime.currentTime, getStatusOffDate());
          } else {
              LocalDateTime switchTime = ControlTime.findSwitchTime(getStatusOnDate(),getStatusOffDate());
              if (switchTime == null) {
                  duration = Duration.between(getStatusOnDate(),getStatusOffDate());
              } else if (switchTime.equals(getStatusOnDate())) {
                  duration = Duration.between(ControlTime.currentTime,getStatusOffDate());
              } else {
                  duration = Duration.between(getStatusOnDate(), ControlTime.currentTime);
              }
          }
          consumedVoltage += ((duration.getSeconds()) / 3600.0)  * ampere * 220;

      }  catch (NullPointerException e) {
            consumedVoltage += 0;
        }
    }
}



import javax.naming.NameNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class DeviceLists {
    public static ArrayList<SmartLamp> smartLamps = new ArrayList<>();
public static ArrayList<SmartLampWithColor> smartLampWithColors = new ArrayList<>();
    public static ArrayList<SmartPlug> smartPlugs = new ArrayList<>();
    public static ArrayList<SmartCamera> smartCameras = new ArrayList<>();
    public static ArrayList<String> names = new ArrayList<>();


    public static void appendSmartLamps(SmartLamp object) {
        smartLamps.add(object);
        names.add(object.getName());
    }

    public static void appendSmartLampWithColors(SmartLampWithColor object) {
        smartLampWithColors.add(object);
        names.add(object.getName());
    }

    public static void appendSmartPlugs(SmartPlug object) {
        smartPlugs.add(object);
        names.add(object.getName());
    }

    public static void appendSmartCameras(SmartCamera object) {
        smartCameras.add(object);
        names.add(object.getName());
    }


    /**
     * scan all the arraylists and finds the Object
     * @param name name of the object
     * @return Device which name is name parameter
     * @throws NameNotFoundException when name parameter is not a device's name
     */
    public static Object nameToObject(String name) throws NameNotFoundException {
        if (names.contains(name)) {
            for (SmartPlug obj : smartPlugs) {
                if (Objects.equals(obj.getName(), name)) {
                    return obj;
                }
            }
            for (SmartLamp obj : smartLamps) {
                if (Objects.equals(obj.getName(), name)) {
                    return obj;
                }
            }
            for (SmartLampWithColor obj : smartLampWithColors) {
                if (Objects.equals(obj.getName(), name)) {
                    return obj;
                }
            }
            for (SmartCamera obj : smartCameras) {
                if (Objects.equals(obj.getName(), name)) {
                    return obj;
                }
            }
        } else {
            throw new NameNotFoundException();
        }
        return null;
    }

    /**
     *switch devices if their switchTime arrives
     * @throws NameNotFoundException coming from nameToObject method
     */
    public static void switcher() throws NameNotFoundException {
        for (String name : names) {
            Device device = (Device) nameToObject(name);
            assert device != null;
            if (device.isSwitchTime()) {
                LocalDateTime switchTime = ControlTime.findSwitchTime(device.getStatusOnDate(),device.getStatusOffDate());
                if (switchTime == null) {
                    if (device.getStatus().equals("On")) {
                        device.setStatus("Off");
                        device.setSwitchTime(false);
                        if (device instanceof SmartPlug) {
                            ((SmartPlug) device).calculateConsumedVoltage();
                        } else if (device instanceof SmartCamera) {
                            ((SmartCamera) device).calculateTotalMegabyte();
                        }
                    } else if (device.getStatus().equals("Off")) {
                        device.setStatus("On");
                        device.setSwitchTime(false);
                    }
                }
            }
        }
    }

}

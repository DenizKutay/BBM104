
import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.channels.AlreadyConnectedException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Main {
    public static void Add(String[] command) {
        try {
            switch (command[1]) {
                case "SmartPlug":
                    try {
                        switch (command.length) {
                            case 3:
                                SmartPlug smartPlug = new SmartPlug(command[2]);
                                DeviceLists.appendSmartPlugs(smartPlug);
                                break;
                            case 4:
                                SmartPlug smartPlug1 = new SmartPlug(command[2], command[3]);
                                DeviceLists.appendSmartPlugs(smartPlug1);
                                break;
                            case 5:
                                SmartPlug smartPlug2 = new SmartPlug(command[2], command[3],command[4]);
                                smartPlug2.setPlug(true);
                                DeviceLists.appendSmartPlugs(smartPlug2);
                                break;
                            default:
                                throw new Error();
                        }
                    } catch (NumberFormatException e) {
                        Writer.Print("ERROR: Ampere value must be a float number", true);
                    } catch (IllegalArgumentException e) {
                        Writer.Print("ERROR: Ampere value must be a positive number!", true);
                    }
                    break;
                case "SmartCamera":
                    try {
                        switch (command.length) {
                            case 4:
                                SmartCamera smartCamera1 = new SmartCamera(command[2], command[3]);
                                DeviceLists.appendSmartCameras(smartCamera1);
                                break;
                            case 5:
                                SmartCamera smartCamera2 = new SmartCamera(command[2], command[3], command[4]);
                                DeviceLists.appendSmartCameras(smartCamera2);
                                break;
                            default:
                                throw new Error();
                        }
                    } catch (NumberFormatException e) {
                        Writer.Print("ERROR: Megabyte value has to be a number!", true);
                    } catch (IllegalArgumentException e) {
                        Writer.Print("ERROR: Megabyte value has to be a positive number!", true);
                    }
                    break;

                case "SmartLamp":
                    try {
                        switch (command.length) {
                            case 3:
                                SmartLamp smartLamp = new SmartLamp(command[2]);
                                DeviceLists.appendSmartLamps(smartLamp);
                                break;
                            case 4:
                                SmartLamp smartLamp1 = new SmartLamp(command[2], command[3]);
                                DeviceLists.appendSmartLamps(smartLamp1);
                                break;
                            case 5:
                                SmartLamp smartLamp2 = new SmartLamp(command[2], command[3], command[4]);
                                DeviceLists.appendSmartLamps(smartLamp2);
                                break;
                            case 6:
                                SmartLamp smartLamp3 = new SmartLamp(command[2], command[3], command[4], command[5]);
                                DeviceLists.appendSmartLamps(smartLamp3);
                                break;
                            default:
                                throw new Error();
                        }
                    } catch (NumberFormatException e) {
                        Writer.Print("ERROR: Erroneous command!", true);
                    } catch (IllegalArgumentException e) {
                        Writer.Print("Kelvin value must be in range of 2000K-6500K!", true);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Writer.Print("ERROR: Brightness must be in range of 0%-100%!",true);
                    }
                    break;
                case "SmartColorLamp":
                    try {
                        switch (command.length) {
                            case 3:
                                SmartLampWithColor smartLampWithColor = new SmartLampWithColor(command[2]);
                                DeviceLists.appendSmartLampWithColors(smartLampWithColor);
                                break;
                            case 4:
                                SmartLampWithColor smartLampWithColor1 = new SmartLampWithColor(command[2], command[3]);
                                DeviceLists.appendSmartLampWithColors(smartLampWithColor1);
                                break;
                            case 6:
                                SmartLampWithColor smartLampWithColor2 = new SmartLampWithColor(command[2], command[3], command[4], command[5]);
                                DeviceLists.appendSmartLampWithColors(smartLampWithColor2);
                                break;
                            default:
                                throw new Error();
                        }
                    } catch (IllegalArgumentException e) {
                        Writer.Print("ERROR: Color code value must be in range of 0x0-0xFFFFFF", true);
                    } catch (IllegalStateException e) {
                        Writer.Print("ERROR: There is already a smart device with same name!", true);
                    }
                    break;

            }
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: There is already a smart device with same name!", true);
        } catch (Error e) {
            Writer.Print("ERROR: Erroneous Command!", true);
        }
    }
    public static void Remove(String[] command) {
        try {
            if (command.length == 2) {
                String name = command[1];
                Object device = DeviceLists.nameToObject(name);
                Writer.Print("SUCCESS: Information about removed smart device is as follows:",true);
                assert device != null;
                ((Device)device).setStatus("Off");
                ((Device)device).setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
                if (device instanceof SmartPlug) {
                    ((SmartPlug) device).calculateConsumedVoltage();
                    Writer.Print(device.toString(),true);
                    DeviceLists.smartPlugs.remove(device);
                    DeviceLists.names.remove(name);
                }
                if (device instanceof SmartCamera) {
                    ((SmartCamera) device).calculateTotalMegabyte();
                    Writer.Print(device.toString(),true);
                    DeviceLists.smartCameras.remove(device);
                    DeviceLists.names.remove(name);

                }
                if (device instanceof SmartLamp) {
                    Writer.Print(device.toString(),true);
                    DeviceLists.smartLamps.remove(device);
                    DeviceLists.names.remove(name);
                }

                if (device instanceof SmartLampWithColor) {
                    Writer.Print(device.toString(),true);
                    DeviceLists.smartLampWithColors.remove(device);
                    DeviceLists.names.remove(name);
                }
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            Writer.Print("ERROR: Erroneous command!",true);
        }catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist!", true);
        }

    }
    public static void SetSwitchTime(String[] command) {
        try {
            if (command.length == 3) {
                if (ControlTime.currentTime.isAfter(ControlTime.stringToDate(command[2]))) {
                    throw new IllegalStateException();
                }
                String name = command[1];
                Object device = DeviceLists.nameToObject(name);
                if (device instanceof Device) {
                    if (((Device) device).getStatus().equals("Off")) {
                        ((Device) device).setStatusOnDate(command[2]);
                        ((Device) device).setSwitchTime(true);
                    } else if (((Device) device).getStatus().equals("On")) {
                        ((Device) device).setStatusOffDate(command[2]);
                        ((Device) device).setSwitchTime(true);
                    }
                }
            } else {
                throw new Error();
            }
        } catch (Error e) {
            Writer.Print("ERROR: Erroneous Command!", true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist!", true);
        } catch (ParseException e) {
            Writer.Print("ERROR: Format of the initial date is wrong! Program is going to terminate!",true);
            Writer.Print("",false);
            System.exit(0);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: Switch time cannot be in the past!",true);
        }
    }
    public static void Switch(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            if (device instanceof Device) {
                if (Objects.equals(command[2], "On")) {
                    if (Objects.equals(((Device) device).getStatus(), "On")) {
                        Writer.Print("ERROR: This device is already switched on!", true);
                    } else {
                        ((Device) device).setStatus(command[2]);
                        ((Device) device).setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
                    }
                } else if (Objects.equals(command[2], "Off")) {
                    if (Objects.equals(((Device) device).getStatus(), "Off")) {
                        Writer.Print("ERROR: This device is already switched off!", true);
                    } else {
                        ((Device) device).setStatus(command[2]);
                        ((Device) device).setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
                        if (device instanceof SmartPlug) {
                            ((SmartPlug) device).calculateConsumedVoltage();
                        } else if (device instanceof SmartCamera) {
                            ((SmartCamera) device).calculateTotalMegabyte();
                        }
                    }
                }
            }
        } catch (Error e) {
            Writer.Print("ERROR: Erroneous command!",true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: There is not such a device!",true);
        }
    }
    public static void ChangeName(String[] command) {
        try {
            if (command.length == 3) {
                if (Objects.equals(command[1], command[2])) {
                    throw new AlreadyConnectedException();
                } else {
                    String name = command[1];
                    Object device = DeviceLists.nameToObject(name);
                    assert device != null;
                    ((Device) device).setName(command[2]);
                    DeviceLists.names.remove(name);
                    DeviceLists.names.add(command[2]);
                }
            } else {
                throw new Error();
            }
        } catch (Error e) {
            Writer.Print("ERROR: Erroneous command!", true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Device does not exist!", true);
        } catch (AlreadyConnectedException e) {
            Writer.Print("ERROR: Both of the names are the same, nothing changed!",true);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: There is already a smart device with same name",true);
        }
    }
    public static void PlugIn(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            assert device != null;
            if (!(((SmartPlug)device).isPlug())){
                if (command.length == 3) {
                    ((SmartPlug) device).setAmpere(command[2]);
                    ((SmartPlug)device).setPlug(true);
                    ((SmartPlug)device).setStatusOnDate(ControlTime.timeToString(ControlTime.currentTime));
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            } else {
                throw new AlreadyConnectedException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Writer.Print("ERROR: Erroneous command!",true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Device does not exist!",true);
        } catch (AlreadyConnectedException e) {
            Writer.Print("ERROR: There is already an item plugged in to that plug!",true);
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Ampere value must be a positive number!",true);
        } catch (ClassCastException e) {
            Writer.Print("ERROR: This device is not a smart plug!",true);
        }
    }
    public static void PlugOut(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            assert device != null;
            if (((SmartPlug)device).isPlug()){
                if (command.length == 2) {
                    ((SmartPlug) device).setPlug(false);
                    ((SmartPlug)device).setStatusOffDate(ControlTime.timeToString(ControlTime.currentTime));
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            } else {
                throw new AlreadyConnectedException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Writer.Print("ERROR: Erroneous command!",true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Device does not exist!",true);
        } catch (AlreadyConnectedException e) {
            Writer.Print("ERROR: This plug has no item to plug out from that plug!",true);
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Ampere value must be a positive number!",true);
        } catch (ClassCastException e) {
            Writer.Print("ERROR: This device is not a smart plug!",true);
        }
    }
    public static void SetKelvin(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            if (device instanceof SmartLamp || device instanceof SmartLampWithColor) {
                ((KelvinBrightness) device).setKelvin(command[2]);
            } else {
                throw new IllegalStateException();
            }
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Kelvin value must be in range of 2000K-6500K!",true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist",true);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: This device is not a smart lamp!",true);
        }
    }
    public static void SetColor(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            if (device instanceof SmartLampWithColor) {
                ((SmartLampWithColor) device).setBrightness(command[3]);
                ((ColorMode) device).setColorMode(command[2]);
            } else {
                throw new IllegalStateException();
            }
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist", true);
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Erroneous command!",true);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: This device is not a smart color lamp!",true);
        } catch (ArrayIndexOutOfBoundsException e) {
            Writer.Print("ERROR: Brightness must be in range of 0%-100%!",true);
        }
    }
    public static void SetColorCode(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            if (device instanceof SmartLampWithColor) {
                ((ColorMode) device).setColorMode(command[2]);
            } else {
                throw new IllegalStateException();
            }
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Erroneous command!",true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist",true);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: This device is not a smart color lamp!",true);
        }
    }
    public static void SetWhite(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            if (device instanceof SmartLampWithColor) {
                ((ColorMode) device).setKelvin(command[2]);
                ((SmartLampWithColor) device).setBrightness(command[3]);
                ((SmartLampWithColor) device).colorCode = null;
            } else {
                throw new IllegalStateException();
            }
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist", true);
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Kelvin value must be in range of 2000K-6500K!",true);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: This device is not a smart color lamp!",true);
        } catch (ArrayIndexOutOfBoundsException e) {
            Writer.Print("ERROR: Brightness must be in range of 0%-100%!",true);
        }
    }
    public static void SetBrightness(String[] command) {
        try {
            String name = command[1];
            Object device = DeviceLists.nameToObject(name);
            if (device instanceof SmartLamp || device instanceof SmartLampWithColor) {
                ((KelvinBrightness) device).setBrightness(command[2]);
            } else {
                throw new IllegalStateException();
            }
        } catch (IllegalArgumentException e) {
            Writer.Print("ERROR: Brightness value must be in range of 0%-100% !",true);
        } catch (NameNotFoundException e) {
            Writer.Print("ERROR: Name does not exist",true);
        } catch (IllegalStateException e) {
            Writer.Print("ERROR: This device is not a smart lamp!",true);
        }
    }
    public static void ZReport(String[] command) {
        try {
            if (command.length != 1) {
                throw new ArrayIndexOutOfBoundsException();
            }
            DeviceLists.switcher();
            Writer.Print("Time is \t" + ControlTime.timeToString(ControlTime.currentTime),true);
            ArrayList <Device> deviceWithSwitchTime = new ArrayList<>();
            ArrayList <Device> deviceWithoutSwitchTime = new ArrayList<>();
            for (int i = 0; i < DeviceLists.names.size() ; i++) {
                String name = DeviceLists.names.get(i);
                Device device = (Device) DeviceLists.nameToObject(name);
                assert device != null;
                if (ControlTime.findSwitchTime(device.getStatusOnDate(),device.getStatusOffDate()) != null) {
                    deviceWithSwitchTime.add(device);
                } else {
                    deviceWithoutSwitchTime.add(device);
                }
            }
            for (Device device : deviceWithSwitchTime) {
               Writer.Print(device.toString(),true);
            } for (Device device : deviceWithoutSwitchTime) {
                Writer.Print(device.toString(),true);
            }

        } catch (NameNotFoundException e) {
            Writer.Print("BUG",true);
        } catch (ArrayIndexOutOfBoundsException e) {
            Writer.Print("ERROR: Erroneous command!",true);
        }
    }
    public static void main(String[] args) {
        try {
            Writer.file = args[1];
            Writer.open();
            File input = new File(args[0]);
            FileReader reader = new FileReader(input);
            Scanner scanner = new Scanner(reader);
            int lineCounter = 0;
            String line = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] command = line.split("\t");
                if (line.equals("")) {
                    continue;
                }
                Writer.Print("COMMAND: " + line, true);

                if (lineCounter == 0) {
                    if (!command[0].equals("SetInitialTime")) {
                        Writer.Print("ERROR: First command must be set initial time! Program is going to terminate!",true);
                        Writer.Print("",false);
                        System.exit(0);
                    }
                }

                switch (command[0]) {
                    case "SetInitialTime":
                        if (lineCounter == 0) {
                            try {
                                ControlTime.currentTime = ControlTime.stringToDate(command[1]);
                                Writer.Print("SUCCESS: Time has been set to " + command[1], true);
                            } catch (DateTimeParseException e) {
                                Writer.Print("ERROR: Format of the initial date is wrong! Program is going to terminate!",true);
                                Writer.Print("",false);
                                System.exit(0);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                Writer.Print("ERROR: First command must be set initial time! Program is going to terminate!",true);
                                Writer.Print("",false);
                                System.exit(0);
                            }


                        } else {
                            Writer.Print("ERROR: Erroneous command!",true);
                        }
                        break;
                    case "SetTime":
                        try {
                            ControlTime.SetCurrentTime(command[1]);
                            if (command[1].equals(ControlTime.timeToString(ControlTime.currentTime))) {
                                Writer.Print("ERROR: There is nothing to change!!",true);
                            }
                        } catch (IllegalArgumentException e) {
                            Writer.Print("ERROR: Time cannot be reversed!",true);
                        } catch (DateTimeParseException e) {
                            Writer.Print("ERROR: Time format is not correct!",true);
                        }
                        break;

                    case "SkipMinutes":
                        try {
                            if (command.length == 2) {
                                ControlTime.skipMinutes(Integer.parseInt(command[1]));
                                if (command[1].equals("0")) {
                                    Writer.Print("ERROR: There is nothing to skip!", true);
                                }
                            } else {
                                throw new ArrayIndexOutOfBoundsException();
                            }
                        } catch (Error e) {
                            Writer.Print("ERROR: Time cannot be reversed!",true);
                        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                            Writer.Print("ERROR: Erroneous command!",true);
                        }

                        break;
                    case "Nop":
                         try {
                             if (command.length == 1) {
                                 ControlTime.nop();
                             } else {
                               throw new ArrayIndexOutOfBoundsException();
                             }
                         } catch (ArrayIndexOutOfBoundsException e) {
                             Writer.Print("ERROR: Erroneous command!",true);
                         } catch (NameNotFoundException e) {
                             throw new RuntimeException(e);
                         } catch (NullPointerException e) {
                             Writer.Print("ERROR: There is nothing to switch!",true);
                         }
                        break;
                    case "Add":
                       Add(command);
                        break;
                    case "Remove":
                        Remove(command);
                        break;
                    case "SetSwitchTime":
                        SetSwitchTime(command);
                        break;
                    case "Switch":
                        Switch(command);
                        break;
                    case "ChangeName":
                        ChangeName(command);
                        break;
                    case "PlugIn":
                        PlugIn(command);
                        break;
                    case "PlugOut":
                        PlugOut(command);
                        break;
                    case "SetKelvin":
                        SetKelvin(command);
                        break;
                    case "SetColor":
                        SetColor(command);
                        break;
                    case "SetColorCode":
                        SetColorCode(command);
                        break;
                    case "SetWhite":
                        SetWhite(command);
                        break;
                    case "SetBrightness":
                        SetBrightness(command);
                        break;
                    case "ZReport":
                        ZReport(command);
                        break;
                    default:
                        Writer.Print("ERROR: Erroneous command!",true);
                }
            lineCounter++;
            }
            if (!Objects.equals(line, "ZReport")) {
                String[] command = {"ZReport"};
                Writer.Print("ZReport:",true);
                ZReport(command);
            }
            Writer.Print("", false);
        } catch (FileNotFoundException | ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}

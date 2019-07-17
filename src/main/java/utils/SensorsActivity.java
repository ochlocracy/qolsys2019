package utils;

public class SensorsActivity extends ConfigPropsBase {

    public static String OPEN;
    public static String CLOSE;
    public static String ACTIVATE;
    public static String RESTORE;
    public static String TAMPER;
    public static String KAWAY;
    public static String KSTAY;
    public static String KDISARM;
    private static SensorsActivity instance;

    private SensorsActivity() throws Exception {
        super("sensors.properties");
        OPEN = getString("openSensor");
        CLOSE = getString("closeSensor");
        ACTIVATE = getString("activateSensor");
        RESTORE = getString("restoreSensor");
        TAMPER = getString("tamperSensor");
        KAWAY = getString("keyfobAway");
        KSTAY = getString("keyfobStay");
        KDISARM = getString("keyfobDisarm");
    }

    public static void init() throws Exception {
        if (instance == null) {
            instance = new SensorsActivity();
        }
    }
}

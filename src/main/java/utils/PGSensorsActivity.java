package utils;

public class PGSensorsActivity extends ConfigPropsBase {

    public static String INOPEN;
    public static String INCLOSE;
    public static String EXOPEN;
    public static String EXCLOSE;
    public static String MOTIONIDLE;
    public static String MOTIONACTIVE;
    public static String SMOKE;
    public static String TAMPER;
    public static String SMOKEM;
    public static String CO;
    public static String GB;
    public static String FLOOD;
    public static String GAS;
    public static String SHOCK;
    public static String TESTBUTTON;
    public static String AUX;
    public static String AUXPANIC;
    public static String POLICEPANIC;
    public static String FIREPANIC;
    public static String SMOKEREST;
    public static String TAMPERREST;
    public static String SMOKEMREST;
    public static String COREST;
    public static String GBREST;
    public static String FLOODREST;
    public static String GASREST;
    public static String SHOCKREST;
    public static String AUXPANICREST;
    public static String POLICEPANICREST;
    public static String FIREPANICREST;
    private static PGSensorsActivity instance;

    private PGSensorsActivity() throws Exception {
        super("pgsensors.properties");
        INOPEN = getString("internalContactopen");
        INCLOSE = getString("internalContactclose");
        EXOPEN = getString("externalContactopen");
        EXCLOSE = getString("externalContactclose");
        MOTIONIDLE = getString("motionIDLE");
        MOTIONACTIVE = getString("motionActive");
        SMOKE = getString("smoke");
        SMOKEREST = getString("smokeOK");
        TAMPER = getString("tamper");
        TAMPERREST = getString("tamperOK");
        SMOKEM = getString("heatSmokeM");
        SMOKEMREST = getString("heatSmokeMOK");
        CO = getString("CO");
        COREST = getString("COOK");
        GB = getString("glassBreak");
        GBREST = getString("glassBreakOK");
        FLOOD = getString("flood");
        FLOODREST = getString("floodOK");
        GAS = getString("gas");
        GASREST = getString("gasOK");
        SHOCK = getString("shock");
        SHOCKREST = getString("shockOK");
        TESTBUTTON = getString("testButton");
        AUX = getString("aux");
        POLICEPANIC = getString("keypadPolice");
        AUXPANIC = getString("keypadAux");
        FIREPANIC = getString("keypadFire");
        POLICEPANICREST = getString("keypadPoliceOK");
        AUXPANICREST = getString("keypadAuxOK");
        FIREPANICREST = getString("keypadFireOK");
    }
    public static void init() throws Exception {
        if (instance == null) {
            instance = new PGSensorsActivity();
        }
    }
}

package utils;

public class ConfigProps extends ConfigPropsBase {

    public static String adbPath;
    public static String appiumPath;
    public static String nodePath;
    public static int normalExitDelay;
    public static int normalEntryDelay;
    public static int longExitDelay;
    public static int longEntryDelay;
    private static ConfigProps instance;

    private ConfigProps() throws Exception {
        super("config.properties");

        adbPath = getString("adb.path");
        appiumPath = getString("appium.path");
        nodePath = getString("node.path");
        normalExitDelay = Integer.parseInt(getString("normalExitDelay"));
        normalEntryDelay = Integer.parseInt(getString("normalEntryDelay"));
        longExitDelay = Integer.parseInt(getString("longExitDelay"));
        longEntryDelay = Integer.parseInt(getString("longEntryDelay"));
    }

    public static void init() throws Exception {
        if (instance == null) {
            instance = new ConfigProps();
        }
    }
}

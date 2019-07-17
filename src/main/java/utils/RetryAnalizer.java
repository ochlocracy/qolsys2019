package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalizer implements IRetryAnalyzer {

    public int counter = 0;
    public static boolean retry;
    int retryLimit = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (counter < retryLimit) {
            retry = true;
            counter++;
            return true;
        }
        return false;
    }
}

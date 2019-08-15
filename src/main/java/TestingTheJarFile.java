import AltPackage.AltClass;
import MainPackage.TestMain;
import org.testng.annotations.Test;

public class TestingTheJarFile {

    @Test
    public void tryTheJar() {
        System.out.println(TestMain.myString);
        System.out.println(AltClass.myString);

    }

}

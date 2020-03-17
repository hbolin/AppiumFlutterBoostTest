import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import pro.truongsinh.appium_flutter.FlutterFinder;
import pro.truongsinh.appium_flutter.finder.FlutterElement;

import java.io.File;

public class AppiumTestDemo {
    public static String appPtah = "/flutterboost-debug.apk";

    public static void main(String[] args) {
        File rootFile = new File("");
        System.out.println("测试app文件路径：" + rootFile.getAbsolutePath());
        File appFile = new File(rootFile.getAbsolutePath() + appPtah);
        System.out.println("测试app文件路径：" + appFile.getAbsolutePath());
        appPtah = appFile.getAbsolutePath();

        AndroidDriver<WebElement> driver = getFlutterDriver();

        // 切换成本地的代码
        driver.context("NATIVE_APP");

        WebElement openNativeWebElement = driver.findElementById("com.taobao.idlefish.flutterboostexample:id/open_flutter");
        openNativeWebElement.click();
        System.out.println("点击完成跳转至flutter页面完成");

        // 切换成flutter端的代码
        driver.context("FLUTTER");

        FlutterFinder find = new FlutterFinder(driver);
        FlutterElement buttonFinder = find.byValueKey("openFirstPage");
        buttonFinder.click();

        System.out.println("openFirstPage页面完成");

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    public static AndroidDriver<WebElement> getFlutterDriver() {
        System.out.println("getFlutterDriver");

        DesiredCapabilities flutterCapabilities = new DesiredCapabilities();
        flutterCapabilities.setCapability("platformName", "Android");
        flutterCapabilities.setCapability("deviceName", "one plus");
        flutterCapabilities.setCapability("app", appPtah);
        flutterCapabilities.setCapability("platformVersion", "10");
        flutterCapabilities.setCapability("noReset", true);
        flutterCapabilities.setCapability("automationName", "Flutter");

        AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
        AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(service.getUrl(), flutterCapabilities);

        System.out.println("getFlutterDriver finish");
        return driver;
    }
}

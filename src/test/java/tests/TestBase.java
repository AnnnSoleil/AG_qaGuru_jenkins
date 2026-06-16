package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.RegistrationPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    RegistrationPage registrationPage = new RegistrationPage();

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @BeforeAll
    static void beforeAll() {
        String baseUrl = System.getProperty("BASE_URL", "https://demoqa.com");
        String browser = System.getProperty("BROWSER", "chrome");
        String browserVersion = System.getProperty("BROWSER_VERSION", "128.0");
        String browserSize = System.getProperty("BROWSER_SIZE", "1920x1080");
        String remoteUrl = System.getProperty("REMOTE_URL", "selenoid.autotests.cloud/wd/hub");
        String remoteLogin = System.getProperty("REMOTE_LOGIN", "user1");
        String remotePassword = System.getProperty("REMOTE_PASSWORD", "1234");


        Configuration.baseUrl = baseUrl;
        Configuration.browser = browser;
        Configuration.browserVersion = browserVersion;
        Configuration.browserSize = browserSize;
        Configuration.browserVersion = browserVersion;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = String.format(
                "https://%s:%s@%s",
                remoteLogin,
                remotePassword,
                remoteUrl
        );
    }
    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
//        Attach.attachAsText("Some file", "Some content");
        closeWebDriver();
    }

}
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static utils.FileUtil.readTextFileAsString;

class UiTest {

    @BeforeEach
    void loadWebpage() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/webdriver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("0.0.0.0:8090");
        options.setCapability("proxy", proxy);
        ChromeDriver chromeDriver = new ChromeDriver(options);
        setWebDriver(chromeDriver);
        open("http://automationpractice.com/index.php?controller=contact");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ui/contactForm.csv", numLinesToSkip = 1)
    void testContactForm(String subjectHeading, String emailAddress, String orderReference, String message, String uploadFile) {
        $(By.id("id_contact")).selectOption(subjectHeading);
        $(By.id("email")).setValue(emailAddress);
        $(By.id("id_order")).setValue(orderReference);
        $(By.id("message")).setValue(readTextFileAsString(message));
        $(By.id("fileUpload")).uploadFile(new File(uploadFile));
        $(By.id("submitMessage")).click();
        $(By.className("alert-success")).shouldHave(Condition.text("Your message has been successfully sent to our team"));
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }
}
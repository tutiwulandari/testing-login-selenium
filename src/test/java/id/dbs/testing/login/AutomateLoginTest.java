package id.dbs.testing.login;

import id.dbs.testing.BaseTest;
import id.dbs.testing.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


class AutomateLoginTest extends BaseTest {
    private static final String LINK_WEB = "https://the-internet.herokuapp.com/login";

    @Test
    void LoginFailed()  {
        driver.get(LINK_WEB);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("toms");
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLoginButton();

        // Use WebDriverWait to wait for error message
        // to handle some delay from API
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));

        String actualErrorMessage = loginPage.getErrorMessage();
        Assertions.assertEquals("Your username is invalid!\n×", actualErrorMessage);
    }

    @Test
    void LoginSuccess() {
        driver.get(LINK_WEB);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("tomsmith");
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLoginButton();

        // Wait for success message
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));

        // Verify success message
        WebElement lblSuccess = driver.findElement(By.id("flash"));
        String actualText = lblSuccess.getText();
        String expectedText = "You logged into a secure area!\n×";
        Assertions.assertEquals(expectedText, actualText);

        // Verify the URL after successful login
        String redirectUrl = driver.getCurrentUrl();
        Assertions.assertEquals("https://the-internet.herokuapp.com/secure", redirectUrl);
    }


}

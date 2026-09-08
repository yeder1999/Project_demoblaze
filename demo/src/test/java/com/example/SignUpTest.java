package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpTest {

    WebDriver driver;

    @BeforeEach
    public void Setup() {
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
    }

    @AfterEach
    public void TearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    @Tag("smoke")
    public void testSignUp() {

        // Cliquer sur Sign up
        driver.findElement(By.id("signin2")).click();

        // Attendre que le champ username soit interactif
        WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("sign-username")
            )
        );

        // Remplir le username
        driver.findElement(By.id("sign-username"))
              .sendKeys("user1234565");

        // Remplir le password
        driver.findElement(By.id("sign-password"))
              .sendKeys("Password1234");

        // Cliquer sur Sign up
        driver.findElement(
            By.xpath("//button[contains(text(),'Sign up')]")
        ).click();

        // Attendre l'alerte
        wait.until(ExpectedConditions.alertIsPresent());

        // Récupérer le message
        String message = driver.switchTo().alert().getText();

        // Vérifier le message
        assertEquals("Sign up successful.", message);

        // Fermer l'alerte
        driver.switchTo().alert().accept();
    }
}

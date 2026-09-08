
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

public class LoginTest {

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
    public void testLogin() {

        // Cliquer sur Log in
        driver.findElement(By.id("login2")).click();

        // Attendre que le champ username soit interactif
        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10));

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("loginusername")
            )
        );

        // Remplir le username
        driver.findElement(By.id("loginusername"))
              .sendKeys("Benamar");

        // Remplir le password
        driver.findElement(By.id("loginpassword")).sendKeys("Benamar");

        // Cliquer sur Log in
        driver.findElement(
            By.xpath("//button[contains(text(),'Log in')]")
        ).click();
        

        // Attendre que le message Welcome apparaisse
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("nameofuser")
            )
        );

        // Vérifier Welcome username
        String welcomeMessage = driver.findElement(By.id("nameofuser")).getText();

        assertEquals("Welcome Benamar", welcomeMessage);
        //assertEquals("https://www.demoblaze.com/index.html",driver.getCurrentUrl());

    }
}


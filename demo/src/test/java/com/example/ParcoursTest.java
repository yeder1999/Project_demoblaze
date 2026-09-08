
package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ParcoursTest {

    WebDriver driver;

   @BeforeEach
public void Setup() {

    ChromeOptions options = new ChromeOptions();

    // Désactiver le gestionnaire de mots de passe Chrome
    options.addArguments("--disable-notifications");

    Map<String, Object> prefs = new HashMap<>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("profile.password_manager_leak_detection", false);
    options.setExperimentalOption("prefs", prefs);
    driver = new ChromeDriver(options);
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
            driver,Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername") ));

        // Remplir le username
        driver.findElement(By.id("loginusername")).sendKeys("Benamar");

        // Remplir le password
        driver.findElement(By.id("loginpassword")).sendKeys("Benamar");

        // Cliquer sur Log in
        driver.findElement(By.xpath("//button[contains(text(),'Log in')]")).click();


        // Vérifier le message Welcome
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        String welcomeMessage = driver.findElement(By.id("nameofuser")).getText();

        assertEquals("Welcome Benamar", welcomeMessage);
wait.until(ExpectedConditions.elementToBeClickable(By.id("itemc")));
// 8. Cliquer sur Phones
driver.findElement(By.id("itemc")).click();

// 9. Attendre que Samsung galaxy s6 soit visible
By samsungS6 = By.xpath("//a[contains(@href,'prod.html?idp_=1') and contains(text(),'Samsung galaxy s6')]"
);

wait.until(ExpectedConditions.visibilityOfElementLocated(samsungS6));

// 10. Cliquer sur Samsung galaxy s6
driver.findElement(samsungS6).click();

// 11. Vérifier l'URL du produit
wait.until(ExpectedConditions.urlContains("prod.html?idp_=1"));

assertEquals(
    "https://www.demoblaze.com/prod.html?idp_=1",driver.getCurrentUrl()
);
        // Cliquer sur Add to cart
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add to cart')]")
    ));

        driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();

        // Attendre l'alerte Product added.
        wait.until(ExpectedConditions.alertIsPresent());

        // Récupérer le message
        String message = driver.switchTo().alert().getText();
        assertEquals("Product added.",message );

        // Fermer l'alerte
        driver.switchTo().alert().accept();
        // 14. Cliquer sur Cart
wait.until(ExpectedConditions.elementToBeClickable(
    By.id("cartur")
));

driver.findElement(By.id("cartur")).click();

// 15. Vérifier l'URL du panier
wait.until(ExpectedConditions.urlToBe(
    "https://www.demoblaze.com/cart.html"
));

assertEquals(
    "https://www.demoblaze.com/cart.html",
    driver.getCurrentUrl()
);

// 16. Cliquer sur Place Order
wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//button[contains(text(),'Place Order')]")
));

driver.findElement(
    By.xpath("//button[contains(text(),'Place Order')]")
).click();

// 17. Attendre que le formulaire de commande soit disponible
wait.until(ExpectedConditions.visibilityOfElementLocated(
    By.id("name")
));

// 18. Remplir Name
driver.findElement(By.id("name"))
      .sendKeys("Benamar");

// 19. Remplir Country
driver.findElement(By.id("country"))
      .sendKeys("France");

// 20. Remplir City
driver.findElement(By.id("city"))
      .sendKeys("Paris");

// 21. Remplir Credit Card
driver.findElement(By.id("card"))
      .sendKeys("1234567890123456");

// 22. Remplir Month
driver.findElement(By.id("month"))
      .sendKeys("09");

// 23. Remplir Year
driver.findElement(By.id("year"))
      .sendKeys("2026");

// 24. Cliquer sur Purchase
wait.until(ExpectedConditions.elementToBeClickable(
    By.xpath("//button[contains(text(),'Purchase')]")
));

driver.findElement(
    By.xpath("//button[contains(text(),'Purchase')]")
).click();

// 25. Attendre la confirmation d'achat
wait.until(ExpectedConditions.visibilityOfElementLocated(
    By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")
));

// 26. Récupérer le message
String purchaseMessage = driver.findElement(
    By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")
).getText();

// 27. Vérifier le message
assertEquals(
    "Thank you for your purchase!",
    purchaseMessage
);

// 28. Cliquer sur OK
wait.until(ExpectedConditions.elementToBeClickable(
    By.cssSelector("button.confirm.btn.btn-lg.btn-primary")
));

driver.findElement(
    By.cssSelector("button.confirm.btn.btn-lg.btn-primary")
).click();
}
}


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

import com.github.javafaker.Faker;

public class ParcoursFakerTest {

    WebDriver driver;

    @BeforeEach
    public void Setup() {

        ChromeOptions options = new ChromeOptions();

        // Désactiver les notifications et le gestionnaire de mots de passe Chrome
        options.addArguments("--disable-notifications");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
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
    public void testParcoursAvecFaker() {

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10));

       

        driver.findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));

        driver.findElement(By.id("loginusername")).sendKeys("Benamar");

        driver.findElement(By.id("loginpassword")).sendKeys("Benamar");

        driver.findElement(
                By.xpath("//button[contains(text(),'Log in')]")).click();

        
        // ici on verife login reussi en verifiant le message de bienvenue
        

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("nameofuser")));

        String welcomeMessage = driver.findElement(By.id("nameofuser")).getText();

        assertEquals(
                "Welcome Benamar",
                welcomeMessage);

        // on clique sur phones

        wait.until(ExpectedConditions.elementToBeClickable(By.id("itemc")));

        driver.findElement(By.id("itemc")).click();

        // 4. CHOISIR SAMSUNG GALAXY 

        By samsungS6 = By.xpath("//a[contains(@href,'prod.html?idp_=1') " +"and contains(text(),'Samsung galaxy s6')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(samsungS6));

        driver.findElement(samsungS6).click();

        
        //  verifier l'url du produit
        

        wait.until(ExpectedConditions.urlContains("prod.html?idp_=1"));

        assertEquals("https://www.demoblaze.com/prod.html?idp_=1",driver.getCurrentUrl());


        // 6. ADD TO CART
    

        wait.until(ExpectedConditions.elementToBeClickable( By.xpath("//a[contains(text(),'Add to cart')]")));

        driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]")).click();

        // verifer l'allerte produit ajouté

        wait.until(ExpectedConditions.alertIsPresent());

        String message = driver.switchTo().alert().getText();

        assertEquals("Product added.",message);

        driver.switchTo().alert().accept();

        // va dans le panier

        wait.until(
                ExpectedConditions.elementToBeClickable(By.id("cartur")));

        driver.findElement(By.id("cartur")).click();

        // 9. VERIFIER L'URL DU CART
        

        wait.until(ExpectedConditions.urlToBe("https://www.demoblaze.com/cart.html"));

        assertEquals("https://www.demoblaze.com/cart.html",driver.getCurrentUrl());

    
        // place order
        

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Place Order')]")));

        driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();

        // gerener des données avec faker pour remplir le formulaire

        Faker faker = new Faker();

        String nom = faker.name().fullName();

        String pays = faker.address().country();

        String ville = faker.address().city();

        String carte = faker.finance().creditCard();

        String mois = String.format("%02d",faker.number().numberBetween(1, 12));

        String annee = String.valueOf(faker.number().numberBetween(2026, 2035));

        // Afficher les données générées dans la console

        System.out.println("=================================");
        System.out.println("DONNEES FAKER");
        System.out.println("Nom      : " + nom);
        System.out.println("Pays     : " + pays);
        System.out.println("Ville    : " + ville);
        System.out.println("Carte    : " + carte);
        System.out.println("Mois     : " + mois);
        System.out.println("Année    : " + annee);
        System.out.println("=================================");

        // attendre que le formulaire soit visible avant de le remplir

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        // rempilir le formulaire avec les données générées par Faker

        driver.findElement(By.id("name")).sendKeys(nom);

        driver.findElement(By.id("country")).sendKeys(pays);

        driver.findElement(By.id("city")).sendKeys(ville);

        driver.findElement(By.id("card")).sendKeys(carte);

        driver.findElement(By.id("month")).sendKeys(mois);

        driver.findElement(By.id("year")).sendKeys(annee);

       //clique sur purchase

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Purchase')]")));

        driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();

       //verifier le message de confirmation d'achat

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text()," +"'Thank you for your purchase!')]")));

        String purchaseMessage = driver.findElement(By.xpath("//h2[contains(text()," +"'Thank you for your purchase!')]")).getText();

        assertEquals("Thank you for your purchase!",purchaseMessage);

    // clique sur ok pour fermer la confirmation d'achat

        wait.until(ExpectedConditions.elementToBeClickable( By.cssSelector("button.confirm.btn.btn-lg.btn-primary")));

        driver.findElement(By.cssSelector("button.confirm.btn.btn-lg.btn-primary")).click();
    }
}
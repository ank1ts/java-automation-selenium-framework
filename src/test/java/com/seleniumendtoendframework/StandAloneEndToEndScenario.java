package com.seleniumendtoendframework;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneEndToEndScenario {
    @Test
    public void StandaloneTest() throws InterruptedException {

        // Configure ChromeOptions to disable password manager
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Optional: maximize the window
        options.addArguments("--disable-web-security"); // Optional: disable web security
        options.addArguments("--no-proxy-server"); // Optional: disable proxy
 
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false); // Disable password saving
        prefs.put("profile.password_manager_enabled", false); // Disable password manager
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--incognito");

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        //Locators of login page.
        WebElement Username_text= driver.findElement(By.id("user-name"));
        WebElement Password_text= driver.findElement(By.id("password"));
        WebElement Login_button= driver.findElement(By.id("login-button"));

        //Enter user name and password.
        String username="standard_user";
        String password="secret_sauce";
        Username_text.sendKeys(username);
        Password_text.sendKeys(password);

        //Click on Login button.
        Login_button.click();

        //*******************Inventory Page************************ */

        //Verify Product tile to verify login is successful. 
        List<WebElement> Page_Title =driver.findElements(By.xpath("//span[@data-test='title']"));
        
        if(Page_Title.size()>0){
            System.out.println("Login is successful");
        }else{
            System.out.println("Login is not successful");
        }

        //Click on Add product to cart and verify button toggle to Remove
        WebElement Add_to_cart1= driver.findElement(By.xpath("//button[@data-test='add-to-cart-sauce-labs-backpack']"));
        Add_to_cart1.click();
        WebElement Remove_from_cart1= driver.findElement(By.xpath("//button[@data-test='remove-sauce-labs-backpack']"));
        String Remove_button = Remove_from_cart1.getText();
        if(Remove_button.equals("Remove")){
            System.out.println("Product is added to cart");
        }else{
            System.out.println("Product is not added to cart");
        }

        // get the price of Product 1
        WebElement Inventory_backpack_price= driver.findElement(By.xpath("(//div[text()='Sauce Labs Backpack']//parent::a//parent::div//following-sibling::div)[2]//child::div"));
        String inventory_backpack_price_text= Inventory_backpack_price.getText();

        WebElement Add_to_cart2= driver.findElement(By.xpath("//button[@data-test='add-to-cart-sauce-labs-bike-light']"));
        Add_to_cart2.click();
        WebElement Remove_from_cart2= driver.findElement(By.xpath("//button[@data-test='remove-sauce-labs-bike-light']"));
        String Remove_button2 = Remove_from_cart2.getText();
        if(Remove_button2.equals("Remove")){
            System.out.println("Product is added to cart");
        }else{
            System.out.println("Product is not added to cart");
        }
        // get the price of Product 2


        WebElement Inventory_BikeLight_price= driver.findElement(By.xpath("(//div[text()='Sauce Labs Bike Light']//parent::a//parent::div//following-sibling::div)[2]//div"));
        String Inventory_BikeLight_price_text=Inventory_BikeLight_price.getText();

        //Click on Cart icon and verify product is added to cart.
        WebElement Cart_icon= driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        Cart_icon.click();

        //******************Cart Page********************** */

        //Verify application moved to Cart page.
        List<WebElement> Cart_Page_Title =driver.findElements(By.xpath("//span[@data-test='title']"));
            if(Cart_Page_Title.size()>0){
            System.out.println("Application moved to Cart page");
        }else{
            System.out.println("Application is not moved to Cart page");
        }

        //Verify product 1 & 2 are added to cart.
        List<WebElement> Cart_Product1 =driver.findElements(By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']"));
        List<WebElement> Cart_Product2 =driver.findElements(By.xpath("//div[@data-test='inventory-item-name' and text()='Sauce Labs Bike Light']"));
        
        if(Cart_Product1.size()>0 && Cart_Product2.size()>0){
            System.out.println("Products are added to cart");
        }else{
            System.out.println("Products are not added to cart");
        }
        
        // Verify product 1 price
        WebElement Product1_price= driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']//parent::a//following-sibling::div//following-sibling::div//child::div"));
        String product1_price_text= Product1_price.getText();
        System.out.println("Product1 expected price= "+product1_price_text);

        //Verify price is same at inventory page and checkout page.
        Assert.assertEquals(inventory_backpack_price_text, product1_price_text);
        System.out.println("Product1 price validated");

        // Verify product 2 price
        WebElement Product2_price=driver.findElement(By.xpath("//div[text()='Sauce Labs Bike Light']//ancestor::div[@class='cart_item_label']//child::div[@class='item_pricebar']//div"));
        String Product2_price_text=Product2_price.getText();
        System.out.println("Product2 expected price= "+Product2_price_text);
        Assert.assertEquals(Inventory_BikeLight_price_text, Product2_price_text);
        System.out.println("Product2 price validated");

        //Click on Checkout button.
        WebElement Checkout_button= driver.findElement(By.xpath("//button[@data-test='checkout']"));
        Checkout_button.click();


        Thread.sleep(5000);
        driver.quit();

    }
    
}

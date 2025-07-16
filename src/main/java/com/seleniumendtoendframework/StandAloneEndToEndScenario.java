package com.seleniumendtoendframework;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneEndToEndScenario {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
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

        //Verify Product tile to verify login is successful. 
        List<WebElement> Page_Title =driver.findElements(By.xpath("//span[@data-test='title']"));
        
        if(Page_Title.size()>0){
            System.out.println("Login is successful");
        }else{
            System.out.println("Login is not successful");
        }




        Thread.sleep(5000);
        driver.quit();

    }
    
}

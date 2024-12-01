package com.nextgenqa.adapters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumAdapter {

    private WebDriver driver;

    public SeleniumAdapter() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        this.driver = new ChromeDriver();
    }

    public void openPage(String url) {
        driver.get(url);
    }

    public void fillField(String fieldId, String value) {
        WebElement field = driver.findElement(By.id(fieldId));
        field.clear();
        field.sendKeys(value);
    }

    public void clickButton(String buttonId) {
        WebElement button = driver.findElement(By.id(buttonId));
        button.click();
    }

    public void verifyText(String expectedText) {
        if (!driver.getPageSource().contains(expectedText)) {
            throw new AssertionError("Texto esperado não encontrado: " + expectedText);
        }
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}

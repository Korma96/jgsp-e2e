package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutButton {
    private WebDriver driver;

    @FindBy(xpath = "//a[contains(.,'Log out')]")
    private WebElement logoutButton;


    public LogoutButton(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(WebElement logoutButton) {
        this.logoutButton = logoutButton;
    }

    public void ensureLogoutButtonIsClickable() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(this.logoutButton));

    }
}

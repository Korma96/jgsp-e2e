package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserAdminPage {
    private WebDriver driver;

    @FindBy(id = "nav-show-requests-tab")
    private WebElement accountRequestsTab;

    public UserAdminPage(WebDriver driver) {
        this.driver = driver;
    }


    public WebElement getAccountRequestsTab() {
        return accountRequestsTab;
    }

    public void setAccountRequestsTab(WebElement accountRequestsTab) {
        this.accountRequestsTab = accountRequestsTab;
    }

    public void ensureIsClickableAccountRequestsTab() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(accountRequestsTab));
    }
}

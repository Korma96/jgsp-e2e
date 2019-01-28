package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRequestsTab {
    private WebDriver driver;

    @FindBy(id = "show_image_Steva_Stevic")
    private WebElement showImageButton;

    @FindBy(id = "decline_Steva_Stevic")
    private WebElement declineButton;

    @FindBy(id = "accept_Steva_Stevic")
    private WebElement acceptButton;

    @FindBy(className = "toast-message")
    private WebElement toastr;

    public AccountRequestsTab(WebDriver driver) {
        this.driver = driver;
    }


    public void ensureIsClikableShowImageButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(showImageButton));
    }

    public void ensureIsClikableDeclineButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(declineButton));
    }

    public void ensureIsClikableAcceptButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(acceptButton));
    }

    public void ensureIsVisibleToastr() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(toastr));
    }

    public WebElement getShowImageButton() {
        return showImageButton;
    }

    public void setShowImageButton(WebElement showImageButton) {
        this.showImageButton = showImageButton;
    }

    public WebElement getDeclineButton() {
        return declineButton;
    }

    public void setDeclineButton(WebElement declineButton) {
        this.declineButton = declineButton;
    }

    public WebElement getAcceptButton() {
        return acceptButton;
    }

    public void setAcceptButton(WebElement acceptButton) {
        this.acceptButton = acceptButton;
    }

    public WebElement getToastr() {
        return toastr;
    }

    public void setToastr(WebElement toastr) {
        this.toastr = toastr;
    }
}

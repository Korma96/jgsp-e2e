package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ModalDilaog {

    private WebDriver driver;

    @FindBy(xpath = "(//app-modal-dialog/div/div/div/div/div/div/div)[last()]")
    private WebElement price;

    @FindBy(xpath = "//button[contains(.,'Confirm purchase')]")
    private WebElement confirmPurchaseButton;

    @FindBy(xpath = "//button[contains(.,'Cancel purchase')]")
    private WebElement cancelPurchaseButton;

    @FindBy(css = ".modal")
    private WebElement dialog;

    @FindBy(className = "toast-message")
    private List<WebElement> toastrs;

    @FindBy(css = "button.close[aria-label='Close']")
    private WebElement closeButton;


    public ModalDilaog(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getConfirmPurchaseButton() {
        return confirmPurchaseButton;
    }

    public void setConfirmPurchaseButton(WebElement confirmPurchaseButton) {
        this.confirmPurchaseButton = confirmPurchaseButton;
    }

    public WebElement getCancelPurchaseButton() {
        return cancelPurchaseButton;
    }

    public void setCancelPurchaseButton(WebElement cancelPurchaseButton) {
        this.cancelPurchaseButton = cancelPurchaseButton;
    }

    public WebElement getPrice() {
        return price;
    }

    public void setPrice(WebElement price) {
        this.price = price;
    }

    public WebElement getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(WebElement closeButton) {
        this.closeButton = closeButton;
    }

    public void ensureIsClickableConfirmPurchaseButton() {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        //new WebDriverWait(driver, 10)
        wait.until(ExpectedConditions.elementToBeClickable(confirmPurchaseButton));
    }

    public void ensureIsClickableCancelPurchaseButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(cancelPurchaseButton));
    }

    public void ensureIsVisibleDialog() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(dialog));
    }

    public void ensureIsInvisibleDialog() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.invisibilityOf(dialog));
    }

    public void ensureIsInvisibleToastrs() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOfAllElements(toastrs));
    }

    public void ensureIsVisiblePrice() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(price));
    }

    public void ensureIsClickableCloseButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(closeButton));
    }
}

package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddPriceticketPage {
    private WebDriver driver;

    @FindBy(name = "datefrom")
    private WebElement datefrom;

    @FindBy(id = "pt")
    private WebElement passerngerType;

    @FindBy(id = "tt")
    private WebElement ticketType;

    @FindBy(id = "zone")
    private WebElement zone;

    @FindBy(id = "id_priceline")
    private WebElement priceline;

    @FindBy(id = "id_pricezone")
    private WebElement pricezone;

    //@FindBy(xpath = "//input[contains(.,'Add')]")
    @FindBy(xpath = "//input[@type='submit']")
    private WebElement addButton;

   /* @FindBy(css = "div.toast-message")
    private  WebElement toastMessage;*/

    public AddPriceticketPage(){

    }

    public AddPriceticketPage(WebDriver driver) {
        this.driver = driver;
    }



    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom.clear();
        this.datefrom.sendKeys(datefrom);
    }

    public WebElement getPasserngerType() {
        return passerngerType;
    }

    public void setPasserngerType(String passerngerType) {
//        this.passerngerType.clear();
        this.passerngerType.sendKeys(passerngerType);
    }

    public WebElement getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
//        this.ticketType.clear();
        this.ticketType.sendKeys(ticketType);
    }

    public WebElement getZone() {
        return zone;
    }

    public void setZone(String zone) {
 //       this.zone.clear();
        this.zone.sendKeys(zone);
    }

    public void setDatefrom(WebElement datefrom) {
        this.datefrom = datefrom;
    }

    public void setPasserngerType(WebElement passerngerType) {
        this.passerngerType = passerngerType;
    }

    public void setTicketType(WebElement ticketType) {
        this.ticketType = ticketType;
    }

    public void setZone(WebElement zone) {
        this.zone = zone;
    }

    public void setPriceline(WebElement priceline) {
        this.priceline = priceline;
    }

    public void setPricezone(WebElement pricezone) {
        this.pricezone = pricezone;
    }

   /* public WebElement getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(WebElement toastMessage) {
        this.toastMessage = toastMessage;
    }*/

    public WebElement getPriceline() {
        return priceline;
    }

    public void setPriceline(String priceline) {
        this.priceline.clear();
        this.priceline.sendKeys(priceline);
    }

    public WebElement getPricezone() {
        return pricezone;
    }

    public void setPricezone(String pricezone) {
        this.pricezone.clear();
        this.pricezone.sendKeys(pricezone);
    }

    public WebElement getAddButton() {
        return addButton;
    }

    public void setAddButton(WebElement addButton) {
        this.addButton = addButton;
    }

    public void ensureIsVisibleDatefrom() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(datefrom));

    }

    public void ensureIsVisiblePassengerType() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(passerngerType));

    }

    public void ensureIsVisibleTicketType() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(ticketType));

    }

    public void ensureIsVisibleZone() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(zone));

    }

    public void ensureIsVisiblePriceline() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(priceline));

    }

    public void ensureIsVisiblePricezone() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(pricezone));

    }

    public void ensureAddButtonIsClickable() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(addButton));

    }

}

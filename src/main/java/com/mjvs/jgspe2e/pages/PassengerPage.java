package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PassengerPage {
    // Driver instance.
    private WebDriver driver;

    @FindBy(id = "nav-buy-ticket-tab")
    private WebElement buyTicketTab;

    @FindBy(id = "nav-show-tickets-tab")
    private WebElement showTicketsTab;

    @FindBy(id = "nav-change-account-type-tab")
    private WebElement changeAccountTypeTab;

    @FindBy(id = "nav-show-schedule-tab")
    private WebElement showScheduleTab;

    @FindBy(id = "nav-positions-of-vehicles-tab")
    private WebElement positionsOfVehiclesTab;

    @FindBy(className = "toast-message")
    private WebElement toastr;


    public PassengerPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getBuyTicketTab() {
        return buyTicketTab;
    }

    public void setBuyTicketTab(WebElement buyTicketTab) {
        this.buyTicketTab = buyTicketTab;
    }

    public WebElement getShowTicketsTab() {
        return showTicketsTab;
    }

    public void setShowTicketsTab(WebElement showTicketsTab) {
        this.showTicketsTab = showTicketsTab;
    }

    public WebElement getChangeAccountTypeTab() {
        return changeAccountTypeTab;
    }

    public void setChangeAccountTypeTab(WebElement changeAccountTypeTab) {
        this.changeAccountTypeTab = changeAccountTypeTab;
    }

    public WebElement getShowScheduleTab() {
        return showScheduleTab;
    }

    public void setShowScheduleTab(WebElement showScheduleTab) {
        this.showScheduleTab = showScheduleTab;
    }

    public WebElement getPositionsOfVehiclesTab() {
        return positionsOfVehiclesTab;
    }

    public void setPositionsOfVehiclesTab(WebElement positionsOfVehiclesTab) {
        this.positionsOfVehiclesTab = positionsOfVehiclesTab;
    }

    public void ensureIsClickableBuyTicketTab() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(buyTicketTab));
    }

    public void ensureIsClickableChangeAccountTypeTab() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(changeAccountTypeTab));
    }

    public void ensureIsClickableShowTicketsTab() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(showTicketsTab));
    }

    public void ensureIsClickableShowScheduleTab() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(showScheduleTab));
    }

    public void ensureIsVisbileToastr() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(toastr));
    }

    public void ensureIsTicketSuccessfullyBought() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.textToBePresentInElement(toastr, "You have successfully bought a ticket!"));
    }

    public void ensureIsClickablePositionsOfVehiclesTab() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(positionsOfVehiclesTab));
    }
}

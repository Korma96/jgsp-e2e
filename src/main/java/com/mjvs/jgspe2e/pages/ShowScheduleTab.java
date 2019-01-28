package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ShowScheduleTab {
    private WebDriver driver;

    @FindBy(id = "id_schedule_valid_from")
    private WebElement scheduleValidFrom;

    @FindBy(id = "id_transport_type_in_show_schedule")
    private WebElement transport;

    @FindBy(id = "id_zone_in_show_schedule")
    private WebElement zone;

    @FindBy(id = "id_line_in_show_schedule")
    private WebElement line;

    @FindBy(id = "id_day")
    private WebElement day;

    @FindBy(xpath = "//button[contains(.,'Show')]")
    private WebElement showButton;

    @FindBy(className = "toast-message")
    private WebElement toastr;

    @FindBy(css = ".line > div > h3")
    private List<WebElement> schedules;

    @FindBy(className = "toast-message")
    private List<WebElement> toastrs;


    public ShowScheduleTab(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsVisbileScheduleValidFrom() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(scheduleValidFrom));
    }

    public void ensureIsVisbileTransport() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(transport));
    }

    public void ensureIsVisbileZone() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(zone));
    }

    public void ensureIsVisbileLine() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(line));
    }

    public void ensureIsVisbileDay() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(day));
    }

    public void ensureIsClickableShowButton() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(showButton));
    }

    public void ensureIsVisbileToastr() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(toastr));
    }

    public void ensureIsVisbileSchedules() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfAllElements(schedules));
    }

    public WebElement getScheduleValidFrom() {
        return scheduleValidFrom;
    }

    public void setScheduleValidFrom(WebElement scheduleValidFrom) {
        this.scheduleValidFrom = scheduleValidFrom;
    }

    public WebElement getTransport() {
        return transport;
    }

    public void setTransport(WebElement transport) {
        this.transport = transport;
    }

    public WebElement getZone() {
        return zone;
    }

    public void setZone(WebElement zone) {
        this.zone = zone;
    }

    public WebElement getLine() {
        return line;
    }

    public void setLine(WebElement line) {
        this.line = line;
    }

    public WebElement getDay() {
        return day;
    }

    public void setDay(WebElement day) {
        this.day = day;
    }

    public WebElement getShowButton() {
        return showButton;
    }

    public void setShowButton(WebElement showButton) {
        this.showButton = showButton;
    }

    public WebElement getToastr() {
        return toastr;
    }

    public void setToastr(WebElement toastr) {
        this.toastr = toastr;
    }

    public List<WebElement> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<WebElement> schedules) {
        this.schedules = schedules;
    }

    public void ensureIsInvisibleToastrs() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.invisibilityOfAllElements(toastrs));
    }
}

package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UpdateSchedulePage
{
    private WebDriver driver;

    @FindBy(id = "date_select")
    private WebElement dateSelect;

    @FindBy(id = "day_select")
    private WebElement daySelect;

    @FindBy(xpath = "/html/body/app-root/div/app-transport-admin-page/div/div[2]/app-update-schedule/" +
            "div[3]/app-timetable/div/table/tbody/tr[2]/td[1]/table/tbody/tr[3]/td/app-time/table/tbody/tr/td[3]/button")
    private WebElement firstRemoveTimeButton;

    @FindBy(xpath = "//button[@class='.addTimeButton']")
    private List<WebElement> addTimeButtons;

    @FindBy(xpath = "//input[@type='time']")
    private List<WebElement> addTimeInputs;

    public UpdateSchedulePage(WebDriver driver){
        this.driver = driver;
    }

    public void waitUntilPageLoads() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.xpath("//option"), 4));
    }

    public void selectValidFromDate(String validFromDate) {
        new Select(dateSelect).selectByVisibleText(validFromDate);
    }

    public void selectDayType(String dayType) {
        new Select(daySelect).selectByVisibleText(dayType);
    }

    public String getSelectedValidFromDateValue() {
        return new Select(dateSelect).getFirstSelectedOption().getText();
    }

    public String getSelectedDayTypeValue() {
        return new Select(daySelect).getFirstSelectedOption().getText();
    }

    public void clickFirstRemoveTimeButton(){
        firstRemoveTimeButton.click();
    }

    public void clickAddTimeForDirectionAButton()
    {
        addTimeButtons.get(0).click();
    }

    public void addTimeForDirectionA(String time, String type){
        addTimeInputs.get(0).sendKeys(time);
        addTimeInputs.get(0).sendKeys(type);
        addTimeButtons.get(0).click();
    }

}

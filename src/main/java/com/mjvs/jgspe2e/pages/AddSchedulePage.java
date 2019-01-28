package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AddSchedulePage
{
    private WebDriver driver;
    private String addSchedulePageUrl = "http://localhost:4200/transport/add_schedule";

    @FindBy(id = "date_input")
    private WebElement dateInput;

    @FindBy(id = "day_type")
    private WebElement dayTypeInput;

    @FindBy(xpath = "//button[@class='.addTimeButton']")
    private List<WebElement> addTimeButtons;

    @FindBy(xpath = "//input[@type='time']")
    private List<WebElement> addTimeInputs;

    @FindBy(css = ".removeTimeButton")
    private List<WebElement> removeButtons;

    @FindBy(css = ".nextButton")
    private WebElement nextButton;

    public AddSchedulePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitUntilPageLoads()
    {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("date_input")));
    }

    public void setDateInputValue(String value)
    {
        dateInput.sendKeys(value);
    }

    public String getCurrentDayType()
    {
        return dayTypeInput.getAttribute("value");
    }

    public void addTimeForDirectionA(String time, String type){
        addTimeInputs.get(0).sendKeys(time);
        addTimeInputs.get(0).sendKeys(type);
        addTimeButtons.get(0).click();
    }

    public void addTimeForDirectionB(String time, String type)
    {
        addTimeInputs.get(1).sendKeys(time);
        addTimeInputs.get(1).sendKeys(type);
        addTimeButtons.get(1).click();
    }

    public String getNextButtonValue()
    {
        return nextButton.getAttribute("textContent");
    }

    public void clickNextButton()
    {
        nextButton.click();
    }

    public void clickAddTimeForDirectionAButton()
    {
        addTimeButtons.get(0).click();
    }

    public void clickAddTimeForDirectionBButton()
    {
        addTimeButtons.get(1).click();
    }

    public int getNumberOfTimesForBothDirections()
    {
        return removeButtons.size();
    }
}

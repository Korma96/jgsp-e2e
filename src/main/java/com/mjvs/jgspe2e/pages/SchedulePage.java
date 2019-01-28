package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SchedulePage
{
    private WebDriver driver;
    public String url = "http://localhost:4200/transport/schedule";

    @FindBy(css = ".lineButton")
    private List<WebElement> lineButtons;

    @FindBy(css = ".manipulateButton")
    private List<WebElement> manipulateLineButtons;

    public SchedulePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitUntilPageLoads() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table"), 1));
    }

    private void navigateToPage() {
        driver.navigate().to(url);
    }

    public void navigateToPageAndWaitToLoad(){
        navigateToPage();
        waitUntilPageLoads();
    }

    public boolean clickOnLineButton(String lineName){
        for(int i=0; i<lineButtons.size(); i++){
            if(lineButtons.get(i).getAttribute("textContent").trim().equals(lineName)){
                lineButtons.get(i).click();
                return true;
            }
        }
        return false;
    }

    public boolean clickOnManipulateLineButton(String lineName){
        for(int i=0; i<lineButtons.size(); i++){
            if(lineButtons.get(i).getAttribute("textContent").trim().equals(lineName)){
                manipulateLineButtons.get(i).click();
                return true;
            }
        }
        return false;
    }

}

package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class PositionsOfVehiclesTab {
    private WebDriver driver;

    @FindBy(id = "id_transport_type")
    private WebElement transport;

    @FindBy(css = "img[src='/assets/icons/bus_station_blue.png'], img[src='/assets/icons/a.svg'], img[src='/assets/icons/b.svg']")
    private List<WebElement> blueStations;

    @FindBy(css = "img[src='/assets/icons/bus_station_red.png'], img[src='/assets/icons/a.svg'], img[src='/assets/icons/b.svg']")
    private List<WebElement> redStations;

    @FindBy(css = "img[src='/assets/icons/bus.png']")
    private List<WebElement> buses;

    @FindBy(id = "line_1A")
    private WebElement line1A;

    @FindBy(id = "line_1B")
    private WebElement line1B;

    @FindBy(id = "line_2B")
    private WebElement line2B;

    @FindBy(id = "id_label")
    private WebElement positionsOfVehiclesCheckBox;

    public PositionsOfVehiclesTab(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getTransport() {
        return transport;
    }

    public void setTransport(WebElement transport) {
        this.transport = transport;
    }

    public List<WebElement> getBlueStations() {
        return blueStations;
    }

    public void setBlueStations(List<WebElement> blueStations) {
        this.blueStations = blueStations;
    }

    public List<WebElement> getRedStations() {
        return redStations;
    }

    public List<WebElement> getBuses() {
        return buses;
    }

    public void setBuses(List<WebElement> buses) {
        this.buses = buses;
    }

    public void setRedStations(List<WebElement> redStations) {
        this.redStations = redStations;
    }

    public WebElement getLine1A() {
        return line1A;
    }

    public void setLine1A(WebElement line1A) {
        this.line1A = line1A;
    }

    public WebElement getLine1B() {
        return line1B;
    }

    public void setLine1B(WebElement line1B) {
        this.line1B = line1B;
    }

    public WebElement getLine2B() {
        return line2B;
    }

    public void setLine2B(WebElement line2B) {
        this.line2B = line2B;
    }

    public WebElement getPositionsOfVehiclesCheckBox() {
        return positionsOfVehiclesCheckBox;
    }

    public void setPositionsOfVehiclesCheckBox(WebElement positionsOfVehicclesCheckBox) {
        this.positionsOfVehiclesCheckBox = positionsOfVehicclesCheckBox;
    }

    public void ensureIsVisibleTransport() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(transport));
    }

    public void ensureIsClickableLine1A() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(line1A));
    }

    public void ensureIsVisibleLine1B() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(line1B));
    }
    public void ensureIsDisabledLine1B() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.not(ExpectedConditions.attributeContains(line1B, "disabled", "")));
    }

    public void ensureIsClickableLine2B() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(line2B));
    }

    public void ensureIsClickablePositionsOfVehiclesCheckBox() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(positionsOfVehiclesCheckBox));
    }

    public void ensureIsVisibleBlueStations(int elementsCount) {
        new WebDriverWait(driver, 30)
                .until(visibilityOfNElements(blueStations, elementsCount));
    }

    public void ensureIsVisibleRedStations(int elementsCount) {
        new WebDriverWait(driver, 30)
                .until(visibilityOfNElements(redStations, elementsCount));
    }

    public void ensureIsVisibleBuses(int elementsCount) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.time.Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        wait.until(visibilityOfNElements(buses, elementsCount));
    }

    public static ExpectedCondition<List<WebElement>> visibilityOfNElements(
            final List<WebElement> elements, final int elementsCount) {

        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                //List<WebElement> elements = driver.findElements(locator);

                // KEY is here - we are "failing" the expected condition
                // if there are less than elementsCount elements
                if (elements.size() < elementsCount) {
                    return null;
                }

                for(int i = 0; i < elementsCount; i++){
                    if(!elements.get(i).isDisplayed()){
                        return null;
                    }
                }
                return elements;
            }

            @Override
            public String toString() {
                return "visibility of N elements";
            }
        };
    }

    public void ensureIsInvisibleBlueStations() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.invisibilityOfAllElements(blueStations));
    }

    public void ensureIsInvisibleRedStations() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.invisibilityOfAllElements(redStations));
    }

    public void ensureIsInvisibleBuses() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.invisibilityOfAllElements(buses));
    }
}

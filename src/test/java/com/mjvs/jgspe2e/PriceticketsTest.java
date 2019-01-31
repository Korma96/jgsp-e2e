package com.mjvs.jgspe2e;

import com.mjvs.jgspe2e.pages.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import static org.junit.Assert.*;

public class PriceticketsTest {

    private WebDriver browser;
    private String baseUrl = "http://localhost:4200";

    private LoginPage loginPage;
    private AddPriceticketPage addPriceticketPage;


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        browser = new ChromeDriver();


        browser.manage().window().maximize();
        browser.navigate().to(baseUrl + "/login");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        addPriceticketPage = PageFactory.initElements(browser,AddPriceticketPage.class);


        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername("nenad");
        loginPage.setPassword("nenad");
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

    }

    @Test
    public void TestPriceTickets() {
        WaitUntilZonePageIsShown();

        assertEquals("http://localhost:4200/transport/zone", browser.getCurrentUrl());

        WebElement priceTickets = browser.findElement(By.linkText("PriceTickets"));
        assertTrue(priceTickets.isDisplayed());

        priceTickets.click();

        WaitUntilPriceticketPageIsShown();

        assertEquals("http://localhost:4200/transport/priceticket", browser.getCurrentUrl());

        List<WebElement> pricetickets = browser.findElements(By.cssSelector("table tbody tr div"));
        int currentSize = pricetickets.size();

        WebElement addPriceticketSection = browser.findElement(By.linkText("ADD PRICETICKET"));
        assertTrue(addPriceticketSection.isDisplayed());
        addPriceticketSection.click();

        addPriceticketPage.ensureIsVisibleDatefrom();
        addPriceticketPage.setDatefrom("27.01.2019");

        addPriceticketPage.ensureIsVisiblePassengerType();
        new Select(addPriceticketPage.getPasserngerType()).selectByVisibleText("OTHER");
        //addPriceticketPage.setPasserngerType("OTHER");

        addPriceticketPage.ensureIsVisibleTicketType();
        new Select(addPriceticketPage.getTicketType()).selectByVisibleText("YEARLY");
        //addPriceticketPage.setTicketType("YEARLY");

        addPriceticketPage.ensureIsVisibleZone();
        new Select(addPriceticketPage.getZone()).selectByVisibleText("prigradske_linije");
        //addPriceticketPage.setZone("prigradske_linije");

        addPriceticketPage.ensureIsVisiblePriceline();
        addPriceticketPage.setPriceline("2000");

        addPriceticketPage.ensureIsVisiblePricezone();
        addPriceticketPage.setPricezone("3000");

        addPriceticketPage.ensureAddButtonIsClickable();
        addPriceticketPage.getAddButton().click();

        addPriceticketPage.ensureIsInvisibleToastrs();

        pricetickets = browser.findElements(By.cssSelector("table tbody tr div"));

        if(currentSize != pricetickets.size()){
            assertEquals((currentSize+1),pricetickets.size());
        }else{
            assertEquals(currentSize,pricetickets.size());
        }


        WebElement showPriceTicketsSection = browser.findElement(By.linkText("SHOW PRICETICKETS"));
        assertTrue(showPriceTicketsSection.isDisplayed());
        showPriceTicketsSection.click();

        WaitUntilPriceticketPageIsShown();

        pricetickets = browser.findElements(By.cssSelector("table tbody tr div"));
        currentSize = pricetickets.size();
        Random r = new Random();
        int indexForDelete = r.nextInt(currentSize);

        List<WebElement> buttonsDelete = browser.findElements(By.cssSelector("table tbody tr div button[class='btn btn-danger']"));


        assertEquals(currentSize,buttonsDelete.size());

        new WebDriverWait(browser, 20)
                .until(ExpectedConditions.elementToBeClickable( buttonsDelete.get(indexForDelete)));
        buttonsDelete.get(indexForDelete).click();

        WebElement tst = browser.findElement(By.cssSelector("div.toast-message"));

        new WebDriverWait(browser, 10)
                .until(ExpectedConditions.invisibilityOfAllElements(tst));

        pricetickets = browser.findElements(By.cssSelector("table tbody tr div"));
        assertEquals(currentSize-1,pricetickets.size());


        WaitUntilPriceticketPageIsShown();
        currentSize = pricetickets.size();
        int indexForEdit = r.nextInt(currentSize);



        List<WebElement> buttonsEdit = browser.findElements(By.cssSelector("table tbody tr div button[class='btn btn-primary']"));

        assertEquals(currentSize,buttonsEdit.size());

        WaitUntilPriceticketPageIsShown();

        new WebDriverWait(browser, 30)
                .until(ExpectedConditions.elementToBeClickable( buttonsEdit.get(indexForEdit)));
        buttonsEdit.get(indexForEdit).click();

        WaitUntilPriceticketEditPageIsShown();

        WebElement pricelineField = browser.findElement(By.id("priceline"));
        WebElement pricezoneField = browser.findElement(By.id("pricezone"));
        WebElement datefromField = browser.findElement(By.id("datefrom"));

        WebElement save = browser.findElement(By.cssSelector("div button[value='Save']"));
        WebElement cancel = browser.findElement(By.cssSelector("div button[value='Cancel']"));



        new WebDriverWait(browser, 10)
                .until(ExpectedConditions.visibilityOf(pricelineField));

        String priceOfLine = pricelineField.getAttribute("value");
        pricelineField.clear();
        int oldPriceline = Integer.parseInt(priceOfLine);
        int newPriceline = oldPriceline + 153;
        pricelineField.sendKeys(Integer.toString(newPriceline));

        String priceOfZone = pricezoneField.getAttribute("value");
        pricezoneField.clear();
        int oldPricezone= Integer.parseInt(priceOfZone);
        int newPricezone = oldPricezone + 273;
        pricezoneField.sendKeys(Integer.toString(newPricezone));

        String oldDatefrom = datefromField.getAttribute("value");
        datefromField.clear();
        String newDatefrom = "05.02.2019.";
        datefromField.sendKeys(newDatefrom);

        new WebDriverWait(browser, 15)
                .until(ExpectedConditions.elementToBeClickable(save));
        save.click();

        tst = browser.findElement(By.cssSelector("div.toast-message"));

        new WebDriverWait(browser, 10)
                .until(ExpectedConditions.invisibilityOfAllElements(tst));

        //pricelineField = browser.findElement(By.id("priceline"));
        assertTrue(!pricelineField.getAttribute("value").equals(oldPriceline));
        //pricezoneField = browser.findElement(By.id("pricezone"));
        assertTrue(!pricezoneField.getAttribute("value").equals(oldPricezone));
        //datefromField = browser.findElement(By.id("datefrom"));
        assertTrue(!datefromField.getAttribute("value").equals(oldDatefrom));

        WaitUntilPriceticketPageIsShown();
        pricetickets = browser.findElements(By.cssSelector("table tbody tr div"));
        indexForEdit = r.nextInt(pricetickets.size());
        WaitUntilPriceticketPageIsShown();


        /*buttonsEdit = browser.findElements(By.cssSelector("table tbody tr div button[class='btn btn-primary']"));
        new WebDriverWait(browser, 30)
                .until(ExpectedConditions.elementToBeClickable(buttonsEdit.get(indexForEdit)));
        buttonsEdit.get(indexForEdit).click();

        WaitUntilPriceticketEditPageIsShown();

        datefromField = browser.findElement(By.id("datefrom"));
        oldDatefrom = datefromField.getAttribute("value");
        datefromField.clear();
        newDatefrom = "safasfasfaveqcac352r";
        datefromField.sendKeys(newDatefrom);


        save = browser.findElement(By.cssSelector("div button[value='Save']"));
        new WebDriverWait(browser, 20)
                .until(ExpectedConditions.elementToBeClickable(save));
        save.click();

        WaitUntilPriceticketPageIsShown();

        buttonsEdit = browser.findElements(By.cssSelector("table tbody tr div button[class='btn btn-primary']"));

        new WebDriverWait(browser, 30)
                .until(ExpectedConditions.elementToBeClickable(buttonsEdit.get(indexForEdit)));
        buttonsEdit.get(indexForEdit).click();

        WaitUntilPriceticketEditPageIsShown();



        assertFalse(oldDatefrom.equals(browser.findElement(By.id("datefrom")).getAttribute("value")));

        cancel.click();*/


    }

    private void WaitUntilZonePageIsShown()
    {
        new WebDriverWait(browser, 10)
                .until(ExpectedConditions.elementSelectionStateToBe(By.id("table_zones"), false));
    }

    private void WaitUntilPriceticketPageIsShown()
    {
        new WebDriverWait(browser, 10)
                .until(ExpectedConditions.elementSelectionStateToBe(By.id("nav-add-price-ticket-tab"), false));
    }

    private void WaitUntilPriceticketEditPageIsShown()
    {
        new WebDriverWait(browser, 10)
                .until(ExpectedConditions.elementSelectionStateToBe(By.id("datefrom"), false));
    }

}

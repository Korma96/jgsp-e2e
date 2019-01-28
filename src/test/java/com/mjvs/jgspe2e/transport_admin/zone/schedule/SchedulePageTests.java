package com.mjvs.jgspe2e.transport_admin.zone.schedule;

import com.mjvs.jgspe2e.pages.AddSchedulePage;
import com.mjvs.jgspe2e.pages.LoginPage;
import com.mjvs.jgspe2e.pages.SchedulePage;
import com.mjvs.jgspe2e.pages.UpdateSchedulePage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// Update schedule will update newly added schedule, so it is necessary to fix method order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchedulePageTests
{
    // some attributes must be static because method annotated with @BeforeClass must be static
    private static LoginPage loginPage;
    private static SchedulePage schedulePage;
    private static AddSchedulePage addSchedulePage;
    private static UpdateSchedulePage updateSchedulePage;
    private static final String baseUrl = "http://localhost:4200";
    private static final String transportAdminUsername = "nenad";
    private static final String transportAdminPassword = "nenad";
    private final String[] workdayTimeA = new String[]{"05:12", "AM"};
    private final String[] saturdayTimeA = new String[]{"04:22", "PM"};
    private final String[] workdayTimeB = new String[]{"04:33", "PM"};
    private final String[] saturdayTimeB= new String[]{"12:11", "AM"};
    private final String lineName = "1";
    private static final int timeout = 10;
    private static WebDriver browser;

    @BeforeClass
    public static void SetupSelenium() {
        //instantiate browser
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        browser = new ChromeDriver();
        //maximize window
        browser.manage().window().maximize();
        browser.navigate().to(baseUrl + "/login");

        schedulePage = PageFactory.initElements(browser, SchedulePage.class);
        updateSchedulePage = PageFactory.initElements(browser, UpdateSchedulePage.class);
        addSchedulePage = PageFactory.initElements(browser, AddSchedulePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        login();
    }

    private static void login() {
        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(transportAdminUsername);
        loginPage.setPassword(transportAdminPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();
        new WebDriverWait(browser, timeout)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table"), 1));
    }

    @Test
    public void addSchedule() throws Exception
    {
        final String WORKDAY = "WORKDAY";
        final String SATURDAY = "SATURDAY";
        final String SUNDAY = "SUNDAY";
        final String Next = "Next";
        final String Finish = "Finish";
        final String validFromDate = "04/23/2019";

        // go to add schedule page for chosen line
        schedulePage.navigateToPageAndWaitToLoad();
        assertTrue(schedulePage.clickOnManipulateLineButton(lineName));
        addSchedulePage.waitUntilPageLoads();

        // populate fields
        assertEquals(WORKDAY, addSchedulePage.getCurrentDayType());
        // try to click next without setting valid from date
        assertEquals(Next, addSchedulePage.getNextButtonValue());
        addSchedulePage.clickNextButton();
        assertEquals(WORKDAY, addSchedulePage.getCurrentDayType()); // schedule is still unchanged
        // set valid from date
        addSchedulePage.setDateInputValue(validFromDate);
        // try to click next without setting any time
        addSchedulePage.clickNextButton();
        assertEquals(WORKDAY, addSchedulePage.getCurrentDayType()); // schedule is still unchanged
        // click to add time without setting time
        addSchedulePage.clickAddTimeForDirectionAButton();
        assertEquals(0, addSchedulePage.getNumberOfTimesForBothDirections()); // time not added
        // set time for direction A
        addSchedulePage.addTimeForDirectionA(workdayTimeA[0], workdayTimeA[1]);
        // try to click next without setting any time for direction B
        addSchedulePage.clickNextButton();
        assertEquals(WORKDAY, addSchedulePage.getCurrentDayType()); // schedule is still unchanged
        // set time for direction B
        addSchedulePage.addTimeForDirectionB(workdayTimeB[0], workdayTimeB[1]);
        // proceed to next schedule
        addSchedulePage.clickNextButton();

        assertEquals(SATURDAY, addSchedulePage.getCurrentDayType());
        assertEquals(Next, addSchedulePage.getNextButtonValue());
        // set times
        addSchedulePage.addTimeForDirectionA(saturdayTimeA[0], saturdayTimeA[1]);
        addSchedulePage.addTimeForDirectionB(saturdayTimeB[0], saturdayTimeB[1]);
        // proceed to next schedule
        addSchedulePage.clickNextButton();

        assertEquals(SUNDAY, addSchedulePage.getCurrentDayType());
        assertEquals(Finish, addSchedulePage.getNextButtonValue());
        // add same times
        addSchedulePage.clickAddTimeForDirectionAButton();
        addSchedulePage.clickAddTimeForDirectionBButton();

        // save
        addSchedulePage.clickNextButton();
        schedulePage.waitUntilPageLoads();
        assertEquals(schedulePage.url, browser.getCurrentUrl());
    }

    @Test
    public void updateSchedule() throws Exception
    {
        final String Workday = "Workday";
        final String Saturday = "Saturday";
        final String Sunday = "Sunday";
        final String validFromDateFormatted = "2019-04-23";

        // go to update schedule page for chosen line
        schedulePage.navigateToPageAndWaitToLoad();
        assertTrue(schedulePage.clickOnLineButton(lineName));
        // choose previous added date
        updateSchedulePage.waitUntilPageLoads();
        updateSchedulePage.selectValidFromDate(validFromDateFormatted);
        assertEquals(validFromDateFormatted, updateSchedulePage.getSelectedValidFromDateValue());
        // choose day type
        updateSchedulePage.selectDayType(Saturday);
        assertEquals(Saturday, updateSchedulePage.getSelectedDayTypeValue());
        // change times
        /*
        updateSchedulePage.clickFirstRemoveTimeButton();
        Thread.sleep(2000);
        updateSchedulePage.addTimeForDirectionA(workdayTimeA[0], workdayTimeA[1]);
        updateSchedulePage.clickAddTimeForDirectionAButton();
        Thread.sleep(2000);
        */
    }

    @AfterClass
    public static void CloseSelenium() {
        // Shutdown the browser
        browser.quit();
    }


}

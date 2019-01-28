package com.mjvs.jgspe2e;

import com.mjvs.jgspe2e.pages.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PassengerPageTest {
    private WebDriver browser;
    private String baseUrl = "http://localhost:4200";
    private String passengerUsername = "steva";
    private String passengerPassword = "steva";
    private String userAdminUsername = "darko";
    private String userAdminPassword = "darko";

    private LoginPage loginPage;
    private PassengerPage passengerPage;
    private BuyTicketTab buyTicketTab;
    private ShowTicketsTab showTicketsTab;
    private ModalDilaog modalDialog;
    private ChangeAccountTypeTab changeAccountTypeTab;
    private LogoutButton logoutButton;
    private UserAdminPage userAdminPage;
    private AccountRequestsTab accountRequestsTab;
    private ShowScheduleTab showScheduleTab;
    private PositionsOfVehiclesTab positionsOfVehiclesTab;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

    // selektovanje button-a sa nekim tekstom u sebi
    //String xpathStr = String.format("//button[contains(.,'%s')]", nekiTekst);
    //driver.findElement(By.xpath(xpathStr));

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        browser = new ChromeDriver();

        browser.manage().window().maximize();
        browser.navigate().to(baseUrl + "/login");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        passengerPage = PageFactory.initElements(browser, PassengerPage.class);
        buyTicketTab = PageFactory.initElements(browser, BuyTicketTab.class);
        showTicketsTab = PageFactory.initElements(browser, ShowTicketsTab.class);
        modalDialog = PageFactory.initElements(browser, ModalDilaog.class);
        changeAccountTypeTab = PageFactory.initElements(browser, ChangeAccountTypeTab.class);
        logoutButton = PageFactory.initElements(browser, LogoutButton.class);
        userAdminPage = PageFactory.initElements(browser, UserAdminPage.class);
        accountRequestsTab = PageFactory.initElements(browser, AccountRequestsTab.class);
        showScheduleTab = PageFactory.initElements(browser, ShowScheduleTab.class);
        positionsOfVehiclesTab = PageFactory.initElements(browser, PositionsOfVehiclesTab.class);

        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(passengerUsername);
        loginPage.setPassword(passengerPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();
    }

    @Test
    public void buyTicket() {
        // Odlazimo na tab show tickets da bismo videli koliko ima starih ticket-a pre kupovine novih
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        int oldNumOfTickets = showTicketsTab.getNumOfTickets();

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo tram za transport i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTransport();
        new Select(buyTicketTab.getTransport()).selectByVisibleText("tram");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Nije moguce dobiti cenu jer trenutno ne postoji cenovnik za tram
        buyTicketTab.ensureIsVisibleToastrError();
        assertEquals("The price has not been successfully delivered!",
                buyTicketTab.getToastrEror().getText().trim());
        modalDialog.ensureIsVisiblePrice();
        assertEquals("Please wait...", modalDialog.getPrice().getText().trim());
        // Gasimo dialog za potvrdu kupovine
        modalDialog.ensureIsClickableCancelPurchaseButton();
        modalDialog.getCancelPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();

        // Izaberemo metro za transport i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTransport();
        new Select(buyTicketTab.getTransport()).selectByVisibleText("metro");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Nije moguce dobiti cenu jer trenutno ne postoji cenovnik za metro
        buyTicketTab.ensureIsVisibleToastrError();
        assertEquals("The price has not been successfully delivered!",
                buyTicketTab.getToastrEror().getText().trim());
        modalDialog.ensureIsVisiblePrice();
        assertEquals("Please wait...", modalDialog.getPrice().getText().trim());
        // Gasimo dialog za potvrdu kupovine
        modalDialog.ensureIsClickableCancelPurchaseButton();
        modalDialog.getCancelPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();

        // Izaberemo metro za transport i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleDate();
        buyTicketTab.getDate().clear(); // simuliramo prazno polje za date
        // ovo radimo da bi ngbDatepicker izgubio fokus
        //**********************************************
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();
        //**********************************************
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Nije moguce dobiti cenu jer trenutno ne postoji cenovnik za metro
        buyTicketTab.ensureIsVisibleToastrError();
        assertEquals("You did not select a date!",
                buyTicketTab.getToastrEror().getText().trim());

        // Izaberemo metro za transport i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleDate();
        buyTicketTab.setDate("25.sdsdsd.2030."); // simuliramo pogresan date
        // ovo radimo da bi ngbDatepicker izgubio fokus
        //**********************************************
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();
        //**********************************************
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Nije moguce dobiti cenu jer trenutno ne postoji cenovnik za metro
        buyTicketTab.ensureIsVisibleToastrError();
        assertEquals("Not valid date!",
                buyTicketTab.getToastrEror().getText().trim());

        // Izaberemo metro za transport i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleDate();
        buyTicketTab.setDate("20.07.2005."); // simuliramo date koji je vec prosao
        // ovo radimo da bi ngbDatepicker izgubio fokus
        //**********************************************
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();
        //**********************************************
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Nije moguce dobiti cenu jer trenutno ne postoji cenovnik za metro
        buyTicketTab.ensureIsVisibleToastrError();
        assertEquals("The selected date has already passed!",
                buyTicketTab.getToastrEror().getText().trim());

        // Izaberemo bus za transport i zonu prigradske_linije, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTransport();
        new Select(buyTicketTab.getTransport()).selectByVisibleText("bus");
        buyTicketTab.ensureIsVisibleZone();
        new Select(buyTicketTab.getZone()).selectByVisibleText("prigradske_linije");
        LocalDate date = LocalDate.now();
        buyTicketTab.ensureIsVisibleDate();
        buyTicketTab.setDate(date.getDayOfMonth() + "."+date.getMonthValue()+"."+date.getYear()+".");
        // ovo radimo da bi ngbDatepicker izgubio fokus
        //**********************************************
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();
        //**********************************************
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        int newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo za ticketType monthly, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("monthly");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo za ticketType yearly, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("yearly");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo za zonu medjumesne_linije, kliknemo na Ticket for line, za liniju izaberemo 60,
        // za ticketType onetime, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleZone();
        new Select(buyTicketTab.getZone()).selectByVisibleText("medjumesne_linije");
        buyTicketTab.ensureIsClickableTicketForLine();
        buyTicketTab.getTicketForLine().click();
        buyTicketTab.ensureIsVisibleLine();
        new Select(buyTicketTab.getLine()).selectByVisibleText("60");
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("onetime");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo za ticketType daily, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("daily");
        date = LocalDate.now();
        int day = date.getDayOfMonth();
        if(day < YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth()) day++;
        buyTicketTab.ensureIsVisibleDate();
        buyTicketTab.setDate(day + "."+date.getMonthValue()+"."+date.getYear()+".");
        // ovo radimo da bi ngbDatepicker izgubio fokus
        //**********************************************
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();
        //**********************************************
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo za ticketType monthly, za month sledeci mesec u odnosu na trenutni, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("monthly");
        buyTicketTab.ensureIsVisibleMonth();
        int month = LocalDate.now().getMonthValue();
        if(month != 12) month++; // hocemo sledeci mesec
        String monthStr = Month.of(month).name();
        monthStr = monthStr.charAt(0) + monthStr.substring(1).toLowerCase();
        new Select(buyTicketTab.getMonth()).selectByVisibleText(monthStr);
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;

        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Izaberemo za ticketType yearly, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("yearly");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Odlazimo na tab show tickets da bismo videli koliko ima ticket-a nakon kupovine jednog ticket-a
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        passengerPage.ensureIsTicketSuccessfullyBought();
        newNumOfTickets = showTicketsTab.getNumOfTickets();
        assertEquals(newNumOfTickets, oldNumOfTickets + 1);
        oldNumOfTickets = newNumOfTickets;
    }

    @Test
    public void showTickets() {
        // Prebacujemo se na tab buy ticket
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();

        // Najpre kupimo par karata da bismo mogli testirati activate, download i remove ticket u show tickets tabu

        // Izaberemo za zonu prigradske_linije, kliknemo na Ticket for line, za liniju izaberemo 60,
        // za ticketType onetime, i pokusamo kupiti ticket
        buyTicketTab.ensureIsVisibleZone();
        new Select(buyTicketTab.getZone()).selectByVisibleText("prigradske_linije");
        buyTicketTab.ensureIsClickableTicketForLine();
        buyTicketTab.getTicketForLine().click();
        buyTicketTab.ensureIsVisibleTicketType();
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("onetime");
        buyTicketTab.ensureIsVisibleLine();
        new Select(buyTicketTab.getLine()).selectByVisibleText("35");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        // Kupimo jos jednu
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        buyTicketTab.ensureIsVisibleTicketType();
        //kupujemo kartu za danasnji datum, jer ce ona odmah poceti da vazi, i ocekujemo da necemo smeti da je obrisemo
        new Select(buyTicketTab.getTicketType()).selectByVisibleText("daily");
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();
        //Zatim pokusavamo kupiti kartu za sutrasnji datum, sem ako je danas vec poslednji dan u tekucem mesecu,
        //jer za kartu koju pocinje da vazi od sutra, ocekujemo da cemo moci da je obrisemo
        LocalDate date = LocalDate.now();
        int day = date.getDayOfMonth();
        if(day < YearMonth.of(date.getYear(), date.getMonthValue()).lengthOfMonth()) day++;
        buyTicketTab.ensureIsVisibleDate();
        buyTicketTab.setDate(day + "."+date.getMonthValue()+"."+date.getYear()+".");
        // ovo radimo da bi ngbDatepicker izgubio fokus
        //**********************************************
        passengerPage.ensureIsClickableBuyTicketTab();
        passengerPage.getBuyTicketTab().click();
        //**********************************************
        buyTicketTab.ensureIsClickableBuyTicketButton();
        buyTicketTab.getBuyTicketButton().click();
        // Otvorio se dodatni dialog za pregled i potvrdu kupovine ticket-a
        modalDialog.ensureIsClickableConfirmPurchaseButton();
        modalDialog.getConfirmPurchaseButton().click();
        modalDialog.ensureIsInvisibleDialog();

        // Odlazimo na tab show tickets da bismo testirali activate, download i remove ticket
        passengerPage.ensureIsClickableShowTicketsTab();
        passengerPage.getShowTicketsTab().click();
        showTicketsTab.ensureIsVisibleTickets();
        showTicketsTab.ensureIsInvisibleToastrs();
        List<WebElement> tickets = showTicketsTab.getTickets();

        //showTicketsTab.ensureIsInVisibleToastr();

        int numOfTickets = showTicketsTab.getNumOfTickets();
        int oldNumOfTuckets = numOfTickets;

        WebElement tr;
        for(int i = 0; i < tickets.size(); i++) {
            showTicketsTab.ensureIsInvisibleToastrs();
            tr = tickets.get(i);
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            WebElement lastTd = tds.get(tds.size() - 1);
            List<WebElement> buttons = showTicketsTab.getButtons(lastTd); // [activate, download, remove]

            String startDateAndTimeStr = tds.get(1).getText().trim();
            String endDateAndTimeStr = tds.get(2).getText().trim();

            assertTrue(showTicketsTab.isDownloadButton(buttons.get(1)));
            showTicketsTab.ensureIsClickableButton(buttons.get(1)); // download
            //buttons.get(1).click();

            if(startDateAndTimeStr.equals("not yet activated") && endDateAndTimeStr.equals("not yet activated")) {
                assertTrue(showTicketsTab.isRemoveButton(buttons.get(2)));
                showTicketsTab.ensureIsClickableButton(buttons.get(2)); // remove
                assertTrue(showTicketsTab.isActivateButton(buttons.get(0)));
                showTicketsTab.ensureIsClickableButton(buttons.get(0)); // activate
                buttons.get(0).click();
                showTicketsTab.ensureTextNotToBePresentInElement(tds.get(1), "not yet activated");
                showTicketsTab.ensureTextNotToBePresentInElement(tds.get(2), "not yet activated");
                assertTrue(!tds.get(1).getText().trim().equals("not yet activated")
                        && !tds.get(2).getText().trim().equals("not yet activated"));
                assertTrue(showTicketsTab.isDownloadButton(buttons.get(1)));
                showTicketsTab.ensureIsClickableButton(buttons.get(1)); // download
                //buttons.get(1).click();
                showTicketsTab.ensureIsInvisibleButton(buttons.get(2));
            }
            else {
                LocalDateTime startDateAndTime = LocalDateTime.parse(startDateAndTimeStr,dateTimeFormatter);
                LocalDateTime endDateAndTime = LocalDateTime.parse(endDateAndTimeStr, dateTimeFormatter);

                LocalDateTime currentDateTime = LocalDateTime.now();
                // 10 sec lufta
                if(startDateAndTime.isAfter(currentDateTime.minusSeconds(10))
                        || currentDateTime.isAfter(endDateAndTime)) {
                    showTicketsTab.ensureIsClickableButton(buttons.get(2)); // remove
                    assertTrue(showTicketsTab.isRemoveButton(buttons.get(2)));
                    buttons.get(2).click();
                    showTicketsTab.ensureIsInvisibleButton(buttons.get(2));
                    numOfTickets = showTicketsTab.getNumOfTickets();
                    assertEquals(oldNumOfTuckets - 1, numOfTickets);
                    oldNumOfTuckets = numOfTickets;
                }
                else {
                    assertFalse(buttons.get(2).getAttribute("class").contains("btn-danger")); // remove dugme disable-ovano
                    assertTrue(buttons.get(2).getAttribute("class").contains("btn-secondary"));
                }
            }

        }
    }

    @Test
    public void changeAccountType() throws UnsupportedEncodingException, InterruptedException {
        String[] passengerTypes = {"STUDENT", "PENSIONER"};
        String selectedPassengerType;

        passengerPage.ensureIsClickableChangeAccountTypeTab();
        passengerPage.getChangeAccountTypeTab().click();

        changeAccountTypeTab.ensureIsVisibleCurrentPassengerType();
        String currentPassengerType = changeAccountTypeTab.getCurrentPassengerType().getText().trim();

        if(currentPassengerType.equals(passengerTypes[0])) selectedPassengerType = passengerTypes[1];
        else selectedPassengerType = passengerTypes[0];

        changeAccountTypeTab.ensureIsVisiblePassengerType();
        new Select(changeAccountTypeTab.getPassengerType()).selectByVisibleText(selectedPassengerType);

        changeAccountTypeTab.ensureIsInvisibleToastrs();

        // probamo poslati bez fajla
        changeAccountTypeTab.ensureIsClickableSendRequestForChangingButton();
        changeAccountTypeTab.getSendRequestForChangingButton().click();

        changeAccountTypeTab.ensureIsVisibleToastrs();
        assertEquals("You did not select the image!",
                changeAccountTypeTab.getToastrs().get(0).getText().trim());

        // pronalazimo i unosimo putanju do image-a koji cemo poslation kao confirmation
        String imageRelativePath = "/images/confirmation_big_file.jpg";
        String absolutePath = new File(URLDecoder.decode(getClass().getResource(imageRelativePath).getFile(),"UTF-8"))
                .getAbsolutePath();
        System.out.println(absolutePath);
        changeAccountTypeTab.setConfirmation(absolutePath);

        changeAccountTypeTab.ensureIsInvisibleToastrs();

        // probamo poslati prevelik fajl
        changeAccountTypeTab.ensureIsClickableSendRequestForChangingButton();
        changeAccountTypeTab.getSendRequestForChangingButton().click();

        changeAccountTypeTab.ensureIsVisibleToastrs();
        assertTrue(changeAccountTypeTab.getToastrs().get(0).getText().trim().contains("The maximum allowed file size is 1 Mb!"));

        // pronalazimo i unosimo putanju do image-a koji cemo poslation kao confirmation
        imageRelativePath = "/images/confirmation.jpg";
        absolutePath = new File(URLDecoder.decode(getClass().getResource(imageRelativePath).getFile(),"UTF-8"))
                                    .getAbsolutePath();
        System.out.println(absolutePath);
        changeAccountTypeTab.setConfirmation(absolutePath);

        changeAccountTypeTab.ensureIsInvisibleToastrs();

        // saljemo request
        changeAccountTypeTab.ensureIsClickableSendRequestForChangingButton();
        changeAccountTypeTab.getSendRequestForChangingButton().click();

        changeAccountTypeTab.ensureIsVisibleToastrs();
        assertEquals("Request to change the account type was successfully sent!",
                changeAccountTypeTab.getToastrs().get(0).getText().trim());

        // izlogujemo se
        logoutButton.ensureLogoutButtonIsClickable();
        logoutButton.getLogoutButton().click();

        // logujemo se kao userAdmin
        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(userAdminUsername);
        loginPage.setPassword(userAdminPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        userAdminPage.ensureIsClickableAccountRequestsTab();
        userAdminPage.getAccountRequestsTab().click();

        accountRequestsTab.ensureIsClikableShowImageButton();
        accountRequestsTab.getShowImageButton().click();
        modalDialog.ensureIsClickableCloseButton();
        modalDialog.getCloseButton().click();

        modalDialog.ensureIsInvisibleDialog();
        accountRequestsTab.ensureIsInvisibleToastrs();

        // ne prihvatamo request
        accountRequestsTab.ensureIsClikableDeclineButton();
        accountRequestsTab.getDeclineButton().click();

        accountRequestsTab.ensureIsVisibleToastr();
        assertEquals("Successfully reviewed request!", accountRequestsTab.getToastr().getText());

        // izlogujemo se
        logoutButton.ensureLogoutButtonIsClickable();
        logoutButton.getLogoutButton().click();

        // opet se ulogujemo kao passenger
        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(passengerUsername);
        loginPage.setPassword(passengerPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        //Ponovo menjamo tip naloga
        passengerPage.ensureIsClickableChangeAccountTypeTab();
        passengerPage.getChangeAccountTypeTab().click();

        String oldPassengerType = currentPassengerType;
        changeAccountTypeTab.ensureIsVisibleCurrentPassengerType();
        currentPassengerType = changeAccountTypeTab.getCurrentPassengerType().getText().trim();
        assertEquals(oldPassengerType, currentPassengerType);

        if(currentPassengerType.equals(passengerTypes[0])) selectedPassengerType = passengerTypes[1];
        else selectedPassengerType = passengerTypes[0];

        changeAccountTypeTab.ensureIsVisiblePassengerType();
        new Select(changeAccountTypeTab.getPassengerType()).selectByVisibleText(selectedPassengerType);

        // ponovo unosimo putanju do istog image-a koji cemo poslation kao confirmation
        changeAccountTypeTab.setConfirmation(absolutePath);

        changeAccountTypeTab.ensureIsInvisibleToastrs();

        // ponovo saljemo request
        changeAccountTypeTab.ensureIsClickableSendRequestForChangingButton();
        changeAccountTypeTab.getSendRequestForChangingButton().click();

        changeAccountTypeTab.ensureIsVisibleToastrs();
        assertEquals("Request to change the account type was successfully sent!",
                changeAccountTypeTab.getToastrs().get(0).getText().trim());

        // izlogujemo se
        logoutButton.ensureLogoutButtonIsClickable();
        logoutButton.getLogoutButton().click();

        // logujemo se kao userAdmin
        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(userAdminUsername);
        loginPage.setPassword(userAdminPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        userAdminPage.ensureIsClickableAccountRequestsTab();
        userAdminPage.getAccountRequestsTab().click();

        accountRequestsTab.ensureIsClikableShowImageButton();
        accountRequestsTab.getShowImageButton().click();
        modalDialog.ensureIsClickableCloseButton();
        modalDialog.getCloseButton().click();

        modalDialog.ensureIsInvisibleDialog();
        accountRequestsTab.ensureIsInvisibleToastrs();

        // prihvatamo request
        accountRequestsTab.ensureIsClikableAcceptButton();
        accountRequestsTab.getAcceptButton().click();

        accountRequestsTab.ensureIsVisibleToastr();
        assertEquals("Successfully reviewed request!", accountRequestsTab.getToastr().getText());

        // izlogujemo se
        logoutButton.ensureLogoutButtonIsClickable();
        logoutButton.getLogoutButton().click();

        // i za kraj logujemo se ponovo kao passenger
        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(passengerUsername);
        loginPage.setPassword(passengerPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        passengerPage.ensureIsClickableChangeAccountTypeTab();
        passengerPage.getChangeAccountTypeTab().click();

        // Proveravamo da li je currentPassengerType uspesno promenjen
        oldPassengerType = currentPassengerType;
        changeAccountTypeTab.ensureIsVisibleCurrentPassengerType();
        currentPassengerType = changeAccountTypeTab.getCurrentPassengerType().getText().trim();
        assertNotEquals(oldPassengerType, currentPassengerType);
        assertEquals(selectedPassengerType, currentPassengerType);
    }

    @Test
    public void showSchedule() {
        // sacekacemo da se prikazu linije i uklone oblacici sa porukama
        showTicketsTab.ensureIsVisibleTickets();
        showTicketsTab.ensureIsInvisibleToastrs();
        passengerPage.ensureIsClickableShowScheduleTab();
        passengerPage.getShowScheduleTab().click();

        // Izabracemo bus za transport, selektovati prigradske_linije za zonu,
        // 24 za liniju i Saturday za dan za koji hocemo da nam se prikaze schedule
        showScheduleTab.ensureIsVisbileTransport();
        new Select(showScheduleTab.getTransport()).selectByIndex(0);
        showScheduleTab.ensureIsVisbileZone();
        new Select(showScheduleTab.getZone()).selectByVisibleText("prigradske_linije");
        showScheduleTab.ensureIsVisbileLine();
        String selectedLine = "24";
        new Select(showScheduleTab.getLine()).selectByVisibleText(selectedLine);
        showScheduleTab.ensureIsVisbileDay();
        new Select(showScheduleTab.getDay()).selectByVisibleText("Saturday");
        showScheduleTab.ensureIsInvisibleToastrs();
        showScheduleTab.ensureIsClickableShowButton();
        showScheduleTab.getShowButton().click();
        showScheduleTab.ensureIsVisbileToastr();
        assertEquals("Times, for checked line, are successfully loaded!",showScheduleTab.getToastr().getText().trim());
        showScheduleTab.ensureIsVisbileSchedules();
        assertEquals(1, showScheduleTab.getSchedules().size());
        assertEquals("Line: " + selectedLine, showScheduleTab.getSchedules().get(0).getText().trim());

        // Izabracemo metro za transport, ocekujemo da ima jedna zona za ovaj transport, ali nijedna linija za ovu zonu.
        // Ipak pokusavamo prikazati schedule. Ocekujemo error poruku u oblacicu.
        showScheduleTab.ensureIsVisbileTransport();
        new Select(showScheduleTab.getTransport()).selectByIndex(2);
        showScheduleTab.ensureIsInvisibleToastrs();
        showScheduleTab.ensureIsClickableShowButton();
        showScheduleTab.getShowButton().click();
        showScheduleTab.ensureIsVisbileToastr();
        assertEquals("You have not selected any line!",showScheduleTab.getToastr().getText().trim());

        // Zatim cemo opet izabrati bus za transport, selektovati medjumesne_linije za zonu, i 31, 32, 60 za linije,
        // i Workday za dan za koji hocemo da nam se prikaze schedule.
        // Ocekujemo da se prikazu sva tri schedule-a.
        showScheduleTab.ensureIsVisbileTransport();
        new Select(showScheduleTab.getTransport()).selectByIndex(0);
        showScheduleTab.ensureIsVisbileZone();
        new Select(showScheduleTab.getZone()).selectByVisibleText("medjumesne_linije");
        showScheduleTab.ensureIsVisbileLine();
        List<String> selectedLines = Arrays.asList("31", "32", "60");
        Select select = new Select(showScheduleTab.getLine());
        selectedLines.stream().forEach(sl -> select.selectByVisibleText(sl));
        showScheduleTab.ensureIsVisbileDay();
        new Select(showScheduleTab.getDay()).selectByVisibleText("Workday");
        showScheduleTab.ensureIsInvisibleToastrs();
        showScheduleTab.ensureIsClickableShowButton();
        showScheduleTab.getShowButton().click();
        showScheduleTab.ensureIsVisbileToastr();
        assertEquals("Times, for checked line, are successfully loaded!",showScheduleTab.getToastr().getText().trim());
        showScheduleTab.ensureIsVisbileSchedules();
        assertEquals(3, showScheduleTab.getSchedules().size());
        for(int i = 0; i < selectedLines.size(); i++) assertEquals("Line: " + selectedLines.get(i),
                showScheduleTab.getSchedules().get(i).getText().trim());

        // Selektujemo gradske_linije za zonu, i 1, 1Z, 2, 3 za linije,
        // i Sunday za dan za koji hocemo da nam se prikaze schedule.
        // Ocekujemo da se prikazu sva cetiri schedule-a.
        showScheduleTab.ensureIsVisbileZone();
        new Select(showScheduleTab.getZone()).selectByVisibleText("gradske_linije");
        showScheduleTab.ensureIsVisbileLine();
        selectedLines = Arrays.asList("1", "1Z", "2", "3");
        selectedLines.stream().forEach(sl -> select.selectByVisibleText(sl));
        showScheduleTab.ensureIsVisbileDay();
        new Select(showScheduleTab.getDay()).selectByVisibleText("Sunday");
        showScheduleTab.ensureIsInvisibleToastrs();
        showScheduleTab.ensureIsClickableShowButton();
        showScheduleTab.getShowButton().click();
        showScheduleTab.ensureIsVisbileToastr();
        assertEquals("Times, for checked line, are successfully loaded!",showScheduleTab.getToastr().getText().trim());
        showScheduleTab.ensureIsVisbileSchedules();
        assertEquals(4, showScheduleTab.getSchedules().size());
        for(int i = 0; i < selectedLines.size(); i++) assertEquals("Line: " + selectedLines.get(i),
                showScheduleTab.getSchedules().get(i).getText().trim());
    }

    @Test
    public void positionsOfVehicles() {
        int numOfStationsForLine1A = 18;
        int numOfStationsForLine2B = 13;
        int numOfBusesForLine = 3;


        // Prebacujemo se na tab postions of vehicles
        passengerPage.ensureIsClickablePositionsOfVehiclesTab();
        passengerPage.getPositionsOfVehiclesTab().click();

        //skrolujemo malo na dole
        JavascriptExecutor jse = (JavascriptExecutor) browser;
        jse.executeScript("window.scrollBy(0,500)");

        // filtriramo checkbox-ove za linije koje pripadaju busovim zonama
        positionsOfVehiclesTab.ensureIsVisibleTransport();
        new Select(positionsOfVehiclesTab.getTransport()).selectByIndex(1);

        // utvrdjuemo da linija 1B nije aktivna, i da je njen checkBox disable-ovan
        positionsOfVehiclesTab.ensureIsVisibleLine1B();
        positionsOfVehiclesTab.ensureIsDisabledLine1B();

        // iscrtavamo putanju linije 1A, njene stanice i buseve koji se voze na toj liniji
        positionsOfVehiclesTab.ensureIsClickableLine1A();
        positionsOfVehiclesTab.getLine1A().click();

        positionsOfVehiclesTab.ensureIsVisibleBlueStations(numOfStationsForLine1A);
        assertEquals(numOfStationsForLine1A,positionsOfVehiclesTab.getBlueStations().size()/2);

        positionsOfVehiclesTab.ensureIsVisibleBuses(numOfBusesForLine);
        assertEquals(numOfBusesForLine, positionsOfVehiclesTab.getBuses().size()/2);

        // uklanjamo prikaz buseva za liniju 1A
        positionsOfVehiclesTab.ensureIsClickablePositionsOfVehiclesCheckBox();
        positionsOfVehiclesTab.getPositionsOfVehiclesCheckBox().click();

        positionsOfVehiclesTab.ensureIsInvisibleBuses();

        // ponovo iscrtavamo buseve za liniju 1A
        positionsOfVehiclesTab.ensureIsClickablePositionsOfVehiclesCheckBox();
        positionsOfVehiclesTab.getPositionsOfVehiclesCheckBox().click();

        // uklanjamo putanju linije 1A, sve stanice i sve buseve koji se voze na ovoj liniji
        positionsOfVehiclesTab.ensureIsClickableLine1A();
        positionsOfVehiclesTab.getLine1A().click();

        positionsOfVehiclesTab.ensureIsInvisibleBlueStations();
        positionsOfVehiclesTab.ensureIsInvisibleBuses();

        // iscrtavamo putanju linije 2B, njene stanice i buseve koji se voze na toj liniji
        positionsOfVehiclesTab.ensureIsClickableLine2B();
        positionsOfVehiclesTab.getLine2B().click();

        positionsOfVehiclesTab.ensureIsVisibleRedStations(numOfStationsForLine2B);
        assertEquals(numOfStationsForLine2B,positionsOfVehiclesTab.getRedStations().size()/2);

        positionsOfVehiclesTab.ensureIsVisibleBuses(numOfBusesForLine);
        assertEquals(numOfBusesForLine, positionsOfVehiclesTab.getBuses().size()/2);

        // uklanjamo prikaz buseva za liniju 2B
        positionsOfVehiclesTab.ensureIsClickablePositionsOfVehiclesCheckBox();
        positionsOfVehiclesTab.getPositionsOfVehiclesCheckBox().click();

        positionsOfVehiclesTab.ensureIsInvisibleBuses();

        // ponovo iscrtavamo buseve za liniju 2B
        positionsOfVehiclesTab.ensureIsClickablePositionsOfVehiclesCheckBox();
        positionsOfVehiclesTab.getPositionsOfVehiclesCheckBox().click();

        // uklanjamo putanju linije 2B, sve stanice i sve buseve koji se voze na ovoj liniji
        positionsOfVehiclesTab.ensureIsClickableLine2B();
        positionsOfVehiclesTab.getLine2B().click();

        positionsOfVehiclesTab.ensureIsInvisibleRedStations();
        positionsOfVehiclesTab.ensureIsInvisibleBuses();
    }

    @After
    public void tearDown() throws Exception {
        // Shutdown the browser
        //if(browser != null) browser.quit();
    }
}

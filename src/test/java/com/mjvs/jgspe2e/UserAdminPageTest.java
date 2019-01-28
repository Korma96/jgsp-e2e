package com.mjvs.jgspe2e;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.mjvs.jgspe2e.pages.AddAdminTab;
import com.mjvs.jgspe2e.pages.LoginPage;
import com.mjvs.jgspe2e.pages.ShowAdminsTab;
import com.mjvs.jgspe2e.pages.UserAdminPage;

public class UserAdminPageTest {
	
	  	private WebDriver browser;
	    private String baseUrl = "http://localhost:4200";
	    private String userAdminUsername = "darko";
	    private String userAdminPassword = "darko";
	    private LoginPage loginPage;
	    private UserAdminPage userAdminPage;
	    private ShowAdminsTab showAdminsTab;
	    private AddAdminTab addAdminTab;


	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        browser = new ChromeDriver();

        browser.manage().window().maximize();
        browser.navigate().to(baseUrl + "/login");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        showAdminsTab = PageFactory.initElements(browser, ShowAdminsTab.class);
        userAdminPage = PageFactory.initElements(browser, UserAdminPage.class);

        loginPage.ensureIsVisbleUsername();
        loginPage.ensureIsVisblePassword();
        loginPage.setUsername(userAdminUsername);
        loginPage.setPassword(userAdminPassword);
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();
    }
	

	@Test
	@Ignore
	public void add() {
		userAdminPage.ensureIsClickableWorkWithAdminsTab();
		userAdminPage.getWorkWithAdminsTab();
        showAdminsTab.ensureIsVisibleAdmins();
        int numOfAdminsBeforeAction = showAdminsTab.getNumOfAdmins();
        
        userAdminPage.ensureIsClickableAddAdminTab();
        userAdminPage.getAddAdminTab().click();
        
        addAdminTab.ensureIsVisbleUsername();
        addAdminTab.setUsername("");
        addAdminTab.ensureLoginButtonIsClickable();
        addAdminTab.getSaveButton().click();
        addAdminTab.ensureIsVisibleToastrError();
        assertEquals("Username is empty!",
        		addAdminTab.getToastrError().getText().trim());
        
        addAdminTab.setUsername(userAdminUsername);
        addAdminTab.ensureIsVisblePassword();
        addAdminTab.setPassword("");
        addAdminTab.getSaveButton().click();
        addAdminTab.ensureIsVisibleToastrError();
        assertEquals("Password is empty!",
        		addAdminTab.getToastrError().getText().trim());
        
        addAdminTab.setPassword(userAdminPassword);
        addAdminTab.getSaveButton().click();
        addAdminTab.ensureIsVisibleToastrError();
        assertEquals("User type is empty",
        		addAdminTab.getToastrError().getText().trim());
        
        addAdminTab.ensureIsVisbleUserType();
        new Select(addAdminTab.getUserType()).selectByVisibleText("USER_ADMINISTRATOR");
        addAdminTab.getSaveButton().click();
        addAdminTab.ensureIsVisibleToastrError();
        assertEquals("Username already exists, try again!",
        		addAdminTab.getToastrError().getText().trim());

        addAdminTab.setUsername("newAdmin");
        addAdminTab.getSaveButton().click();
        addAdminTab.ensureIsVisibleToastrError();
        assertEquals("You have successfully added admin!",
        		addAdminTab.getToastrError().getText().trim());

        userAdminPage.ensureIsClickableWorkWithAdminsTab();
        userAdminPage.getWorkWithAdminsTab().click();
        int newNumOfAdmins = showAdminsTab.getNumOfAdmins();
        assertEquals(newNumOfAdmins, numOfAdminsBeforeAction + 1);
        
         
	}
	@Test
	@Ignore
	public void workWithAdminsTest() {
		userAdminPage.ensureIsClickableAddAdminTab();
        userAdminPage.getAddAdminTab().click();        
        addAdminTab.ensureIsVisbleUsername();
        addAdminTab.setUsername("workWithAdmins");
        addAdminTab.setPassword("workWithAdmins");	
        addAdminTab.getSaveButton().click();
        addAdminTab.ensureIsVisbleUserType();
        new Select(addAdminTab.getUserType()).selectByVisibleText("USER_ADMINISTRATOR");
        addAdminTab.getSaveButton().click();   // dodajemo admina nad kojim cemo proveriti funkcionalnosti
        
       
        
        userAdminPage.ensureIsClickableWorkWithAdminsTab();
        userAdminPage.getWorkWithAdminsTab().click();
        showAdminsTab.ensureIsVisibleAdmins();
        
        List<WebElement> admins = showAdminsTab.getAdmins();

        //showTicketsTab.ensureIsInVisibleToastr();

        int numOfAdmins = showAdminsTab.getNumOfAdmins();
        
        
        

        WebElement tr;
        for(int i = 0; i < admins.size(); i++) {
        	showAdminsTab.ensureIsInvisibleToastrs();
            tr = admins.get(i);
            List<WebElement> tds = tr.findElements(By.tagName("td"));
            WebElement lastTd = tds.get(tds.size() - 1);
            List<WebElement> buttons = showAdminsTab.getButtons(lastTd); 
            WebElement deleteButton = buttons.get(0);
            WebElement activateButton = buttons.get(0);
            assertTrue(showAdminsTab.isRemoveButton(deleteButton));
            assertTrue(showAdminsTab.isActivateButton(buttons.get(1)));
            showAdminsTab.ensureIsClickableButton(buttons.get(1));
            
            if (tds.get(1).getText().equals("workWithAdmins")) {    //Proveru cemo izvrsiti na dodatom adminu
                showAdminsTab.ensureIsClickableButton(activateButton);
                assertEquals("deactivate", activateButton.getText());
                
                activateButton.click();								// provera deaktivacija
                showAdminsTab.ensureIsVisibleToastr();
                assertEquals("Successfully deactivated admin!", showAdminsTab.getToastr());
                assertEquals(tds.get(3).getText(), "ACTIVATED");
                assertEquals("activate", activateButton.getText());
                
                showAdminsTab.ensureIsClickableButton(activateButton);
                activateButton.click();								// provera aktivacije
                showAdminsTab.ensureIsVisibleToastr();
                assertEquals("Successfully activated admin!", showAdminsTab.getToastr()); 
                assertEquals(tds.get(3).getText(), "DEACTIVATED");
                assertEquals("deactivate", activateButton.getText());
                
                showAdminsTab.ensureIsClickableButton(deleteButton); // provera brisanja
                deleteButton.click();
                showAdminsTab.ensureIsVisibleToastr();
                assertEquals("Successfully deleted admin!", showAdminsTab.getToastr()); 
                assertEquals(numOfAdmins - 1, showAdminsTab.getNumOfAdmins());
                
            }
            
            
	}
	
	}
	
}

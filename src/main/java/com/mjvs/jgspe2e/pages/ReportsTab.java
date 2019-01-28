package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReportsTab {
	
	private WebDriver driver;

	@FindBy(id = "nav-general-report-tab")
	private WebElement accountRequestsTab;

	@FindBy(id = "nav-line-zone-report-tab")
	private WebElement addAdminTab;

	public ReportsTab(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getAccountRequestsTab() {
		return accountRequestsTab;
	}

	public void setAccountRequestsTab(WebElement accountRequestsTab) {
		this.accountRequestsTab = accountRequestsTab;
	}

	public WebElement getAddAdminTab() {
		return addAdminTab;
	}

	public void setAddAdminTab(WebElement addAdminTab) {
		this.addAdminTab = addAdminTab;
	}
	
	
	

}

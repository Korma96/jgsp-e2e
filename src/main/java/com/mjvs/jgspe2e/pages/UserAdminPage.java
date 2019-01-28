package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserAdminPage {
	private WebDriver driver;

	@FindBy(id = "nav-show-requests-tab")
	private WebElement accountRequestsTab;

	@FindBy(id = "nav-add-admin-tab")
	private WebElement addAdminTab;

	@FindBy(id = "nav-work-with-admins-tab")
	private WebElement workWithAdminsTab;

	@FindBy(id = "nav-activate-passengers-tab")
	private WebElement activatePassengersTab;

	@FindBy(id = "nav-reports-tab")
	private WebElement reportsTab;

	public UserAdminPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getAccountRequestsTab() {
		return accountRequestsTab;
	}

	public void setAccountRequestsTab(WebElement accountRequestsTab) {
		this.accountRequestsTab = accountRequestsTab;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getAddAdminTab() {
		return addAdminTab;
	}

	public void setAddAdminTab(WebElement addAdminTab) {
		this.addAdminTab = addAdminTab;
	}

	public WebElement getWorkWithAdminsTab() {
		return workWithAdminsTab;
	}

	public void setWorkWithAdminsTab(WebElement workWithAdminsTab) {
		this.workWithAdminsTab = workWithAdminsTab;
	}

	public WebElement getActivatePassengersTab() {
		return activatePassengersTab;
	}

	public void setActivatePassengersTab(WebElement activatePassengersTab) {
		this.activatePassengersTab = activatePassengersTab;
	}
	
	public WebElement getReportsTab() {
		return reportsTab;
	}

	public void setReportsTab(WebElement reportsTab) {
		this.reportsTab = reportsTab;
	}

	public void ensureIsClickableAccountRequestsTab() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(accountRequestsTab));
	}
	public void ensureIsClickableAddAdminTab() {
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(addAdminTab));
	}
	public void ensureIsClickableWorkWithAdminsTab() {
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(workWithAdminsTab));
	}
	public void ensureIsClickableActivatePassengersTab() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(activatePassengersTab));
	}
	public void ensureIsClickableReportsTab() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(reportsTab));
	}
}

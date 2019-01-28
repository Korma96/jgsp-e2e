package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddAdminTab {
	
    private WebDriver driver;
	
	@FindBy(id = "id_username")
	private WebElement username;

	@FindBy(id = "id_password")
	private WebElement password;
	
	@FindBy(id = "id_user_type")
	private WebElement userType;
	
	@FindBy(xpath = "//button[contains(.,'Save')]")
	private WebElement saveButton;
	
	@FindBy(css = "div.toast-message")
    private WebElement toastrError;
	
	public AddAdminTab(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureLoginButtonIsClickable() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.elementToBeClickable(this.saveButton));

	}

	public void ensureIsVisbleUsername() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOf(username));

	}

	public void ensureIsVisblePassword() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOf(password));

	}
	public void ensureIsVisbleUserType() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOf(userType));

	}
	
	public void ensureIsVisibleToastrError() {
		new WebDriverWait(driver, 10)
		.until(ExpectedConditions.visibilityOf(toastrError));		
	}

	public WebElement getUsername() {
		return username;
	}

	public void setUsername(String value) {
		WebElement username = getUsername();
		username.clear();
		username.sendKeys(value);
	}

	public void setPassword(String value) {
		WebElement pass = getPassword();
		pass.clear();
		pass.sendKeys(value);
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getUserType() {
		return userType;
	}

	public void setUserType (String type) {
		WebElement userType = getUserType();
		userType.clear();
		userType.sendKeys(type);
	}

	public WebElement getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(WebElement saveButton) {
		this.saveButton = saveButton;
	}

	public WebElement getToastrError() {
		return toastrError;
	}

	public void setToastrError(WebElement toastrError) {
		this.toastrError = toastrError;
	}
	

	
	
	
	
	

}

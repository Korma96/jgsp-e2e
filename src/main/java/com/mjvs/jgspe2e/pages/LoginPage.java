package com.mjvs.jgspe2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	private WebDriver driver;

	@FindBy(id = "username")
	private WebElement username;

	@FindBy(id = "inputPassword")
	private WebElement password;

	@FindBy(xpath = "//button[contains(.,'Sign in')]")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureLoginButtonIsClickable() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.elementToBeClickable(this.loginButton));

	}

	public void ensureIsVisbleUsername() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOf(username));

	}

	public void ensureIsVisblePassword() {
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.visibilityOf(password));

	}

	public WebElement getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username.clear();
		this.username.sendKeys(username);
	}

	public WebElement getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password.clear();
		this.password.sendKeys(password);
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(WebElement loginButton) {
		this.loginButton = loginButton;
	}
}

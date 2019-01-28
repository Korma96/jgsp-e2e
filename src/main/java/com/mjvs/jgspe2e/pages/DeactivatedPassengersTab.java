package com.mjvs.jgspe2e.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeactivatedPassengersTab {
	
	 private WebDriver driver;
		
		@FindBy(css = "table tbody tr")
	    private List<WebElement> rows;

	    @FindBy(xpath = "(//table[1]/tbody/tr)[last()]/td")
	    private List<WebElement> tdTagsFromLastTrTag;

	    @FindBy(className = "toast-message")
	    private List<WebElement> toastrs;

	    @FindBy(className = "toast-message")
	    private WebElement toastr;
		
		
		public WebElement getToastr() {
			return toastr;
		}

		public void setToastr(WebElement toastr) {
			this.toastr = toastr;
		}

		public int getNumOfPassengers() {
	        return rows.size() - 1; 
	    }

	    public List<WebElement> getTdTagsFromLastTrTag() {
	        return tdTagsFromLastTrTag;
	    }

	    public void setTdTagsFromLastTrTag(List<WebElement> tdTagsFromLastTrTag) {
	        this.tdTagsFromLastTrTag = tdTagsFromLastTrTag;
	    }

	    public List<WebElement> getAdmins() {
	    	
	        int size = rows.size();
	        if (size > 0) return rows.subList(1, size); 
	        return rows;
	    }

	    public void ensureIsVisibleAdmins() {
	        new WebDriverWait(driver, 10)
	                .until(ExpectedConditions.visibilityOfAllElements(rows));
	    }

	    public void ensureIsClickableButton(WebElement button) {
	        new WebDriverWait(driver, 10)
	                .until(ExpectedConditions.elementToBeClickable(button));
	    }

	    public void ensureIsInvisibleToastrs() {
	        new WebDriverWait(driver, 10)
	                .until(ExpectedConditions.invisibilityOfAllElements(toastrs));
	    }
	    
	    public void ensureIsVisibleToastr() {
	        new WebDriverWait(driver, 10)
	                .until(ExpectedConditions.visibilityOf(toastr));
	    }

	    public void ensureIsPassengersLoaded() {
	        new WebDriverWait(driver, 10)
	        .until(ExpectedConditions.textToBePresentInElement(toastr, "Passengers are successfully loaded!"));
	    }

	    public WebElement getActivateButton(WebElement lastTd) {
	        return lastTd.findElement(By.tagName("button"));
	    }

	    public boolean isActivateButton(WebElement button) {
	        return button.getAttribute("class").contains("btn-primary");
	    }

}

package webCommons;

import base.BaseClass;
import constants.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class WebCommons {

    protected WebDriver driver;

    // Default constructor
    public WebCommons() {
        this.driver = BaseClass.getDriver();
    }

    // Constructor with driver
    public WebCommons(WebDriver driver) {
        this.driver = driver;
    }

    // Launch URL
    public void launchTheURL(String url) {
        driver.get(url);
    }

    // Click TAB key
    public void clickTab(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    // Get page title
    public String getTitleOfTheWebPage() {
        return driver.getTitle();
    }

    // Scroll to Top
    public void scrollToTheTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    // Click element
    public void click(WebElement element) {
        element.click();
    }

    // Scroll to an element
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
    }

    // JS Click
    public void jsClick(WebElement element) {
        scrollToElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // JS SendKeys
    public void jsSendKeys(WebElement element, String text) {
        scrollToElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + text + "';", element);
    }

    // Actions → Single Click
    public void singleClick(WebElement element) {
        scrollToElement(element);
        new Actions(driver).moveToElement(element).click().perform();
    }

    // Actions → Double Click
    public void doubleClick(WebElement element) {
        scrollToElement(element);
        new Actions(driver).moveToElement(element).doubleClick().perform();
    }

    // Actions → Right Click
    public void rightClick(WebElement element) {
        scrollToElement(element);
        new Actions(driver).moveToElement(element).contextClick().perform();
    }

    // Hover
    public void hoverOverElement(WebElement element) {
        scrollToElement(element);
        new Actions(driver).moveToElement(element).perform();
    }

    // Copy content
    public void copyTheContent(WebElement element) {
        scrollToElement(element);
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        actions.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
    }

    // Paste content
    public void pasteTheContent(WebElement element) {
        scrollToElement(element);
        new Actions(driver).moveToElement(element)
                .keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
    }

    // Open new tab
    public void openNewTab() {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform();
    }

    // Open new window
    public void openNewWindow() {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("n").keyUp(Keys.CONTROL).perform();
    }

    // Accept alert
    public void acceptAlert() {
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert to accept: " + e.getMessage());
        }
    }

    // Dismiss alert
    public void dismissAlert() {
        try {
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            System.out.println("No alert to dismiss: " + e.getMessage());
        }
    }

    // Get alert text
    public String getAlertText() {
        try {
            return driver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {
            return null;
        }
    }

    // Switch to frame
    public void switchToFrame(WebElement frameElement) {
        scrollToElement(frameElement);
        driver.switchTo().frame(frameElement);
    }

    // Switch to default content
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // File upload
    public void fileUpload(WebElement uploadElement, String fileName) {
        scrollToElement(uploadElement);
        uploadElement.sendKeys(System.getProperty("user.dir") + "/Files/" + fileName);
    }

    // Validate button label text
    public void labelOfTheButtonbyTextValue(WebElement button, String expectedLabel) {
        scrollToElement(button);
        String actual = button.getText();
        System.out.println(actual.equals(expectedLabel)
                ? "Button label matches"
                : "Button label mismatch");
    }

    // Validate button attribute value
    public void labelOfTheButtonbyAttributeValue(WebElement button, String attr, String expected) {
        scrollToElement(button);
        String actual = button.getAttribute(attr);
        System.out.println(actual.equals(expected)
                ? "Attribute value matches"
                : "Attribute mismatch");
    }

    // Mouse click
    public void mouseClick(WebElement element) {
        new Actions(driver).moveToElement(element).click().perform();
    }

    // Verify hyperlink
    public void verifyHyperlink(WebElement element, String expectedURL) {
        scrollToElement(element);
        String actual = element.getAttribute("href");
        System.out.println(actual.equals(expectedURL)
                ? "Hyperlink matches"
                : "Hyperlink mismatch");
    }

    // Enter text
    public void enterText(WebElement element, String text) {
        scrollToElement(element);
        element.clear();
        element.sendKeys(text);
    }

    // Move to element + send keys
    public void moveToElementAndSendKeys(WebElement element, String text) {
        new Actions(driver).moveToElement(element).click().sendKeys(text).perform();
    }

    // Clear input box using CTRL + A
    public void clearTheInputBox(WebElement element) {
        new Actions(driver).moveToElement(element).click()
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .sendKeys(Keys.BACK_SPACE).perform();
    }

    // Enter text normally
    public void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
    }

    // Checkbox
    public void checkboxHandle(WebElement checkbox, boolean status) {
        if (checkbox.isSelected() != status) {
            checkbox.click();
        }
    }

    // Radio button
    public void radioButtonHandle(WebElement radio, boolean status) {
        if (radio.isSelected() != status) {
            scrollToElement(radio);
            radio.click();
        }
    }

    // Dropdown – Index
    public void dropdownHandlebyIndex(WebElement dropdown, int index) {
        new Select(dropdown).selectByIndex(index);
    }

    // Dropdown – Value
    public void dropdownHandlebyValue(WebElement dropdown, String value) {
        new Select(dropdown).selectByValue(value);
    }

    // Dropdown – Visible text
    public void dropdownHandlebyVisibleText(WebElement dropdown, String text) {
        new Select(dropdown).selectByVisibleText(text);
    }

    // Deselect All
    public void deselectAllDropdown(WebElement dropdown) {
        new Select(dropdown).deselectAll();
    }

    // Text
    public String getText(WebElement element) {
        scrollToElement(element);
        return element.getText();
    }

    // Attribute
    public String getAttributeValue(WebElement element, String attribute) {
        scrollToElement(element);
        return element.getAttribute(attribute);
    }

    // Current URL
    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    // Refresh page
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // Thread sleep
    public void threadWait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Implicit wait
    public void implicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.TIMEOUT));
    }

    // Explicit wait for visibility
    public void explicitWait(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    // Explicit wait using locator
    public void explicitWait(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 0));
    }

    // Background color HEX
    public String getBackgroundColorAsHex(WebElement element) {
        return Color.fromString(element.getCssValue("background-color")).asHex();
    }

    // Get text from multiple elements
    public List<String> getTextFromElements(List<WebElement> elements) {
        return elements.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }
}
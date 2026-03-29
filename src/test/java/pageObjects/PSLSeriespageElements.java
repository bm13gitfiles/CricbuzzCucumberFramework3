package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import webCommons.WebCommons;

public class PSLSeriespageElements extends WebCommons {

    public WebDriver driver;

    public PSLSeriespageElements(WebDriver driver) {
        super(driver); // Pass driver to WebCommons
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//a[@title='Cricket Series']")
    public WebElement seriesLinkHeader;

    @FindBy(xpath = "//a[@title='Pakistan Super League 2026']")
    public WebElement pslSeriesLink;

    @FindBy(xpath = "//h1[text()='Pakistan Super League 2026']")
    public WebElement pslSeriesPageTitle;

    @FindBy(xpath = "(//div[@class='w-2/3 flex flex-col gap-1 tb:gap-2']//a)[1]")
    public WebElement pslSeriesTopNews;

    @FindBy(xpath = "//h1[@class='text-xl wb:text-4xl font-semibold']")
    public WebElement pslSeriesTopNewsTitle;

    @FindBy(xpath = "//div[@class='hidden wb:flex wb:flex-col wb:gap-2']//img")
    public WebElement pslSeriesTopNewsImage;

    @FindBy(xpath = "//div[@class='hidden wb:flex wb:flex-col wb:gap-2']//span")
    public WebElement pslSeriesTopNewsImageDescription;

    @FindBy(xpath = "(//div[@class='flex flex-col gap-6 text-base wb:flex wb:flex-col text-[#333]']//p)[1]")
    public WebElement pslSeriesTopNewsIntro;


















}

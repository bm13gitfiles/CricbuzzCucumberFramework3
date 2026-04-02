package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import webCommons.WebCommons;

import java.util.List;

public class RankingpageElements extends WebCommons {

    public WebDriver driver;

    public RankingpageElements(WebDriver driver) {
        super(driver); // Pass driver to WebCommons
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // HEADER ELEMENTS

    @FindBy(xpath = "(//a[@class='absolute left-1/2 -translate-x-1/2 wb:static wb:translate-x-0 wb:m-3'])[2]")
    public WebElement cricbuzzLogo;

    @FindBy(xpath = "//a[@title='Cricket Rankings' and text()='Rankings']/following-sibling::span")
    public WebElement rankingsLink;

    @FindBy(xpath = "//a[@title='ICC Rankings - Men']")
    public WebElement iccMensRankingLink;

    @FindBy(xpath = "//a[@title='ICC Rankings - Women']")
    public WebElement iccWomensRankingLink;

    //  PAGE TITLES

    @FindBy(xpath = "//h1[@class='text-lg tb:text-2xl text-cbWhite wb:!text-black wb:font-bold font-medium false line-clamp-1']")
    public WebElement mensRankingPageTitle;

    @FindBy(xpath = "//h1[@class='text-lg tb:text-2xl text-cbWhite wb:!text-black wb:font-bold font-medium false line-clamp-1']")
    public WebElement womensRankingPageTitle;

    // TOP PLAYER ELEMENTS


    @FindBy(xpath = "//div[@class='grid grid-cols-4 items-center border-b border-cbBorderGrey py-2 px-3']//div[@class='text-base font-medium']")
    public List<WebElement> topBatterNames;

    @FindBy(xpath="//div[@class='grid grid-cols-4 items-center border-b border-cbBorderGrey py-2 px-3']//div[@class='col-span-1 text-base text-right mr-1']")
    public List<WebElement> topBatterPoints;


    // FORMAT SELECTORS

    @FindBy(xpath = "//div[contains(@class,'w-[65px] text-xs py-2 text-center rounded-full border border-solid border-cbThmClrLgtHvr')]//div[text()='ODI']")
    public WebElement odiSelectorButton;

    @FindBy(xpath = "//div[contains(@class,'w-[65px] text-xs py-2 text-center rounded-full border border-solid border-cbThmClrLgtHvr')]//div[text()='T20']")
    public WebElement t20iSelectorButton;

    @FindBy(xpath = "//div[contains(@class,'w-[65px] text-xs py-2 text-center rounded-full border border-solid border-cbThmClrLgtHvr')]//div[text()='TEST']")
    public WebElement testSelectorButton;

}
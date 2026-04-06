package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import webCommons.WebCommons;

import java.util.List;

public class IPLSeriespageElements extends WebCommons {
    public WebDriver driver;

    public IPLSeriespageElements(WebDriver driver) {
        super(driver); // Pass driver to WebCommons
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // HEADER ELEMENTS

    @FindBy(xpath = "(//a[@class='absolute left-1/2 -translate-x-1/2 wb:static wb:translate-x-0 wb:m-3'])[2]")
    public WebElement cricbuzzLogo;

    @FindBy(xpath = "//a[@class='text-[18px] font-bold hover:underline']")
    public WebElement ipl2026;

    @FindBy(xpath = "//a[@title='Cricket Series']")
    public WebElement seriesLinkHeader;

    @FindBy(xpath = "//a[@title='Indian Premier League 2026']")
    public WebElement iplSeriesLink;

    @FindBy(xpath = "//h1[text()='Indian Premier League 2026']")
    public WebElement iplSeriesPageTitle;

    @FindBy(xpath = "(//div[@class='w-2/3 flex flex-col gap-1 tb:gap-2']//a)[1]")
    public WebElement iplSeriesTopNews;

    @FindBy(xpath = "//h1[@class='text-xl wb:text-4xl font-semibold']")
    public WebElement iplSeriesTopNewsTitle;

    @FindBy(xpath = "//div[@class='hidden wb:flex wb:flex-col wb:gap-2']//img")
    public WebElement iplSeriesTopNewsImage;

    @FindBy(xpath = "(//div[@class='hidden wb:flex wb:flex-col wb:gap-2']//span)[1]")
    public WebElement iplSeriesTopNewsImageDescription;

    @FindBy(xpath = "(//div[@class='flex flex-col gap-6 text-base wb:flex wb:flex-col text-[#333]']//p)[1]")
    public WebElement iplSeriesTopNewsIntro;

    //Points Table Locators

    @FindBy(xpath = "//a[@title='Table - IPL 2026']")
    public WebElement iplSeriesPointsTableLink;

    @FindBy(xpath = "//div[@class='w-full wb:w-[67%] min-h-page relative wb:bg-white']//div[@class='wb:p-3']")
    public WebElement iplSeriesPointsTable;

    @FindBy(xpath = "//div[@class=' flex flex-col justify-center bg-white']//div[@class='mx-2']")
    public List<WebElement> iplSeriesPointsTableTeamNames;

    @FindBy(xpath = "//div[@class=' flex flex-col justify-center bg-white']//div[@class='flex justify-center items-center'][5]")
    public List<WebElement> iplSeriesPointsTableTeamPoints;

    @FindBy(xpath = "//div[@class=' flex flex-col justify-center bg-white']//div[@class='flex justify-center items-center'][6]")
    public List<WebElement> iplSeriesPointsTableTeamNRR;


    // IPL Most Runs and Most wickets Statistics

    @FindBy(xpath = "//a[@title='Stats - IPL 2026']")
    public WebElement iplSeriesStatsLink;

    @FindBy(xpath = "//table[@class='w-full wb:mt-3 text-xs wb:text-sm table-auto ']")
    public WebElement iplMostRunsTable;

    @FindBy(xpath = "//tbody//tr[@class='wb:text-sm border-b border-cbBorderGrey z-2']//a")
    public List<WebElement> iplMostRunsBatsmenNames;

    @FindBy(xpath = "//tbody//tr[@class='wb:text-sm border-b border-cbBorderGrey z-2']//a")
    public List<WebElement> iplMostWicketsBowlerNames;

    @FindBy(xpath = "//tbody//td[@class='bg-white w-20 text-center p-2 font-bold text-sm whitespace-nowrap']")
    public List<WebElement> iplMostRunsBatsmenScores;

    @FindBy(xpath = "//tbody//td[@class='bg-white w-20 text-center p-2 font-bold text-sm whitespace-nowrap']")
    public List<WebElement> iplMostWickets;

    @FindBy(xpath = "//div[contains(@class,'flex justify-between')]//span[text()='Most Wickets']")
    public WebElement iplMostWicketsLink;

    @FindBy(xpath = "(//table[@class='w-full wb:mt-3 text-xs wb:text-sm table-auto ']/thead/tr/th[text()='WKTS'])[2]")
    public WebElement iplMostWicketsTable;

    // Squads and Captains

    @FindBy(xpath = "//a[@title='Squads - IPL 2026']")
    public WebElement iplSeriesSquadsLink;

    @FindBy(xpath = "(//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1])")
    public List<WebElement> iplTeamNames;

    @FindBy(xpath = "//div[@class='pl-3 tb:text-base']//span")
    public List<WebElement> iplTeamCaptain;

    @FindBy(xpath = "(//div[@class='pl-3 tb:text-base']//span[contains(text(),'Captain')][1])")
    public WebElement iplTeamCaptainRole;

    @FindBy(xpath = "//div[@class='pl-3 tb:text-base']//span//parent::span")
    public List<WebElement> iplTeamSquadPlayer;

    @FindBy(xpath = "//div[@class='pl-3 tb:text-base']//p")
    public List<WebElement> iplTeamSquadRole;

}

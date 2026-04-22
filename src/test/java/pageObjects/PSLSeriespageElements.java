package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import webCommons.WebCommons;

import java.util.List;

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


    //Points Table Locators

    @FindBy(xpath = "//a[@title='Table - Pakistan Super League 2026']")
    public WebElement pslSeriesPointsTableLink;

    @FindBy(xpath = "//div[@class='w-full wb:w-[67%] min-h-page relative wb:bg-white']//div[@class='wb:p-3']")
    public WebElement pslSeriesPointsTable;

    @FindBy(xpath = "//div[@class=' flex flex-col justify-center bg-white']//div[@class='mx-2']")
    public List<WebElement> pslSeriesPointsTableTeamNames;

    @FindBy(xpath = "//div[@class=' flex flex-col justify-center bg-white']//div[@class='flex justify-center items-center'][5]")
    public List<WebElement> pslSeriesPointsTableTeamPoints;

    @FindBy(xpath = "//div[@class=' flex flex-col justify-center bg-white']//div[@class='flex justify-center items-center'][6]")
    public List<WebElement> pslSeriesPointsTableTeamNRR;


    // PSL Most Runs and Most wickets Statistics

    @FindBy(xpath = "//a[@title='Stats - Pakistan Super League 2026']")
    public WebElement pslSeriesStatsLink;


    @FindBy(xpath = "//table[@class='w-full wb:mt-3 text-xs wb:text-sm table-auto ']")
    public WebElement pslMostRunsTable;

    @FindBy(xpath = "//tbody//tr[@class='wb:text-sm border-b border-cbBorderGrey z-2']//a")
    public List<WebElement> pslMostRunsBatsmenNames;

    @FindBy(xpath = "//tbody//tr[@class='wb:text-sm border-b border-cbBorderGrey z-2']//a")
    public List<WebElement> pslMostWicketsBowlerNames;


    @FindBy(xpath = "//tbody//td[@class='bg-white w-20 text-center p-2 font-bold text-sm whitespace-nowrap']")
    public List<WebElement> pslMostRunsBatsmenScores;

    @FindBy(xpath = "//tbody//td[@class='bg-white w-20 text-center p-2 font-bold text-sm whitespace-nowrap']")
    public List<WebElement> pslMostWickets;


    @FindBy(xpath = "//div[contains(@class,'flex justify-between')]//span[text()='Most Wickets']")
    public WebElement pslMostWicketsLink;

    @FindBy(xpath = "(//table[@class='w-full wb:mt-3 text-xs wb:text-sm table-auto ']/thead/tr/th[text()='WKTS'])[2]")
    public WebElement pslMostWicketsTable;


    // Squads and Captains

    @FindBy(xpath = "//a[@title='Squads - Pakistan Super League 2026']")
    public WebElement pslSeriesSquadsLink;

    @FindBy(xpath = "//h2[text()='SQUADS FOR PAKISTAN SUPER LEAGUE 2026']")
    public WebElement pslSquadsPage;

    @FindBy(xpath = "//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]")
    public List<WebElement> pslTeamNames;

    @FindBy(xpath = "//div[@class='pl-3 tb:text-base']//span[contains(text(),'Captain')]/..")
    public WebElement pslTeamCaptain;

    @FindBy(xpath = "//div[@class='pl-3 tb:text-base']//span//parent::span")
    public List<WebElement> pslTeamSquadPlayer;

    @FindBy(xpath = "//div[@class='pl-3 tb:text-base']//p")
    public List<WebElement> pslTeamSquadRole;



}

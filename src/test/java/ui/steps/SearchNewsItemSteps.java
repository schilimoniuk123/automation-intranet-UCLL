package ui.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.LoginPage;
import ui.pages.Page;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchNewsItemSteps {

    private final static String XPATH_ALL = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[1]/a";
    private final static String XPATH_SEARCH = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a[1]";
    private final static String XPATH_SEARCHBAR = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div/div/div[1]/div/form/div/div/div/div[1]/div/div/input";
    private final static String XPATH_SEARCHBTN = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div/div/div[1]/div/form/div/div/div/div[3]/button";

    private String searchtext;

    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;

    @Before
    public void setUp() {
        Page.initDriver();
    }

    @Given("the news item {string} is on the news feed")
    public void the_news_item_is_on_the_news_feed(String string) {
        try{
            clickItem(XPATH_ALL);
            link = null;
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

            assertEquals(string.toLowerCase(), link.getText().toLowerCase());
        }catch(IndexOutOfBoundsException ioobe)
        {
            ioobe.getMessage();
            throw new NullPointerException("News item bestaat niet of is verwijderd");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @When("Yannick searches the news item {string}")
    public void yannick_searches_the_news_item(String string) throws Exception {
        try{
            clickItem(XPATH_SEARCH);
            link = Page.getDriver().findElements(By.xpath(XPATH_SEARCHBAR)).get(0);
            link.sendKeys(string);
            clickItem(XPATH_SEARCHBTN);
        }catch (IndexOutOfBoundsException ioobe)
        {
            ioobe.getMessage();
            throw new Exception("News item bestaat niet of is verwijderd");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @When("Yannick searches {string} to search for the news item {string}")
    public void yannick_searches_to_search_for_the_news_item(String string, String string2) throws Exception {
        try{
            clickItem(XPATH_SEARCH);
            link = Page.getDriver().findElements(By.xpath(XPATH_SEARCHBAR)).get(0);
            link.sendKeys(string);
            clickItem(XPATH_SEARCHBTN);
        }catch (IndexOutOfBoundsException ioobe)
        {
            ioobe.getMessage();
            throw new Exception("News item bestaat niet of is verwijderd");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Then("the news item {string} should be found")
    public void the_news_item_should_be_found(String string) {
        link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2//a[text()='" + string +"']")).get(0);
        WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link));

        assertTrue(link.getText().toLowerCase().contains(string.toLowerCase()));

    }
    @After
    public void clean() {
        Page.quitDriver();
    }

    public void clickItem(String xpath) throws InterruptedException {
        WebElement element = Page.getDriver().findElements(By.xpath(xpath)).get(0);
        WebDriverWait wait1 = new WebDriverWait(Page.getDriver(), Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", element);

        //wacht totdat de inhoud van de verwijderde items ingeladen zijn.
        TimeUnit.SECONDS.sleep(2);
    }

}

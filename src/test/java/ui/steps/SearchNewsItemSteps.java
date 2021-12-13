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
import ui.pages.Page;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SearchNewsItemSteps {

    private final static String XPATH_ALL = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[1]/a";
    private final static String XPATH_DELETED = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[4]/a";
    private final static String XPATH_SEARCH = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a[1]";

    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;

    @Before
    public void setUp() {
        Page.initDriver();
    }

    @Given("the news item {string} is on the news feed")
    public void the_news_item_is_on_the_news_feed(String string) {

        

    }

    @When("Yannick searches the news item {string}")
    public void yannick_searches_the_news_item(String string) {

        link = Page.getDriver().findElements(By.xpath("/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[1]/a[1]")).get(0);

        WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", link);

    }

    @When("Yannick searches {string} to search the news item {string}")
    public void yannick_searches_to_search_the_news_item(String string, String string2) {



    }

    @Then("the news item {string} should be found")
    public void the_news_item_should_be_found(String string) {
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

/*    public void unFavorite(String string) throws InterruptedException {
        clickItem(XPATH_DELETED);
        clickItem("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as favorite') or contains(@title, 'Mark as unfavorite')]");
        System.out.println("safely unfavorited element: " + string);
    }*/
}

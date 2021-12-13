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

import static org.junit.Assert.assertEquals;

public class RecoverDeletedNewsItemSteps {

    private final static String XPATH_ALL = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[1]/a";
    private final static String XPATH_DELETED = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[4]/a";

    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;

    @Before
    public void setUp() {
        Page.initDriver();
    }

    @Given("the news item {string} is a deleted news item")
    public void the_news_item_is_a_deleted_news_item(String newsItem) throws Exception {
        try{
            clickItem(XPATH_DELETED);
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

            assertEquals(newsItem.toLowerCase(), link.getText().toLowerCase());
        }catch(IndexOutOfBoundsException ioobe)
        {
            ioobe.getMessage();
            throw new Exception("News item bestaat niet of is niet verwijderd");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @When("Yannick undeletes the news item {string}")
    public void yannick_undeletes_the_news_item(String newsItem) {
        link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as undeleted')]")).get(0);
        WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link));

        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", link);
    }

    @Then("the news item {string} should be on the news feed again")
    public void the_news_item_should_be_on_the_news_feed_again(String newsItem) {
        try{
            clickItem(XPATH_ALL);
            link = null;
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

            assertEquals(newsItem.toLowerCase(), link.getText().toLowerCase());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Then("the news item {string} should be removed from the list of deleted news items")
    public void the_news_item_should_be_removed_from_the_list_of_deleted_news_items(String newsItem) {
        try {
            clickItem(XPATH_DELETED);
            link = null;
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
        }catch(IndexOutOfBoundsException ioob)
        {
            assertEquals(null, link);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Then("the news item {string} should be marked as undeleted")
    public void the_news_item_should_be_marked_as_undeleted(String newsItem) throws Exception {
        try {
            clickItem(XPATH_ALL);
            link = null;
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as deleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

            assertEquals("Mark as deleted", link.getText());
        } catch (IndexOutOfBoundsException ioob)
        {
            ioob.getMessage();
            throw new Exception("News item bestaat niet of is niet verwijderd");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @After
    public void clean() {
        Page.quitDriver();
    }



    //====================================================================================================



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

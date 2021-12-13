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

    @Given("The news item {string} is a deleted news item")
    public void the_news_item_is_a_deleted_news_item(String string) {

        try{

            System.out.println("expected element: " + string);
            System.out.println("found element: " + Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']")).get(0).getText());

            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as deleted')]")).get(0);

            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

        } catch (NullPointerException e)
        {

            throw new NullPointerException("News item bestaat niet of is verwijderd");

        } catch (Exception ex){

            ex.getMessage();

        }
    }

    @When("Yannick undeletes the news item {string}")
    public void yannick_undeletes_the_news_item(String string) {



    }

    @Then("the news item {string} should be on the news feed again")
    public void the_news_item_should_be_on_the_news_feed_again(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the news item {string} should be removed from the list of deleted news items")
    public void the_news_item_should_be_removed_from_the_list_of_deleted_news_items(String string) {



    }

    @Then("the news item {string} should be marked as undeleted")
    public void the_news_item_should_be_marked_as_undeleted(String string) {



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

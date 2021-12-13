package ui.steps;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.LoginPage;
import ui.pages.Page;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DeleteNewsItemSteps {

    private final static String XPATH_ALL = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[1]/a";
    private final static String XPATH_DELETED = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[4]/a";

    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;


    @Before
    public void setUp() {
        Page.initDriver();
    }


    @Given("the news item {string} is not a deleted news item")
    public void the_news_item_is_not_a_deleted_news_item(String newsItem) throws Exception {
        try{
            System.out.println("expected element: " + newsItem);
            System.out.println("found element: " + Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']")).get(0).getText());

            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as deleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

        } catch (NullPointerException e)
        {
            throw new Exception("News item bestaat niet of is verwijderd");
        } catch (Exception ex){
            ex.getMessage();
        }

    }


    @When("Yannick deletes the news item {string}")
    public void yannick_deletes_the_news_item(String newsItem) {
        link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);

        WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", link);
    }


    @Then("the news item {string} should be marked as deleted")
    public void the_news_item_should_be_marked_as_deleted(String newsItem) {
        try{
            clickItem(XPATH_DELETED);
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
            assertEquals(link.getText(), "Mark as undeleted");
        }catch(Exception e){
            e.getMessage();
        }

    }
    @Then("the news item {string} should be removed from the overview of all news items")
    public void the_news_item_should_be_removed_from_the_overview_of_all_news_items(String newsItem) throws InterruptedException {
        try{
            clickItem(XPATH_ALL);
            link = null;
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
        }catch (IndexOutOfBoundsException oob)
        {
            assertEquals(null, link);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Then("the news item {string} is added to the list of deleted news items")
    public void the_news_item_is_added_to_the_list_of_deleted_news_items(String newsItem) {
        try {
            clickItem(XPATH_DELETED);
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
            assertEquals(Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + newsItem +"']")).get(0).getText().toLowerCase(), newsItem.toLowerCase());
            assertEquals(link.getText(), "Mark as undeleted");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @After
    public void clean() {
        Page.quitDriver();
    }


    //alleen voor de links zoals "Alle", "Belangrijk", "Favoriet", etc...
    public void clickItem(String xpath) throws InterruptedException {
        WebElement element = Page.getDriver().findElements(By.xpath(xpath)).get(0);
        WebDriverWait wait1 = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", element);
        //wacht totdat de inhoud van de verwijderde items ingeladen zijn.
        TimeUnit.SECONDS.sleep(2);
    }
}

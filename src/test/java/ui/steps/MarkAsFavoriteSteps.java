package ui.steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.LoginPage;
import ui.pages.Page;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MarkAsFavoriteSteps {

    private final static String XPATH_ALL = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[1]/a";
    private final static String XPATH_FAVORITE = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[3]/a";

    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;


    @Before
    public void setUp() {
        Page.initDriver();
    }

    @Given("Yannick is logged in")
    public void yannick_is_logged_in(){
        LoginPage loginPage = PageFactory.initElements(Page.getDriver(), LoginPage.class);
        loginPage.login();
    }



    @Given("the news item {string} is not a favorite news item")
    public void the_news_item_is_not_a_favorite_news_item(String string) {
        try{

            clickItem(XPATH_ALL);
            System.out.println("expected element: " + string);
            System.out.println("found element: " + Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']")).get(0).getText());
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as favorite') or contains(@title, 'Mark as unfavorite')]")).get(0);

            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

        } catch (NullPointerException e)
        {
            throw new NullPointerException("News item bestaat niet of is verwijderd");
        } catch (Exception ex){
            ex.getMessage();
        }
    }


    @When("Yannick marks the news item {string} as favorite")
    public void yannick_marks_the_news_item_as_favorite(String string) {
        link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as favorite') or contains(@title, 'Mark as unfavorite')]")).get(0);

        WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", link);

    }


    @Then("the news item {string} should be marked as a favorite news item")
    public void the_news_item_should_be_marked_as_a_favorite_news_item(String string) throws InterruptedException {
        try{
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as favorite') or contains(@title, 'Mark as unfavorite')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
            assertEquals(link.getText(), "Mark as unfavorite");
            unFavorite(string);

        }
        catch(Exception e){
            e.getMessage();
        }
    }

    @Then("the news item {string} should be added to the list of favorite news items")
    public void the_news_item_should_be_added_to_the_list_of_favorite_news_items(String string) {
        try{
            clickItem(XPATH_FAVORITE);
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as favorite') or contains(@title, 'Mark as unfavorite')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
            assertEquals(link.getText(), "Mark as unfavorite");
            assertNotNull(link);
            unFavorite(string);
        }
        catch(Exception e){
            e.getMessage();
        }
    }


    @After
    public void clean() {
        Page.quitDriver();
    }




    //====================================================================================================


    public void clickItem(String xpath) throws InterruptedException {
        WebElement element = Page.getDriver().findElements(By.xpath(xpath)).get(0);
        WebDriverWait wait1 = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", element);

        //wacht totdat de inhoud van de verwijderde items ingeladen zijn.
        TimeUnit.SECONDS.sleep(2);
    }

    public void unFavorite(String string) throws InterruptedException {
        clickItem(XPATH_FAVORITE);
        clickItem("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as favorite') or contains(@title, 'Mark as unfavorite')]");
        System.out.println("safely unfavorited element: " + string);
    }

}
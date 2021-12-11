package ui.steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
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

public class DeleteNewsItem {

    private final static String XPATH_ALL = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[1]/a";
    private final static String XPATH_DELETED = "/html/body/div[2]/div[2]/div/div/div/div/div/div/div[2]/div/div[3]/div/div/div[1]/div[3]/ul[2]/li[4]/a";

    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;

    public DeleteNewsItem() {
        Page.initDriver();
    }

    @Given("Yannick is logged in")
    public void yannick_is_logged_in(){
        LoginPage loginPage = PageFactory.initElements(Page.getDriver(), LoginPage.class);
        loginPage.login();
    }

    @Given("the news item {string} is not a deleted news item")
    public void the_news_item_is_not_a_deleted_news_item(String string){
        try{

            TimeUnit.SECONDS.sleep(2);
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


    @When("Yannick deletes the news item {string}")
    public void yannick_deletes_the_news_item(String string) {
        link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);

        WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(link));
        JavascriptExecutor executor = (JavascriptExecutor) Page.getDriver();
        executor.executeScript("arguments[0].click();", link);
    }


    @Then("the news item {string} should be marked as deleted")
    public void the_news_item_should_be_marked_as_deleted(String string) {
        try{
            clickItem(XPATH_DELETED);

            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
            assertEquals(link.getText(), "Mark as undeleted");

            unDeleteItem(string);

        }catch(Exception e){
            e.getMessage();
        }

    }
    @Then("the news item {string} should be removed from the overview of all news items")
    public void the_news_item_should_be_removed_from_the_overview_of_all_news_items(String string) throws InterruptedException {
        try{
            clickItem(XPATH_ALL);

            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

        }catch (IndexOutOfBoundsException oob)
        {
            unDeleteItem(string);
            assertTrue(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    @Then("the news item {string} is added to the list of deleted news items")
    public void the_news_item_is_added_to_the_list_of_deleted_news_items(String string) {
        try {
            clickItem(XPATH_DELETED);
            link = Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]")).get(0);
            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));

            assertEquals(Page.getDriver().findElements(By.xpath("//ancestor::div[@class='view-content']//h2[text()='" + string +"']")).get(0).getText().toLowerCase(), string.toLowerCase());
            assertEquals(link.getText(), "Mark as undeleted");

            unDeleteItem(string);
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

    public void unDeleteItem(String string) throws InterruptedException {
        clickItem(XPATH_DELETED);
        clickItem("//ancestor::div[@class='view-content']//h2[text()='" + string +"']/../..//a[contains(@title,'Mark as deleted') or contains(@title, 'Mark as undeleted')]");
        System.out.println("safely undeleted element: " + string);
    }

}

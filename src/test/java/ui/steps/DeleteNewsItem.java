package ui.steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.Message;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.LoginPage;
import ui.pages.Page;

import java.time.Duration;
import java.util.List;

public class DeleteNewsItem {
    @FindBy(linkText = "Mijn nieuws")
    private WebElement link;

    public DeleteNewsItem() {
        Page.initDriver();
    }


    @Given("the news item {string} is not a deleted news item")
    public void the_news_item_is_not_a_deleted_news_item(String string){
        try{

            link = Page.getDriver().findElements(By.xpath("//h2[text()='" + string +"']//ancestor::div[@class='view-content']//a[contains(@title,'Mark as ')]")).get(0);

            WebDriverWait wait = new WebDriverWait(Page.getDriver(),Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(link));
            

            System.out.println(link.getText());
            System.out.println(link.getLocation().toString());
            System.out.println(link.getTagName());
            System.out.println(link.getSize().toString());


        } catch (NullPointerException e)
        {
            throw new NullPointerException("News item bestaat niet of is verwijderd");
        } catch (Exception ex){
            ex.getMessage();
        }

    }


    @When("Yannick deletes the news item {string}")
    public void yannick_deletes_the_news_item(String string) {

    }


    @Then("the news item {string} should be marked as deleted")
    public void the_news_item_should_be_marked_as_deleted(String string) {
    }


    @After
    public void clean() {
        Page.quitDriver();
    }

}

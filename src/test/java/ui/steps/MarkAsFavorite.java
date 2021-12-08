package ui.steps;

import com.google.j2objc.annotations.AutoreleasePool;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import ui.pages.LoginPage;
import ui.pages.Page;

public class MarkAsFavorite {

    public MarkAsFavorite(){
        Page.initDriver();
    }

    @Before
    public void setUp() {
    }

    @Given("Yannick is logged in")
    public void yannick_is_logged_in(){
        LoginPage loginPage = PageFactory.initElements(Page.getDriver(), LoginPage.class);
        loginPage.login();
    }

    @Given("the news item {string} is not a favorite news item")
    public void the_news_item_is_not_a_favorite_news_item(String string) {

    }


    @When("Yannick marks the news item {string} as favorite")
    public void yannick_marks_the_news_item_as_favorite(String string) {
    }


    @Then("the news item {string} should be marked as a favorite news item")
    public void the_news_item_should_be_marked_as_a_favorite_news_item(String string) {
    }


    @After
    public void clean() {
        Page.quitDriver();
    }

}

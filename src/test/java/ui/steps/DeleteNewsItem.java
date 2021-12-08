package ui.steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.support.PageFactory;
import ui.pages.LoginPage;
import ui.pages.Page;

public class DeleteNewsItem {

    public DeleteNewsItem() {

    }


    @Given("the news item {string} is not a deleted news item")
    public void the_news_item_is_not_a_deleted_news_item(String string) {
    }
    @When("Yannick deletes the news item {string}")
    public void yannick_deletes_the_news_item(String string) {
    }
    @Then("the news item {string} should be marked as deleted")
    public void the_news_item_should_be_marked_as_deleted(String string) {
    }


}

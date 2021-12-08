package ui.steps;

import io.cucumber.java.en.Given;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import ui.pages.LoginPage;
import ui.pages.Page;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"src/test/resources/features"},
        tags=("@UI"),
        glue={"ui/steps"},
        plugin={"json:target/ui-test.json"}
)

public class CucumberTests {

    @Before
    public void setUp() {
        Page.getDriver();
    }

    @After
    public void clean() {
        Page.quitDriver();
    }

    @Given("Yannick is logged in")
    public void yannickIsLoggedIn() {
        LoginPage loginPage = PageFactory.initElements(Page.getDriver(), LoginPage.class);
        loginPage.login();
    }
}
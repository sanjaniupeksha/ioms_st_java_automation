package runner;


import factory.CucumberHooks;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;

@CucumberOptions(
        features = "src/test/resources/features",
        glue= "steps",
        plugin = {"pretty","html:target/cucumber-html-report","json:target/cucumber.json"},
        monochrome = true,
        tags ="",
        publish = true
)

public class TestRunner extends AbstractTestNGCucumberTests {

 @BeforeSuite(alwaysRun = true)
 public void setUpClass() throws Exception {

  CucumberHooks hooks = new CucumberHooks();
  hooks.setup();
 }

 }
package Test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src\\test\\resources\\redbus.feature",
        glue = "Test",
        tags = "",
        plugin = {"pretty", "html:target/Reports/redbus.html"},
        dryRun = true
)

public class TestRunner {
}

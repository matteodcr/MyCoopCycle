package myccopcycle.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import myccopcycle.IntegrationTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@IntegrationTest
@WebAppConfiguration
public class CucumberTestContextConfiguration {}

package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.ArrayList;
import java.util.List;

public class calclick {

    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\sreenu\\IdeaProjects\\Redbus\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--guest");
        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.redbus.in/");

        driver.findElement(By.xpath("//span[@class='dateText']")).click();

        String givenmonth = "Oct";
        String givenyear = "2025";

        String givendate = givenmonth + " " + givenyear;

        String date = driver.findElement(By.xpath("//div[contains(@style, 'font-size')]")).getText();
        String holidaycount = driver.findElement(By.xpath("//div[@class='holiday_count']")).getText();

        String Redbusdate = date.replace(holidaycount, "").trim();
        int count = 1;

        while (!(givendate.equals(Redbusdate))) {
            System.out.println(date);

            if (count == 1) {
                driver.findElement(By.xpath("//*[@id='Layer_1']")).click();
            } else {
                driver.findElement(By.xpath("(//*[@id='Layer_1'])[2]")).click();
            }
            date = driver.findElement(By.xpath("//div[contains(@style, 'font-size')]")).getText();
            if (date.contains("Jun")) {
                Redbusdate = date;
            } else {
                holidaycount = driver.findElement(By.xpath("//div[@class='holiday_count']")).getText();
                Redbusdate = date.replace(holidaycount, "").trim();
            }

            count++;
        }
        System.out.println(date);
        List<WebElement> holidays = driver.findElements(By.xpath("//div[contains(@class, 'DayTilesWrapper')]//span[contains(@class, 'bwoYtA')]"));
        List<String> l = new ArrayList<>();
        for (WebElement ele : holidays) {
            l.add(ele.getText());
        }
        System.out.println(l);
        driver.quit();
    }
}

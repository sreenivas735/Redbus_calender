package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.InvalidObjectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\sreenu\\IdeaProjects\\Redbus\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--guest");
        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.redbus.in/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String from = "Bangalore";
        String to = "Tirupathi";

        driver.findElement(By.id("src")).sendKeys(from);
        driver.findElement(By.xpath("(//text[@class='placeHolderMainText'])[1]")).click();
        driver.findElement(By.id("dest")).sendKeys(to);
        driver.findElement(By.xpath("(//text[@class='placeHolderMainText'])[2]")).click();

        driver.findElement(By.xpath("//span[@class='dateText']")).click();
        String date = driver.findElement(By.xpath("//div[contains(@style, 'font-size')]")).getText();
        String holiday = driver.findElement(By.xpath("//div[@class='holiday_count']")).getText();
        String originaldate = date.replace(holiday, "").trim();

        String givenmonth = "Apr";
        String givenyear = "2024";
        int givenday = 7;

        String calmonth = originaldate.split(" ")[0];
        String calyear = originaldate.split(" ")[1];

        int givenmonthnumber = monthTonumberconvert(givenmonth);
        int calmonthnumber = monthTonumberconvert(calmonth);

        if (givenyear.equals(calyear) & givenmonth.equals(calmonth)) {
            driver.findElement(By.xpath("//div//span[contains(text(), '" + givenday + "')]")).click();
        } else if (Integer.parseInt(givenyear) > Integer.parseInt(calyear)) {
            int count = 1;
            while (!(givenmonth.equals(calmonth)) & givenyear.equals(calyear)) {
                if (count == 1) {
                    driver.findElement(By.xpath("//*[@id='Layer_1']")).click();
                } else {
                    driver.findElement(By.xpath("(//*[@id='Layer_1'])[2]")).click();
                }
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@style, 'font-size')]")));
                date = driver.findElement(By.xpath("//div[contains(@style, 'font-size')]")).getText();
                holiday = driver.findElement(By.xpath("//div[@class='holiday_count']")).getText();
                originaldate = date.replace(holiday, "").trim();
                calmonth = originaldate.split(" ")[0];
                calyear = originaldate.split(" ")[1];
                count++;
            }
            driver.findElement(By.xpath("//div//span[contains(text(), '" + givenday + "')]")).click();
        } else if (Integer.parseInt(givenyear) < Integer.parseInt(calyear)) {
            throw new InvalidObjectException("not possible to select in the given year because it is expired");
        } else if (Integer.parseInt(givenyear) == Integer.parseInt(calyear)) {

            if (givenmonthnumber > calmonthnumber) {
                int count = 1;
                while (!(givenmonth.equals(calmonth))) {

                    if (count == 1) {
                        driver.findElement(By.xpath("//*[@id='Layer_1']")).click();
                    } else {
                        driver.findElement(By.xpath("(//*[@id='Layer_1'])[2]")).click();
                    }
//                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@style, 'font-size')]")));
                    date = driver.findElement(By.xpath("//div[contains(@style, 'font-size')]")).getText();
                    holiday = driver.findElement(By.xpath("//div[@class='holiday_count']")).getText();
                    originaldate = date.replace(holiday, "").trim();
                    calmonth = originaldate.split(" ")[0];
//                    calyear = originaldate.split(" ")[1];
                    count++;
                }
                driver.findElement(By.xpath("//div//span[contains(text(), '" + givenday + "')]")).click();

            } else {
                throw new InvalidObjectException("not possible to select in the given month because it is expired");
            }

            driver.findElement(By.xpath("//button[@id='search_button']")).click();

            Thread.sleep(3000);

            for (int i = 0; i < 50; i++) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000);
            }
            List<WebElement> ele = driver.findElements(By.xpath("//div[contains(@class, 'travels')]"));

            Map<String, Integer> bus = new HashMap<>();

            int n = ele.size();

            for (WebElement e : ele) {
                String s = e.getText();
                if (bus.containsKey(s)) {
                    bus.put(s, bus.get(s) + 1);
                } else {
                    bus.put(s, 1);
                }
            }

            for (Map.Entry<String, Integer> busses : bus.entrySet()) {
                System.out.println(busses.getKey() + " -- " + busses.getValue());
            }

            System.out.println("list of busses from " + from + " -- " + to + "  :" + n);

        }
    }

    public static int monthTonumberconvert(String monthName) throws ParseException {
        // Replace with your desired month name

        // Create a SimpleDateFormat object to parse month names
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        // Parse the month name into a Date object
        Date date = dateFormat.parse(monthName);

        // Convert the Date object into a month number (1-12)
        int monthNumber = date.getMonth() + 1; // Adding 1 because month numbers are 0-based
        return monthNumber;
    }
}
package com.flipkart.step;

import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MobilePurchase {
	
	@DataProvider(name = "mobile")
	public Object[][] mobileName() {
		
		return new Object[][] {{"SAMSUNG"}};

	}
	
	@DataProvider(name = "tv")
	public Object[][] tvName() {
		
		return new Object[][] {{"SAMSUNG tv"}};

	}
	
	static WebDriver driver;
	
	@Parameters({"browser"})
	@BeforeClass(groups = "default")
	public static void launch(@Optional("chrome")String browser) {
		
		System.out.println("before class");
		
		if(browser.equals("chrome")){
		WebDriverManager.chromedriver().setup();
		
		driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}else if(browser.equals("edge")){
			
			WebDriverManager.edgedriver().setup();
			
			driver = new EdgeDriver();
			driver.get("https://www.flipkart.com/");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		}

	}
	
	@AfterClass(groups = "default")
	public static void quit() {
		
		System.out.println("after class");
		driver.quit();

	}
	
	long startTime;
	
	@BeforeMethod(groups = "default")
	public void beforeMethod() {
		
		System.out.println("before method");
		startTime = System.currentTimeMillis();

	}
	
	@AfterMethod(groups = "default")
	public void afterMethod() {
		
		System.out.println("after method");
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken :"+(endTime - startTime));

	}
	
	@Test(priority = 1,groups = "sanity")
	public void login() {
		
		System.out.println("login");
		 try{
				
				WebElement closeIcon = driver.findElement(By.xpath("//button[text()='✕']"));
				closeIcon.isDisplayed();
				closeIcon.click();
				
			}catch (Exception e) {
				
				System.out.println("Login not required");
				
			}

	}
	
	@Test(priority = 2, dataProvider = "mobile",groups = "sanity")
	public void search(String name) {
		
		System.out.println("search");
		driver.findElement(By.name("q")).sendKeys(name,Keys.ENTER);
		WebElement mobileName = driver.findElement(By.xpath("(//div[contains(text(),'"+name+"')])[2]"));
	    String fn = mobileName.getText();
		System.out.println("Mobile Name is :"+fn);
		
		mobileName.click();

	}
	
	@Test(priority = 3)
	public void windows() {
		
		System.out.println("windows");
         String parent = driver.getWindowHandle();
		
		Set<String> child = driver.getWindowHandles();
		for (String x : child) {
			
			if(!x.equals(parent)){
				driver.switchTo().window(x);
				System.out.println("window switched");
			}
			
		}

	}
	
	@Test(priority = 4,retryAnalyzer = Rerun.class)
	public void addToCart() {
		
		System.out.println("addToCart");
		
		System.out.println("drpdown & screenshot");
		WebElement buy = driver.findElement(By.xpath("//button[text()='BUY NOW']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true)", buy);
		String text = buy.getText();
		
		Assert.assertTrue(buy.isDisplayed());
		
		Assert.assertEquals("BUY", text);

	}

}
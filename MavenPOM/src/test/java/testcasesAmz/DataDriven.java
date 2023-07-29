package testcasesAmz;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.log4j.BasicConfigurator;

public class DataDriven {
	WebDriver driver;
	private static final org.apache.log4j.Logger logger = LogManager.getLogger(DataDriven.class);
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public void BrowserSetup() {
		System.setProperty("WebDriver.Chrome.Driver","C:\\Users\\godwi\\eclipse-workspace\\MavenPOM\\ChDriver\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	
	@Test(dataProvider="lginData")
	public void login(String user, String psw, String data) {
		BasicConfigurator.configure();
		logger.info("Checking Logging Functionalities");
		System.out.println(user +" : "+ psw+": "+data);
		driver.get("https://www.saucedemo.com/");
		System.out.println("First Page Title : "+driver.getTitle());
		WebElement usernm=driver.findElement(By.xpath("//input[@id='user-name']"));
		usernm.clear();
		usernm.sendKeys(user);
		WebElement pswfld= driver.findElement(By.xpath("//input[@id='password']"));
		pswfld.clear();
		pswfld.sendKeys(psw);
		driver.findElement(By.xpath("//input[@id='login-button']")).click();	
		
		String Exp_Title = "Swag Labs";
		String Act_Title = driver.getTitle();
		System.out.println("Current Title : "+Act_Title);
		
		
		if(data.equals("Valid")) {
			System.out.println("Second Page Title : "+driver.getTitle());
			if(Act_Title.equals(Exp_Title)) {
		WebElement Button = driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
		Button.click();
		driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
				Assert.assertTrue(true);
			}
			else {
				Assert.assertTrue(false);
			}			
		}
//		else if(data.equals("Invalid")) {
//			System.out.println("==================");
//			System.out.println(driver.getTitle());
//			System.out.println("==================");
//			if(Act_Title.equals(Exp_Title)) {
//				WebElement Button = driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
//				Button.click();
//				driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
//				Assert.assertTrue(false);
//			}
//			else {
//				Assert.assertTrue(true);
//			}
//		}
	}
	
	@DataProvider(name="lginData")
	public String [][] getData() throws IOException{
//		String loginData[][]= {
//				{"standard_user11", "secret_sauce", "Invalid"},
//				{"locked_out_user11", "secret_sauce", "Invalid"},
//				{"problem_user11", "secret_sauce", "Invalid"},
//				{"performance_glitch_user", "secret_sauce", "Valid"}
//		};
		//Get the data from excel
//		try {
		String path=".\\Files\\Log in Data.xlsx";
		XLUtility obj = new XLUtility(path);
		int TotalRows=obj.getRowCount("Sheet1");
		System.out.println("Number of Rows = "+TotalRows);
		int TotalCells=obj.getCellCount("Sheet1",2);
		System.out.println("Number of Cells = "+TotalCells);
		
		String loginData[][]= new String [TotalRows][TotalCells];
		for(int i=0; i<TotalRows; i++) {
			for(int j=0; j<TotalRows; j++) {
				loginData[i][j]=obj.getCellData("Sheet1", i, j);				
			}
		}
//		}
//		catch(Exception e) {
//	}
		return loginData;
	}
	
	@AfterClass
	public void end() {
		driver.close();
	}

}

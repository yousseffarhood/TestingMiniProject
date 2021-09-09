package convertWordToPDF;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ConvertProject {
	ChromeDriver driver;
	public static String downloadpath= System.getProperty("user.dir")+"\\download";
	public static ChromeOptions chromeoption()
	{
		ChromeOptions option=new ChromeOptions();
		HashMap<String, Object> chromeprefs=new HashMap<String,Object>();
		chromeprefs.put("profile.default.content_settings.popups", 0);
		chromeprefs.put("download.default_directory", downloadpath);
		option.setExperimentalOption("prefs", chromeprefs);
		option.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return option;
		
		
		
	}

	@BeforeTest
	public void openURL()
	{
		String path=System.getProperty("user.dir")+"\\chromedriversource\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);
		driver=new ChromeDriver(chromeoption());
		driver.manage().window().maximize();
		driver.navigate().to("https://smallpdf.com/word-to-pdf");
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	}

	@Test
	public void ConvertFile() throws AWTException, InterruptedException, IOException
	{String document="demoproject.docx";
	String path= System.getProperty("user.dir")+ "\\uploads\\"+document;
	WebElement uploadbtn= driver.findElement(By.cssSelector("button.l3tlg0-0.hrcxSS"));
	assertEquals("button", uploadbtn.getTagName());
	uploadbtn.click();
	//Ctrl+c
	Robot robot=new Robot();
	StringSelection selection=new StringSelection(path);
	Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
	clipboard.setContents(selection, null);
	robot.keyPress(KeyEvent.VK_ENTER);
	robot.keyRelease(KeyEvent.VK_ENTER);
	robot.delay(2000);
	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_CONTROL);
	robot.keyRelease(KeyEvent.VK_V);
	robot.delay(2000);
	robot.keyPress(KeyEvent.VK_ENTER);
	robot.keyRelease(KeyEvent.VK_ENTER);
	robot.delay(2000);
	robot.keyPress(KeyEvent.VK_CONTROL);
	robot.keyPress(KeyEvent.VK_V);
	robot.keyRelease(KeyEvent.VK_CONTROL);
	robot.keyRelease(KeyEvent.VK_V);
	robot.delay(2000);
	robot.keyPress(KeyEvent.VK_ENTER);
	robot.keyRelease(KeyEvent.VK_ENTER);
	@SuppressWarnings("deprecation")
	Wait<WebDriver> fwait=new FluentWait<WebDriver>(driver).
	withTimeout(30,TimeUnit.SECONDS).
	pollingEvery(2, TimeUnit.SECONDS).
	ignoring(NoSuchElementException.class);
	WebElement democonverted=driver.findElement(By.cssSelector("span.sc-11eo6r2-3.kNdpcX"));
	fwait.until(ExpectedConditions.visibilityOf(democonverted));
	assertEquals("Continue in", democonverted.getText());
	System.out.println(democonverted.getText());

WebElement downloadbtn= driver.findElement(By.cssSelector("button.l3tlg0-0.eqlXyA"));
downloadbtn.click();
Thread.sleep(3000);

File srcfile=driver.getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(srcfile, new File("./screenshots/downloadbtn.png"));

	}

	@AfterTest
	public void CloseWebSite()
	{
		driver.quit();
	}

}

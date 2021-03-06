package rateScreenFedEx;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import basePackage.BaseInit;
import basePackage.Email;

public class RateVerification extends BaseInit {
	static StringBuilder msg = new StringBuilder();
	static double quoteTime;

	@Test
	public void prService() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// Actions act = new Actions(driver);
		msg.append("Rate/Quote Verification Process Start.... " + "\n");
		long start, end;
		// --get the data
		System.out.println("Rate Verification start");

		//try {
			// 31
			for (int i = 1; i < 31; i++) {
				driver.getTitle();
				pause(1000);

				// --PickUp Zip
				String PUZip = getData("RateVerification", "Sheet1", i, 0);
				System.out.println("PickUp Zip==" + PUZip);
				driver.findElement(By.id("txtOrig")).clear();
				driver.findElement(By.id("txtOrig")).sendKeys(PUZip);
				driver.findElement(By.id("txtOrig")).sendKeys(Keys.TAB);
				Thread.sleep(5000);

				// --Delivery Zip
				String DLZip = getData("RateVerification", "Sheet1", i, 1);
				driver.findElement(By.id("txtDest")).clear();
				driver.findElement(By.id("txtDest")).sendKeys(DLZip);
				driver.findElement(By.id("txtDest")).sendKeys(Keys.TAB);
				Thread.sleep(5000);

				// --Weight
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActWt0")));
				driver.findElement(By.id("txtActWt0")).clear();
				driver.findElement(By.id("txtActWt0")).sendKeys("5");
				System.out.println("entered weight");
				driver.findElement(By.id("txtActWt0")).sendKeys(Keys.TAB);
				Thread.sleep(5000);

				// --Dim(L)
				driver.findElement(By.id("txtDimLen0")).clear();
				driver.findElement(By.id("txtDimLen0")).sendKeys("5");
				System.out.println("entered dim");
				pause(200);

				// --Dim(W)
				driver.findElement(By.id("txtDimWid0")).clear();
				driver.findElement(By.id("txtDimWid0")).sendKeys("5");
				System.out.println("entered wid");
				pause(200);

				// --Dim(H)
				driver.findElement(By.id("txtDimHt0")).clear();
				driver.findElement(By.id("txtDimHt0")).sendKeys("5");
				System.out.println("entered Ht");
				pause(200);

				// --ShipDate
				driver.findElement(By.id("datepicker")).clear();
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String newDate = dateFormat.format(cal.getTime());
				driver.findElement(By.id("datepicker")).sendKeys(newDate);
				System.out.println("Selected Date,current Date");
				driver.findElement(By.id("datepicker")).sendKeys(Keys.TAB);
				pause(200);

				// Select hour
				Select hour = new Select(driver.findElement(By.id("ddlPickupHour")));
				hour.selectByIndex(7); // AM
				System.out.println("select hour");
				pause(2000);

				// Select minuts
				Select minutes = new Select(driver.findElement(By.id("ddlPickupMinutes")));
				minutes.selectByIndex(1); // AM
				System.out.println("select minutes");
				pause(2000);

				// Select AM/PM
				Select AmPm = new Select(driver.findElement(By.id("ddlTimeType")));
				AmPm.selectByIndex(0); // AM
				System.out.println("select ampm");
				pause(2000);

				// --Click on show rates
				// --Start time
				start = System.nanoTime();
				driver.findElement(By.id("btngetQuickquote")).click();
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class=\"fdxRatetable\"]")));
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,350)", "");

				waitForVisibilityOfElement(By.xpath(".//*[@id='Process']"), 2);
				waitForInVisibilityOfElement(By.xpath(".//*[@id='Process']"), 30);
				waitForVisibilityOfElement(By.id("btnShip"), 30);
				// --End time
				end = System.nanoTime();
				quoteTime = (end - start) * 1.0e-9;
				System.out.println("Quote Time (in Seconds) = " + quoteTime);
				msg.append("Quote Time (in Seconds) = " + quoteTime + "\n");
				// --set the data

				String serviceid = getData("RateVerification", "Sheet1", i, 2);

				if (serviceid.equals("PR")) {
					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[3]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = getData("RateVerification", "Sheet1", i, 3);

					setData("RateVerification", "Sheet1", i, 4, actrate);
					msg.append("ServiceID==" + serviceid + "\n");
					// --Get the Rate details
					GetRateDetails(3, 1, 1, i);
					msg.append("Actual Rate==" + actrate + "\n");
					msg.append("Expected Rate==" + ExpectedRate + "\n");

					if (!actrate.equals(ExpectedRate)) {

						setData("RateVerification", "Sheet1", i, 5, "FAIL");
						msg.append("Result==" + "FAIL" + "\n\n");

					}

					else {
						setData("RateVerification", "Sheet1", i, 5, "PASS");
						msg.append("Result==" + "PASS" + "\n\n");

					}
				}

				else if (serviceid.equals("S2")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[5]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = getData("RateVerification", "Sheet1", i, 3);

					setData("RateVerification", "Sheet1", i, 4, actrate);
					msg.append("ServiceID==" + serviceid + "\n");
					// --Get the Rate details
					GetRateDetails(5, 2, 2, i);
					msg.append("Actual Rate==" + actrate + "\n");

					msg.append("Expected Rate==" + ExpectedRate + "\n");
					if (!actrate.equals(ExpectedRate)) {
						setData("RateVerification", "Sheet1", i, 5, "FAIL");
						msg.append("Result==" + "FAIL" + "\n\n");

					}

					else {
						setData("RateVerification", "Sheet1", i, 5, "PASS");
						msg.append("Result==" + "PASS" + "\n\n");

					}
				} else if (serviceid.equals("EC")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[7]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = getData("RateVerification", "Sheet1", i, 3);

					setData("RateVerification", "Sheet1", i, 4, actrate);
					msg.append("ServiceID==" + serviceid + "\n");
					// --Get the Rate details
					GetRateDetails(7, 3, 3, i);

					msg.append("Actual Rate==" + actrate + "\n");

					msg.append("Expected Rate==" + ExpectedRate + "\n");
					if (!actrate.equals(ExpectedRate)) {
						setData("RateVerification", "Sheet1", i, 5, "FAIL");
						msg.append("Result==" + "FAIL" + "\n\n");

					}

					else {
						setData("RateVerification", "Sheet1", i, 5, "PASS");
						msg.append("Result==" + "PASS" + "\n\n");

					}
				} else if (serviceid.equals("DR")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[1]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = getData("RateVerification", "Sheet1", i, 3);

					setData("RateVerification", "Sheet1", i, 4, actrate);
					msg.append("ServiceID==" + serviceid + "\n");
					// --Get the Rate details
					GetRateDetails(1, 0, 0, i);

					msg.append("Actual Rate==" + actrate + "\n");

					msg.append("Expected Rate==" + ExpectedRate + "\n");
					if (!actrate.equals(ExpectedRate)) {
						setData("RateVerification", "Sheet1", i, 5, "FAIL");
						msg.append("Result==" + "FAIL" + "\n\n");

					}

					else {
						setData("RateVerification", "Sheet1", i, 5, "PASS");
						msg.append("Result==" + "PASS" + "\n\n");

					}
				} else if (serviceid.equals("AIR")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[1]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = getData("RateVerification", "Sheet1", i, 3);

					setData("RateVerification", "Sheet1", i, 4, actrate);
					msg.append("ServiceID==" + serviceid + "\n");
					// --Get the Rate details
					GetRateDetails(1, 0, 0, i);
					msg.append("Actual Rate==" + actrate + "\n");

					msg.append("Expected Rate==" + ExpectedRate + "\n");
					if (!actrate.equals(ExpectedRate)) {
						setData("RateVerification", "Sheet1", i, 5, "FAIL");
						msg.append("Result==" + "FAIL" + "\n\n");

					}

					else {
						setData("RateVerification", "Sheet1", i, 5, "PASS");
						msg.append("Result==" + "PASS" + "\n\n");

					}

				}

				else if (serviceid.equals("DRV")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[1]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = getData("RateVerification", "Sheet1", i, 3);

					setData("RateVerification", "Sheet1", i, 4, actrate);
					msg.append("ServiceID==" + serviceid + "\n");
					// --Get the Rate details
					GetRateDetails(1, 0, 0, i);

					msg.append("Actual Rate==" + actrate + "\n");

					msg.append("Expected Rate==" + ExpectedRate + "\n");
					if (!actrate.equals(ExpectedRate)) {
						setData("RateVerification", "Sheet1", i, 5, "FAIL");
						msg.append("Result==" + "FAIL" + "\n\n");

					} else {
						setData("RateVerification", "Sheet1", i, 5, "PASS");
						msg.append("Result==" + "PASS" + "\n\n");

					}

				}

			}

			msg.append("Rate/Quote Verification Process Completed !!" + "\n");

			/*
			 * } catch (
			 * 
			 * Exception e) { System.out.println("Something went Wrong");
			 * msg.append("Rate/Quote Verification Process Completed !!==FAIL" + "\n");
			 * 
			 * }
			 */

		// Send Email

		String Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script: " + Env + " FedEx Rate/Quote";
		try {
			// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
			// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void GetRateDetails(int TBody, int Ebtn, int RDetail, int Col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions act = new Actions(driver);

		// --Expand the Rate
		String SXpath = "//*[@class=\"fdxRatetable\"]/tbody[" + TBody + "]//div[@id=\"toggle-" + Ebtn + "\"]/button";
		System.out.println("SExpand xpath==" + SXpath);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SXpath)));
		WebElement ExpandPR = driver.findElement(By.xpath(SXpath));
		act.moveToElement(ExpandPR).build().perform();
		act.moveToElement(ExpandPR).click().perform();

		// --Get the Rate details
		String RBasicXpath = "//*[@id=\"FdxrateDetail" + RDetail + "\"]";
		System.out.println("BRate Detail xpath==" + RBasicXpath);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(RBasicXpath)));

		String RNameXpath = RBasicXpath + "//td[1]/div";
		System.out.println("RName Xpath==" + RNameXpath);
		String RValueXpath = RBasicXpath + "//td[2]/div";
		System.out.println("RValue Xpath==" + RValueXpath);

		// --Create List of Rate Names
		List<WebElement> RateNames = driver.findElements(By.xpath(RNameXpath));
		int TotalRName = RateNames.size();
		System.out.println("Total Rates==" + TotalRName);

		for (int RN = 0; RN < TotalRName; RN++) {
			String RName = RateNames.get(RN).getText();
			System.out.println("Rate Name==" + RName);
			// --Create List of Rate Values
			List<WebElement> RateValues = driver.findElements(By.xpath(RValueXpath));
			int TotalRValue = RateValues.size();
			System.out.println("Total Rates==" + TotalRName);
			for (int RV = RN; RV < TotalRValue;) {
				String RValue = RateValues.get(RN).getText();
				System.out.println("Rate Value==" + RValue);
				msg.append(RName + "=" + RValue + "\n");
				if (RName.contains("Base Rate")) {
					setData("RateVerification", "Sheet1", Col, 7, RValue);
				} else if (RName.contains("Fuel Surcharge 5.75%")) {
					setData("RateVerification", "Sheet1", Col, 8, RValue);
				} else if (RName.contains("Excess Mileage")) {
					setData("RateVerification", "Sheet1", Col, 9, RValue);
				} else if (RName.contains("Estimated Total")) {
					setData("RateVerification", "Sheet1", Col, 10, RValue);
				} else if (RName.contains("AFTER HOURS (DELIVERY)")) {
					setData("RateVerification", "Sheet1", Col, 11, RValue);
				} else if (RName.contains("Delivery Mileage")) {
					setData("RateVerification", "Sheet1", Col, 12, RValue);
				} else {
					System.out.println("Unknown Rate found==" + RName);
				}

				break;
			}

		}

	}

	public static void pause(final int iTimeInMillis) {
		try {
			Thread.sleep(iTimeInMillis);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		} catch (Exception e) {
		}
	}

	public static void waitForInVisibilityOfElement(By objLocator, long lTime) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
	}
}

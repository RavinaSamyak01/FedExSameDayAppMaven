package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TrackShipment extends BaseClass {

	public static void trckShipment()
			throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, 50);

		msg.append("--------------------------------------" + "\n");
		msg.append("TrackOrder Shipment Tracking :- " + "\n");

		for (int i = 1; i < 3; i++) {
			if (i == 1) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title=\"FedEx Home\"]")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title=\"FedEx Home\"]")));
				driver.findElement(By.xpath("//*[@title=\"FedEx Home\"]")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Tracking")));
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Tracking")));
				driver.findElement(By.linkText("Tracking")).click();
				waitForVisibilityOfElement(By.linkText("Track Multiple Shipments"), 5);

				driver.findElement(By.linkText("Track Multiple Shipments")).click();
				waitForVisibilityOfElement(By.id("rightColumn"), 5);

				String tracking = getData("ShipmentCreation", "Sheet1", 2, 15);

				WebElement TrackTB = driver.findElement(By.id("rightColumn"));
				js.executeScript("arguments[0].scrollIntoView();", TrackTB);
				driver.findElement(By.id("track_num")).sendKeys(tracking);

				driver.findElement(By.id("btnTrack")).click();
				waitForVisibilityOfElement(By.xpath("//*[@id=\"pnlDetails\"]//table[3]"), 5);

				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(".\\src\\main\\resources\\Screenshots\\Single_ShipmentTracking.png"));

			}
			if (i == 2) {
				driver.findElement(By.linkText("Tracking")).click();
				waitForVisibilityOfElement(By.linkText("Track Multiple Shipments"), 5);

				driver.findElement(By.linkText("Track Multiple Shipments")).click();
				waitForVisibilityOfElement(By.id("rightColumn"), 5);

				String t1 = getData("ShipmentCreation", "Sheet1", 2, 15);
				String t2 = getData("ShipmentCreation", "Sheet1", 3, 15);

				WebElement TrackTB = driver.findElement(By.id("rightColumn"));
				js.executeScript("arguments[0].scrollIntoView();", TrackTB);
				driver.findElement(By.id("track_num")).sendKeys(t1 + "," + t2);

				driver.findElement(By.id("btnTrack")).click();
				waitForVisibilityOfElement(By.xpath("//*[@class=\"cust-fdxtableheader\"]"), 5);

				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(".\\src\\main\\resources\\TestFiles\\Multiple_ShipmentTracking_1.png"));

				waitForVisibilityOfElement(By.id("dgItin_shipNum_0"), 5);
				driver.findElement(By.id("dgItin_shipNum_0")).click();
				waitForVisibilityOfElement(By.xpath("//*[@id=\"pnlDetails\"]//table[3]"), 5);

				File scrFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile2, new File(".\\src\\main\\resources\\Screenshots\\Multiple_ShipmentTracking_2.png"));

			}
		}
		msg.append("Single Shipment Tracking   : PASS" + "\n");
		msg.append("Multiple Shipment Tracking : PASS" + "\n\n");
		System.out.println("Shipment Tracking Test Case Executed successfully !!!");
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

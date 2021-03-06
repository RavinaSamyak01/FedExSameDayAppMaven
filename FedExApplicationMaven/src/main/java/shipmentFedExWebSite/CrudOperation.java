package shipmentFedExWebSite;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import basePackage.BaseInit;

public class CrudOperation extends BaseInit {

	static StringBuilder msg = new StringBuilder();

	@Test
	public void crudOperations() throws Exception {
		Actions act = new Actions(driver);

		for (int i = 1; i < 5; i++) {

			WebDriverWait wait = new WebDriverWait(driver, 10);
			// --click on shipping menu
			driver.findElement(By.linkText("Shipping")).click(); // Click on ship screen
			Thread.sleep(2000);
			// --click on Create shipment
			driver.findElement(By.linkText("Create a Shipment")).click();
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("content1")));
			driver.getTitle();

			try {
				if (driver.findElement(By.id("cmdChangePUAddr")).isDisplayed()) // If my preferences has setup From
																				// Address
				{
					driver.findElement(By.id("cmdChangePUAddr")).click();
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("pnlFullPUAddr")));

				}
			} catch (Exception e) {
				System.out.println("No default address !!");
			}

			// Pickup Company
			String PUCompany = getData("CrudOperation", "Sheet1", i, 0);
			driver.findElement(By.xpath("//*[@id='pu_company']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_company']")).sendKeys(PUCompany);

			// Pickup Name
			String PUName = getData("CrudOperation", "Sheet1", i, 1);
			driver.findElement(By.xpath("//*[@id='pu_pertosee']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_pertosee']")).sendKeys(PUName);

			// PU Address line 1
			String PUAddress1 = getData("CrudOperation", "Sheet1", i, 2);
			driver.findElement(By.xpath("//*[@id='pu_addr1']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_addr1']")).sendKeys(PUAddress1);

			// Pickup Zip code and tab
			String PUZip = getData("CrudOperation", "Sheet1", i, 4);
			driver.findElement(By.xpath("//*[@id='pu_zip']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(PUZip);
			driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(Keys.TAB);
			Thread.sleep(2000);

			// Pickup phone Number
			String PUPhone = getData("CrudOperation", "Sheet1", i, 5);
			driver.findElement(By.xpath("//*[@id='pu_phone']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_phone']")).sendKeys(PUPhone);

			boolean pures = driver.findElement(By.id("Pu_Res")).isSelected();
			if (pures == false) {
				System.out.println("From address section 'RES' check box is Un-tick");

				boolean bussnessopen = driver.findElement(By.id("PUBOTHourDropDownList")).isEnabled();
				if (bussnessopen == true) {
					System.out.println("Business Open/Close Time is enable !!");
				}
			}

			driver.findElement(By.id("Pu_Res")).click();
			boolean pures1 = driver.findElement(By.id("Pu_Res")).isSelected();
			if (pures1 == true) {
				System.out.println("From address 'RES' check box is tick");

				boolean bussnessopen = driver.findElement(By.id("PUBOTHourDropDownList")).isEnabled();
				if (bussnessopen == false) {
					System.out.println("Business Open/Close Time is disable !!");
				}
			}

			driver.findElement(By.id("chkSAveAddrFrom")).click();

			// Delivery Information : To Address

			// Delivery Company name
			String DLCompany = getData("CrudOperation", "Sheet1", i, 6);
			driver.findElement(By.xpath("//*[@id='dl_company']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_company']")).sendKeys(DLCompany);

			// Delivery Contact Name
			String DLName = getData("CrudOperation", "Sheet1", i, 7);
			driver.findElement(By.xpath("//*[@id='dl_attn']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_attn']")).sendKeys(DLName);

			// Del Address line 1
			String DLAddress1 = getData("CrudOperation", "Sheet1", i, 8);
			driver.findElement(By.xpath("//*[@id='dl_addr1']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_addr1']")).sendKeys(DLAddress1);

			// Del Zip and tab
			String DLZip = getData("CrudOperation", "Sheet1", i, 10);
			driver.findElement(By.xpath("//*[@id='dl_zip']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(DLZip);
			driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(Keys.TAB);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Thread.sleep(2000);

			// Del Phone number
			String DLPhone = getData("CrudOperation", "Sheet1", i, 11);
			driver.findElement(By.xpath("//*[@id='dl_phone']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_phone']")).sendKeys(DLPhone);

			boolean dlres = driver.findElement(By.id("dl_Res")).isSelected();
			if (dlres == false) {
				System.out.println("TO address section 'RES' check box is Un-tick");

				boolean bussnessopenclose = driver.findElement(By.id("DLBOTHourDropDownList")).isEnabled();
				if (bussnessopenclose == true) {
					System.out.println("Business Open/Close Time is enable !!");
				}
			}

			driver.findElement(By.id("dl_Res")).click();
			boolean dlres1 = driver.findElement(By.id("dl_Res")).isSelected();
			if (dlres1 == true) {
				System.out.println("TO address 'RES' check box is tick");

				boolean bussnessopenclose1 = driver.findElement(By.id("DLBOTHourDropDownList")).isEnabled();
				if (bussnessopenclose1 == false) {
					System.out.println("Business Open/Close Time is disable !!");
				}
			}

			driver.findElement(By.id("chkSaveAddrTo")).click();

			if (i == 4) {
				JavascriptExecutor jse2 = (JavascriptExecutor) driver;
				jse2.executeScript("window.scrollBy(0,-950)", "");
				driver.findElement(By.id("lnkClearPU")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("pu_quickcode")).sendKeys("QC_AUTOMAT");
				driver.findElement(By.id("pu_quickcode")).sendKeys(Keys.TAB);
				Thread.sleep(2000);
				jse2.executeScript("window.scrollBy(0,500)", "");
				driver.findElement(By.id("lnkClearDl")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("dl_quickcode")).sendKeys("QC_AUTOMAT");
				driver.findElement(By.id("dl_quickcode")).sendKeys(Keys.TAB);
				Thread.sleep(2000);
			}

			Thread.sleep(4000);

			driver.findElement(By.id("anchor1xx")).click(); // click on calendar
			driver.findElement(By.xpath("//a[contains(.,'Today')]")).click(); // select today

			Thread.sleep(4000);

			Select select1 = new Select(driver.findElement(By.id("ddlReadyHour"))); // ready time selection
			select1.selectByVisibleText("11");

			select1 = new Select(driver.findElement(By.id("ddlReadyMinutes"))); // ready time min selection
			select1.selectByVisibleText("30");

			select1 = new Select(driver.findElement(By.xpath(".//*[@name='ddlReadyTimeType']"))); // AM/ PM selection
			select1.selectByVisibleText("AM");
			Thread.sleep(2000);

			String serviceid = getData("CrudOperation", "Sheet1", i, 13); // Service ID compare from the
																			// Excel

			if (serviceid.equals("DRV") || serviceid.equals("AIR") || serviceid.equals("SDC")
					|| serviceid.equals("FRG")) {
				driver.findElement(By.name("txt_content")).clear();
				driver.findElement(By.name("txt_content")).sendKeys("BOX"); // Enter value in contents text box
			}

			String pieces = getData("CrudOperation", "Sheet1", i, 14);
			driver.findElement(By.id("pieces")).sendKeys(Keys.BACK_SPACE);
			driver.findElement(By.id("pieces")).sendKeys(Keys.BACK_SPACE);
			pieces = getData("CrudOperation", "Sheet1", i, 14);
			driver.findElement(By.id("pieces")).sendKeys(pieces);
			Thread.sleep(1000);
			driver.findElement(By.id("pieces")).sendKeys(Keys.TAB);
			Random rn = new Random(); // Generate random numbers
			int pval = Integer.parseInt(pieces);
			if (pval == 1) {
				int ans;
				if (serviceid.equals("FRG")) {
					ans = rn.nextInt(200) + 1;
				} else {
					ans = rn.nextInt(10) + 1;
				}
				String st = String.valueOf(ans);

				// Dim

				new Select(driver.findElement(By.id("ddProfile"))).selectByVisibleText("Enter dimensions");
				driver.findElement(By.id("txtDimLen0")).clear();
				driver.findElement(By.id("txtDimLen0")).sendKeys(st);
				driver.findElement(By.id("txtDimWid0")).clear();
				driver.findElement(By.id("txtDimWid0")).sendKeys(st);
				driver.findElement(By.id("txtDimHt0")).clear();
				driver.findElement(By.id("txtDimHt0")).sendKeys(st);
				driver.findElement(By.id("txtActWt0")).clear();
				driver.findElement(By.id("txtActWt0")).sendKeys(st);
				driver.findElement(By.id("order_by")).clear();
				driver.findElement(By.id("order_by")).sendKeys("Abhishek Sharma");
				driver.findElement(By.id("order_phone")).clear();
				driver.findElement(By.id("order_phone")).sendKeys("1112223333");
			} else {

				driver.findElement(By.id("rdbNo")).click();
				String drpdim;
				String txtqt;
				String dimlen;
				String dimwh;
				String dimhi;
				String ActWt;
				for (int j = 0; j < pval; j++) {

					int ans = rn.nextInt(10) + 1;
					String st = String.valueOf(ans);
					drpdim = "drpdim" + j;
					txtqt = "txtQty" + j;
					dimlen = "txtDimLenN" + j;
					dimwh = "txtDimWidN" + j;
					dimhi = "txtDimHtN" + j;
					ActWt = "txtActWtNew" + j;

					driver.findElement(By.id(txtqt)).clear();
					driver.findElement(By.id(txtqt)).sendKeys("1");

					// --select dim form Dim dropdown
					Select dim = new Select(driver.findElement(By.id(drpdim)));
					dim.selectByVisibleText("Enter dimensions");
					Thread.sleep(2000);

					driver.findElement(By.id(dimlen)).clear();
					driver.findElement(By.id(dimlen)).sendKeys(st);
					driver.findElement(By.id(dimwh)).clear();
					driver.findElement(By.id(dimwh)).sendKeys(st);
					driver.findElement(By.id(dimhi)).clear();
					driver.findElement(By.id(dimhi)).sendKeys(st);
					driver.findElement(By.id(ActWt)).clear();
					driver.findElement(By.id(ActWt)).sendKeys(st);
				}

			}

			Thread.sleep(1000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-850)", "");
			driver.findElement(By.id("lnkCalculate")).click(); // Click on calculate link
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("divAvailableServicesInternal")));

			driver.findElement(By.id("declared_value")).clear();
			driver.findElement(By.id("declared_value")).sendKeys("2000");
			driver.findElement(By.id("declared_value")).sendKeys(Keys.TAB);

			System.out.println(driver.findElement(By.id("lblDecValDimErr")).getText());
			String signature = driver.findElement(By.id("ddlSignatureType")).getAttribute("value");
			System.out.println(signature);
			Thread.sleep(1000);

			if (i == 2) {
				driver.findElement(By.id("dl_DoNotDeliver")).click();
			}

			driver.findElement(By.id("cmdSubmit")).click(); // Create job button
			Thread.sleep(5000);

			try {
				boolean recalc = driver.findElement(By.id("lblRecalMsg")).isDisplayed();

				if (recalc == true) {
					driver.findElement(By.id("lnkCalculate")).click();
					wait.until(
							ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("divAvailableServicesInternal")));
				}

				// Service

				if (serviceid.equals("PR")) // If match with PR, below code will execute
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkPR")));
					driver.findElement(By.id("chkPR")).click();
					String rate = driver.findElement(By.id("btnPR")).getText();
					System.out.println(rate);
					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					msg.append("PR Service - Actual Rate :" + rate + "\n");
					setData("CrudOperation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");

						// workbook.write(fis1);

					}

					else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");
						// workbook.write(fis1);

					}

				}

				else if (serviceid.equals("S2")) // If match with S2, below code will execute
				{

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkS2")));
					driver.findElement(By.id("chkS2")).click();

					String rate = driver.findElement(By.id("btnS2")).getText();
					System.out.println(rate);
					msg.append("S2 Service - Actual Rate :" + rate + "\n");
					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					setData("CrudOperation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
						// workbook.write(fis1);

					}

					else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");
						// workbook.write(fis1);

					}

				}

				else if (serviceid.equals("EC")) // If match with EC, below code will execute
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkSDRTS")));
					driver.findElement(By.id("chkSDRTS")).click();
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					driver.findElement(By.id("chkEC")).click();
					String rate = driver.findElement(By.id("btnEC")).getText();
					System.out.println(rate);
					msg.append("EC Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					setData("CrudOperation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
						// workbook.write(fis1);

					}

					else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");
						// workbook.write(fis1);

					}

				}

				else if (serviceid.equals("DR")) // If match with DR, below code will execute
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkDR")));
					driver.findElement(By.id("chkDR")).click();

					String rate = driver.findElement(By.id("btnDR")).getText();
					System.out.println(rate);
					msg.append("DR Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					setData("CrudOperation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
						// workbook.write(fis1);

					} else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");
						// workbook.write(fis1);

					}
				}

				else if (serviceid.equals("DRV")) // If match with DRV, below code will execute
				{

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkDRV")));
					driver.findElement(By.id("chkDRV")).click();

					String rate = driver.findElement(By.id("btnDRV")).getText();
					System.out.println(rate);
					msg.append("DRV Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					setData("CrudOperation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
						// workbook.write(fis1);

					}

					else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");
						// workbook.write(fis1);

					}

				}

				else if (serviceid.equals("AIR")) // If match with AIR, below code will execute
				{

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkAIR")));
					driver.findElement(By.id("chkAIR")).click();

					String rate = driver.findElement(By.id("btnAIR")).getText();
					System.out.println(rate);
					msg.append("AIR Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					setData("CrudOperation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
						// workbook.write(fis1);

					}

					else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");
						// workbook.write(fis1);

					}

				}

				else if (serviceid.equals("SDC"))// If match with SDC, below code will execute
				{

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkSDC")));
					driver.findElement(By.id("chkSDC")).click();

					String rate = driver.findElement(By.id("btnSDC")).getText();
					System.out.println(rate);
					msg.append("SDC Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("CrudOperation", "Sheet1", i, 12);
					setData("CrudOperation", "Sheet1", i, 16, rate);

					setData("CrudOperation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("CrudOperation", "Sheet1", i, 17, "FAIL");
					}

					else {
						setData("CrudOperation", "Sheet1", i, 17, "PASS");

					}

				}

				else if (serviceid.equals("FRG")) // If match with FRG, below code will execute
				{

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkFRG")));
					driver.findElement(By.id("chkFRG")).click();

					String rate = driver.findElement(By.id("btnFRG")).getText();
					System.out.println(rate);
					msg.append("FRG Service - Actual Rate :" + rate + "\n");

				}

				if (i == 3) {
					driver.findElement(By.id("chkSaveDim")).click();
					Thread.sleep(1000);
					driver.findElement(By.id("txtprofilename")).sendKeys("PKG-Dim");
					Thread.sleep(1000);
					JavascriptExecutor jse1 = (JavascriptExecutor) driver;
					jse1.executeScript("window.scrollBy(0,-700)", "");
					driver.findElement(By.id("lnkCalculate")).click(); // Click on calculate link
					wait.until(
							ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("divAvailableServicesInternal")));
				}

				driver.findElement(By.id("cmdSubmit")).click(); // Create job button
				Thread.sleep(2000);
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblErrMessage")));
					WebElement ACRestriction = driver.findElement(By.id("lblErrMessage"));
					act.moveToElement(ACRestriction).build().perform();
					if (ACRestriction.isDisplayed()) {
						String ErrMsg = driver.findElement(By.id("lblErrMessage")).getText();
						System.out.println("Validation Message is displayed==" + ErrMsg);
						msg.append("Validation Message is displayed==" + ErrMsg + "\n");
						System.out.println("Account is restricted, Please Active the account");
						msg.append("Account is restricted, Please Active the account" + "\n");

					}

				} catch (Exception ACRestriction) {

				}

				// If alert pop-up exist, than accept.

				if (isAlertPresent()) {
					Alert alt = driver.switchTo().alert();
					alt.accept();
				}

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("currentForm")));
				driver.getTitle();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id=cmdSubmit]")));
				driver.findElement(By.cssSelector("input[id=cmdSubmit]")).click(); // Confirm from Shipment Summary
																					// screen.

				try {
					String VoucherNum = driver.findElement(By.xpath("//*[@id='lblVoucherNum']")).getText(); // Get //
																											// variable
					System.out.println("Shipment Tracking # " + VoucherNum);
					setData("CrudOperation", "Sheet1", i, 15, VoucherNum);
					msg.append("Shipment Tracking # " + VoucherNum + "\n\n");
				} catch (Exception e) {
					System.out.println("Write voucher in excel is working !!");
				}

			} catch (Exception e) {
				System.out.println("Recalculate message is not displayed");
			}
			// Send Email
		}
		String Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script: " + Env + " FedEx Crud operation";
		try {
			basePackage.Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(CrudOperation.class.getName()).log(Level.SEVERE, null, ex);
		}

		/*
		 * driver.findElement(By.linkText("Rate")).click(); // Go to Rate screen //
		 * driver.findElement(By.id("Wuc_headerlogout1_lnkbtnLogout")).click(); // click
		 * // on Logout driver.quit(); // close browser
		 */
	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		} catch (Exception e) {
		}
	}

	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public static void waitForInVisibilityOfElement(By objLocator, long lTime) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
	}
}

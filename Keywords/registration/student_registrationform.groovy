package registration

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import internal.GlobalVariable
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.Keys
import java.text.SimpleDateFormat
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.configuration.RunConfiguration


public class student_registrationform {


	@Keyword
	def InputField(String value, String objectPath, String fieldName = "Field") {
		try {
			// Ambil object dari repository
			TestObject inputField = findTestObject(objectPath)
			// Input value (STOP_ON_FAILURE kalau gagal)
			WebUI.setText(inputField, value, FailureHandling.STOP_ON_FAILURE)
			// Menampilkan log informasi di console (berhasil)
			KeywordUtil.logInfo("${fieldName} berhasil diisi : " + value)
			KeywordUtil.markPassed("Berhasil Input ${fieldName}")
		} catch (Exception e) {
			// Menampilkan log informasi di console (gagal) lalu stop test
			KeywordUtil.markFailedAndStop("Gagal Input ${fieldName}. Error : " + e.message)
		}
	}


	@Keyword
	def SelectGender(String genderValue) {
		// Cek gender male, dan pilih male
		if (genderValue.equalsIgnoreCase("Male")) {
			WebUI.click(findTestObject('Registration/gender_Male'))
			KeywordUtil.logInfo("Radio Button Male dipilih")
			// Cek gender female, dan pilih female
		} else if (genderValue.equalsIgnoreCase("Female")) {
			WebUI.click(findTestObject('Registration/gender_Female'))
			KeywordUtil.logInfo("Radio Button Female dipilih")
			// Menampilkan log informasi di console tidak valid
		} else {
			KeywordUtil.logInfo("Gender value tidak valid : " + genderValue)
		}
	}


	//	@Keyword
	//	def setDate(String dateValue, String objectPath, String inputFormat = "dd-MM-yyyy", String outputFormat = "dd MMM yyyy") {
	//		try {
	//			// Parsing: ubah string jadi Date
	//			Date date = new SimpleDateFormat(inputFormat, Locale.ENGLISH).parse(dateValue)
	//			// Formatting: ubah Date ke format yang dibutuhkan UI
	//			String formattedDate = new SimpleDateFormat(outputFormat, Locale.ENGLISH).format(date)
	//			// Input ke field
	//			TestObject dateField = findTestObject(objectPath)
	//			WebUI.clearText(dateField)
	//			WebUI.setText(dateField, formattedDate)
	//			// Menampilkan log informasi di console
	//			KeywordUtil.logInfo("Tanggal berhasil diinput : " + formattedDate)
	//		} catch (Exception e) {
	//			KeywordUtil.markFailed("Gagal input tanggal : " + e.message)
	//		}
	//	}


	@Keyword
	def selectSubjects(String values, String objectPath) {
		try {
			// Ambil object dari repository
			TestObject inputField = findTestObject(objectPath)
			// Pisahkan jika subject lebih dari satu (array)
			def subjects = values.split(",")
			// Perulangan untuk subject
			for (String subject : subjects) {
				// Hapus spasi di awal/akhir teks
				subject = subject.trim()
				// Input subject dan enter
				WebUI.setText(inputField, subject)
				WebUI.sendKeys(inputField, Keys.chord(Keys.ENTER))
				// Menampilkan log informasi di console
				KeywordUtil.logInfo("Berhasil pilih subject : " + subject)
			}
			// Log selesai
			KeywordUtil.markPassed("Semua subjects berhasil diinput : " + values)
		} catch (Exception e) {
			// Log gagal + stop test
			KeywordUtil.markFailedAndStop("Gagal input subjects. Error : " + e.message)
		}
	}


	@Keyword
	def selectHobby(String hobbyValue) {
		// Pisahkan jika hobi lebih dari satu
		def hobbies = hobbyValue.split(",")
		// Hapus spasi di awal/akhir teks
		hobbies = hobbies*.trim()
		// Perulangan untuk hobi, jika lebih dari satu
		hobbies.each { hobby ->
			TestObject checkbox = null
			// Mencocokkan pilihan hobi
			switch(hobby) {
				case "Sports":
					checkbox = findTestObject('Registration/hobbies_Sport')
					break
				case "Reading":
					checkbox = findTestObject('Registration/hobbies_Reading')
					break
				case "Music":
					checkbox = findTestObject('Registration/hobbies_Music')
					break
				default:
					KeywordUtil.logInfo("Hobby tidak dikenal : " + hobby)
			}
			// Jika hobi ada dalam pilihan
			if (checkbox != null) {
				if (!WebUI.verifyElementChecked(checkbox, 1, FailureHandling.OPTIONAL)) {
					WebUI.click(checkbox)
					KeywordUtil.logInfo(hobby + " Berhasil dicentang")
				}
			}
		}
	}


	@Keyword
	def uploadFile(TestObject to, String filePath) {
		try {
			// Menunggu element terlihat
			WebUI.waitForElementVisible(to, 3)
			// Upload file
			WebUI.uploadFile(to, filePath)
			// Jika berhasil
			KeywordUtil.logInfo("File berhasil diupload : " + filePath)
			KeywordUtil.markPassed("Upload file sukses")
		} catch (Exception e) {
			// Jika gagal
			KeywordUtil.logInfo("Gagal upload file : " + filePath)
			KeywordUtil.markFailed("Upload file gagal. Error : " + e.message)
		}
	}


	@Keyword
	def selectDropdown(String value, String objectPath, String fieldName = "Dropdown") {
		try {
			// Ambil object dari repository
			TestObject inputField = findTestObject(objectPath)
			// Menunggu element terlihat
			WebUI.waitForElementVisible(inputField, 3)
			// Input value & tekan enter
			WebUI.setText(inputField, value)
			WebUI.sendKeys(inputField, Keys.chord(Keys.ENTER))
			// Menampilkan log informasi di console (berhasil)
			KeywordUtil.logInfo("Pilih ${fieldName} : " + value)
			KeywordUtil.markPassed("${fieldName} berhasil dipilih : " + value)
		} catch (Exception e) {
			// Menampilkan log informasi di console (gagal)
			KeywordUtil.markFailed("Gagal memilih ${fieldName}. Error : " + e.message)
		}
	}


	@Keyword
	def takeScreenshot() {
		// Get lokasi direktori
		String projectDir = RunConfiguration.getProjectDir()
		// Buat timestamp
		String timestamp = new Date().format("dd-MM-yyyy_HH-mm-ss")
		// Menentukan lokasi folder
		String path = projectDir + "/Screenshots/Registration/registration_page_" + timestamp + ".png"
		// Mengambil screenshot
		WebUI.takeScreenshot(path)
		// Menampilkan log informasi di console
		println "Screenshot tersimpan di : " + path
	}


	//	@Keyword
	//	def takeScreenshotWithName(String fileName) {
	//		// Get lokasi direktori
	//		String projectDir = RunConfiguration.getProjectDir()
	//		// Menentukan lokasi folder
	//		String path = projectDir + "/Screenshots/" + fileName + ".png"
	//		// Mengambil screenshot
	//		WebUI.takeScreenshot(path)
	//		// Menampilkan log informasi di console
	//		println "Screenshot tersimpan di : " + path
	//	}
}


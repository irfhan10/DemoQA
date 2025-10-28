package registrationform

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
	def verifyURL(String expectedURL) {
		try {
			String currentURL = WebUI.getUrl()
			if (currentURL == expectedURL) {
				KeywordUtil.markPassed("URL sesuai: " + currentURL)
			} else {
				KeywordUtil.markFailed("URL tidak sesuai.\nExpected: ${expectedURL}\nActual: ${currentURL}")
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Gagal memverifikasi URL. Error: " + e.message)
		}
	}



	@Keyword
	def InputField(String value, String objectPath, String fieldName = "Field") {
		try {
			// Ambil object dari repository
			TestObject inputField = findTestObject(objectPath)
			// Input value (STOP_ON_FAILURE kalau gagal)
			WebUI.setText(inputField, value, FailureHandling.STOP_ON_FAILURE)

			// Assertion nilai field sesuai yang diinput
			WebUI.verifyElementAttributeValue(inputField, 'value', value, 5)

			KeywordUtil.logInfo("${fieldName} berhasil diisi dan diverifikasi: " + value)
			KeywordUtil.markPassed("Berhasil Input & Verifikasi ${fieldName}")
		} catch (Exception e) {
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
				KeywordUtil.logInfo("Berhasil pilih subject : " + subject)

				// Assertion subject saat muncul di field
				TestObject subjectTag = new TestObject().addProperty("xpath",
						ConditionType.EQUALS, "//div[contains(@class,'subjects-auto-complete__multi-value__label') and text()='" + subject + "']")
				WebUI.verifyElementPresent(subjectTag, 5)
				KeywordUtil.logInfo("Verifikasi subject muncul: " + subject)
			}

			KeywordUtil.markPassed("Semua subjects berhasil diinput : " + values)
		} catch (Exception e) {
			KeywordUtil.markFailedAndStop("Gagal input subjects. Error : " + e.message)
		}
	}



	@Keyword
	def selectHobby(String hobbyValue) {
		// Pisahkan jika hobi lebih dari satu dan Hapus spasi di awal/akhir teks
		def hobbies = hobbyValue.split(",")*.trim()
		// Perulangan untuk hobi, jika lebih dari satu
		hobbies.each { hobby ->
			TestObject labelObj = null
			TestObject inputObj = null

			switch(hobby) {
				case "Sports":
					labelObj = findTestObject('Registration/hobbies_Sport')
					inputObj = findTestObject('Registration/klik_hobbies_Sport')
					break
				case "Reading":
					labelObj = findTestObject('Registration/hobbies_Reading')
					inputObj = findTestObject('Registration/klik_hobbies_Reading')
					break
				case "Music":
					labelObj = findTestObject('Registration/hobbies_Music')
					inputObj = findTestObject('Registration/klik_hobbies_Music')
					break
				default:
					KeywordUtil.markWarning("Hobby tidak dikenal : " + hobby)
					return
			}
			// Jika hobi ada dalam pilihan
			if (labelObj != null && inputObj != null) {
				def isChecked = WebUI.getAttribute(inputObj, 'checked')

				if (isChecked == null) {
					WebUI.click(labelObj)
					WebUI.delay(1)
					WebUI.verifyElementChecked(inputObj, 5)
					KeywordUtil.markPassed("Checkbox '${hobby}' berhasil dipilih.")
				} else {
					KeywordUtil.logInfo("Checkbox '${hobby}' sudah dipilih.")
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



	@Keyword
	def verifikasiModalTampil() {
		// Verifikasi popup sukses tidak muncul
		boolean isModalPresent = WebUI.verifyElementPresent(findTestObject('Registration/modal_thanks_for_submit'), 3, FailureHandling.OPTIONAL)

		if (isModalPresent) {
			KeywordUtil.markPassed("Modal Thanks for submitting the form tampil")
		} else {
			KeywordUtil.markFailed("Modal Thanks for submitting the form tidak tampil")
		}
		WebUI.verifyTextPresent('Thanks for submitting the form', false)
	}



	@Keyword
	def verifikasiModalTidakTampil() {
		// Verifikasi popup sukses tidak muncul
		boolean isModalNotPresent = WebUI.verifyElementNotPresent(findTestObject('Registration/modal_thanks_for_submit'), 3, FailureHandling.OPTIONAL)

		if (isModalNotPresent) {
			KeywordUtil.markPassed("Modal Thanks for submitting the form tidak tampil")
		} else {
			KeywordUtil.markFailed("Modal Thanks for submitting the form tampil")
		}
	}
}


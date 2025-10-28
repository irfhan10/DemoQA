package verification

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

import internal.GlobalVariable

public class soft_assert {

	// Soft Verify Element Text, verifikasi teks dari element tanpa menghentikan test case
	@Keyword
	def verifyElementText(TestObject to, String expectedText) {
		try {
			WebUI.verifyElementText(to, expectedText)
			KeywordUtil.markPassed("[Soft Verify] Teks sesuai: '${expectedText}'")
		} catch (Exception e) {
			KeywordUtil.markFailed("[Soft Verify] Teks tidak sesuai: ${e.message}")
		}
	}


	// Soft Verify Element Visible, cek element di halaman
	@Keyword
	def verifyElementVisible(TestObject to) {
		try {
			WebUI.verifyElementVisible(to)
			KeywordUtil.markPassed("[Soft Verify] Elemen terlihat: ${to.getObjectId()}")
		} catch (Exception e) {
			KeywordUtil.markFailed("[Soft Verify] Elemen tidak terlihat: ${to.getObjectId()}")
		}
	}


	// Soft Verify Match Text, bandingkan dua teks (actual dan expected)
	@Keyword
	def verifyMatchText(String actual, String expected) {
		try {
			WebUI.verifyMatch(actual, expected, false)
			KeywordUtil.markPassed("[Soft Verify] Text cocok dengan expected")
		} catch (Exception e) {
			KeywordUtil.markFailed("[Soft Verify] Text tidak cocok: ${e.message}")
		}
	}
}


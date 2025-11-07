import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

WebUI.callTestCase(findTestCase('Registration/RegistrationForm/RegistrationScreen'), [:], FailureHandling.OPTIONAL)

WebUI.delay(5)

CustomKeywords.'registrationform.student_registrationform.InputField'(first_name, 'Registration/FirstName')

CustomKeywords.'registrationform.student_registrationform.InputField'(last_name, 'Registration/LastName')

CustomKeywords.'registrationform.student_registrationform.InputField'(email_user, 'Registration/UserEmail')

CustomKeywords.'registrationform.student_registrationform.SelectGender'(gender_user)

CustomKeywords.'registrationform.student_registrationform.InputField'(mobile_user, 'Registration/UserNumber')

CustomKeywords.'registrationform.student_registrationform.selectSubjects'(subjects_user, 'Registration/Subjects')

CustomKeywords.'registrationform.student_registrationform.selectHobby'(hobbies_user)

CustomKeywords.'registrationform.student_registrationform.uploadFile'(findTestObject('Registration/UploadPicture'), picture_user)

CustomKeywords.'registrationform.student_registrationform.InputField'(address_user, 'Registration/CurrentAddress')

CustomKeywords.'registrationform.student_registrationform.selectDropdown'(state_user, 'Registration/State')

CustomKeywords.'registrationform.student_registrationform.selectDropdown'(city_user, 'Registration/City')

WebUI.delay(5)

WebUI.click(findTestObject('Registration/button_Submit'))

WebUI.delay(5)

CustomKeywords.'registrationform.student_registrationform.verifikasiModalThanks'()

WebUI.delay(5)

CustomKeywords.'registrationform.student_registrationform.takeScreenshot'()

WebUI.delay(5)

CustomKeywords.'registrationform.student_registrationform.handleModal'()

WebUI.closeBrowser()


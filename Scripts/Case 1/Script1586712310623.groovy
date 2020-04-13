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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By



WebUI.openBrowser('')

WebDriver driver = DriverFactory.getWebDriver()

WebUI.navigateToUrl('http://prestashop-automation.qatestlab.com.ua/ru/')

result = WebUI.getText(findTestObject('Object Repository/Test 1/Page_prestashop-automation/div_    UAH                                _1402b7'))

sign = result[-3]

popular_items = driver.findElements(By.xpath("//article"))

for (def item : popular_items) 
{
	txt = item.findElement(By.xpath("div/div/div/span")).text
	WebUI.verifyMatch(txt, '.*'+sign+'.*', true)
}

WebUI.click(findTestObject('Object Repository/Test 1/Page_prestashop-automation/i_'))

WebUI.click(findTestObject('Object Repository/Test 1/Page_prestashop-automation/a_USD'))

WebUI.click(findTestObject('Object Repository/Test 1/Page_prestashop-automation/span_061'))

WebUI.setText(findTestObject('Object Repository/Test 1/Page_prestashop-automation/input_Summer Dresses_s'), 'dress.')

WebUI.click(findTestObject('Object Repository/Test 1/Page_prestashop-automation/i__1'))

items_text = WebUI.getText(findTestObject('Object Repository/Test 1/Page_/p_ 7'))

list = driver.findElements(By.xpath("//*[@id='js-product-list']/div[1]/article"))

WebUI.verifyMatch(items_text, 'Товаров: '+list.size()+'.*', true)

cur = driver.findElement(By.xpath("//span[@class='expand-more _gray-darker hidden-sm-down']")).text

prices = driver.findElements(By.xpath("//article/div/div/div/span[@class='price']"))

for (def price : prices) 
{
	WebUI.verifyMatch(price.text, '.*'+cur[-1], true)
}

WebUI.click(findTestObject('Object Repository/Test 1/Page_/i_'))

WebUI.click(findTestObject('Object Repository/Test 1/Page_/a_'))

articles =  driver.findElements(By.xpath("//article"))

def oldPricesList = []

for (def art : articles)
{
	prcs = art.findElement(By.xpath("div/div/div/span"))
	for (def pr : prcs)
	{
		oldPricesList << pr.text
	}
}

def newOldPricesList = []
newOldPricesList.addAll(oldPricesList)

newOldPricesList.sort()
newOldPricesList.reverse(true)

WebUI.verifyEqual(oldPricesList, newOldPricesList)

def discounts = []

for (def art : articles)
{
	prcs = art.findElements(By.xpath("div/div/div/span"))
	if (prcs.size()>2)
	{
		def tmp = []
		for (def pr : prcs)
		{
			tmp << pr.text.replace(",", ".")
		}
		tmp.remove(tmp.size()-1)
		discounts << tmp
	}	
}

for (discount in discounts)
{
	WebUI.verifyMatch(discount[1], '.*%', true)
	for (int i = 0; i < discount.size(); i++)
	{
		discount[i] = Double.valueOf(discount[i].replaceAll("[^-,0-9.]", ""))
	}
}

for (discount in discounts)
{
	calculated = 100 * ((discount[2] - discount[0]) / discount[0]).round(2)
	WebUI.verifyEqual(calculated, discount[1])
}


WebUI.closeBrowser()


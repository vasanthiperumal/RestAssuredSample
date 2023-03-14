package com.cdd.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CascadingDropdownScenarios {

	@Test

	public void cascadingDropdownScenarios () throws InterruptedException, StreamReadException, DatabindException, IOException {
		//Launch browser
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		WebDriver driver=new ChromeDriver(ops);
			
		//driver.get("E:\\Vasanthi-SDET\\WorkspaceVasanthi\\SeleniumLocators\\src\\main\\resources\\htmlFiles\\CascadingDropDownExample.html");
		driver.get("file:///E:/Vasanthi-SDET/WorkspaceVasanthi/SeleniumLocators/src/main/resources/htmlFiles/CascadingDropDownExample.html");
		
		//verify values in Subjects dropdown
		WebElement subjectDropDown=driver.findElement(By.id("subject"));
		Select selectSubjectDropDown= new Select(subjectDropDown);
		
//		List<WebElement> allSubjectElement = selectSubjectDropDown.getOptions();
//		List<String> actualSubjects = new ArrayList();
//		for(WebElement ele :  allSubjectElement )
//		{
//			actualSubjects.add(ele.getText());
//		}
		
		//Java Stream
		//Get list of Actual Subjects Displayed
		List<String> actualSubjects = new ArrayList<>();
		actualSubjects=selectSubjectDropDown.getOptions().stream().map(ele->ele.getText()).collect(Collectors.toList());
		actualSubjects.remove(0);//removes the [0] element in array, here "Select Subject"
		System.out.println("Actual Subjects : "+ actualSubjects);
		
		//Read Expected Subjects Data from Json file as Map - bcz expected espected JSON is a JSON object
		File expectedJsonFile = new File(System.getProperty("user.dir")+"/src/main/resources/jsonFiles/expecteddata.json");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> expectedJsonDataMap= objectMapper.readValue(expectedJsonFile,new TypeReference<Map<String,Object>>(){});
		
		//Get all expected subjects value and convert to List
		Set<String> expectedSubjectSet = expectedJsonDataMap.keySet();
		List<String> expectedSubject =new ArrayList<String>();
		expectedSubject.addAll(expectedSubjectSet);
		System.out.println("Expected Subjects : "+ expectedSubject);
		
		//Asserting actual and expected subjects
		Assert.assertEquals(actualSubjects, expectedSubject);
		selectSubjectDropDown.selectByValue("Front-end");
		Thread.sleep(5000);
		
		//Get list of Actual Topics Displayed
		WebElement dropDownTopic = driver.findElement(By.id("topic"));
		Select selectDropDownTopic = new Select(dropDownTopic);
		List<String> actualTopics = new ArrayList<>();
		actualTopics = selectDropDownTopic.getOptions().stream().map(ele->ele.getText()).collect(Collectors.toList());
		actualTopics.remove(0);
		System.out.println("Actual Topics: "+ actualTopics);
		
		//Read Expected Topics Data from Json file
		Object frontEndvalue = expectedJsonDataMap.get("Front-end");
		Map<String,Object> frontEndMap = (Map<String,Object>)frontEndvalue;
		
		//Get all expected topics value and convert to List
		Set<String> expectedTopicsSet = frontEndMap.keySet();
		List<String> expectedTopic =new ArrayList<String>();
		expectedTopic.addAll(expectedTopicsSet);
		System.out.println("Expected Topics : "+ expectedTopic);
		
		//Asserting actual and expected topics
		Assert.assertEquals(actualTopics, expectedTopic);
		
		//Get list of Actual Chapters Displayed		
		selectDropDownTopic.selectByValue("HTML");
		Thread.sleep(5000);
					
		WebElement dropDownChapter = driver.findElement(By.id("chapter"));
		Select selectDropDownChapter = new Select(dropDownChapter);
		List<String> actualChapters = new ArrayList<>();
		actualChapters = selectDropDownChapter.getOptions().stream().map(ele->ele.getText()).collect(Collectors.toList());
		actualChapters.remove(0);
		System.out.println("Actual Chapters: "+ actualChapters);
		
		////Read Expected Chapters Data from Json file	
		Object htmlVal= frontEndMap.get("HTML");
		List<String> expectedChapters = (List<String>) htmlVal;
		System.out.println("Expected Chapters: "+ expectedChapters);
		
		//Asserting actual and expected chapters
	   Assert.assertEquals(actualChapters, expectedChapters);
}
}

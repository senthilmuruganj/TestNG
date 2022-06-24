package com.flipkart.step;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Rerun implements IRetryAnalyzer {

	int i=1;
	int j=2;
	
	public boolean retry(ITestResult result) {
		
		if(i<j) {
			i++;
			return true;
		}
		System.out.println("failed method name is: "+result.getName());
		return false;
	}
	
	

}

package com.nes.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseCountryAndCountryCodes {

	public ParseCountryAndCountryCodes() {
	}
	
	//Format expected CountryName-->CountryCode
	public Map<String,String> parsedCountryAndCountryCodes(File file, boolean firstLineHeader) throws IOException{
		Map<String,String> result = new HashMap<String, String>();
		if(!file.exists() || file.isDirectory()){
			System.out.println("Incorrect file location");
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try{
		String data=null;
		boolean firstLine = true;
		while((data = reader.readLine())!=null){
			if(firstLine && firstLineHeader){
				firstLine=false;
				continue;
			}
			firstLine=false;
			String[] parsedValues = data.split("\t");
			if(parsedValues.length<2){
				System.out.println(Arrays.asList(parsedValues));
				continue;
			}
			result.put(parsedValues[1].toLowerCase(),parsedValues[0]);
			System.out.println(parsedValues[1]+","+parsedValues[0]);
		}
		return result;
		}
		finally{
			reader.close();
		}
	}
}

package com.nes.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseStateAndStateCodes {

	public ParseStateAndStateCodes() {
		// TODO Auto-generated constructor stub
	}

	public Map<String,String> parseStateAndStateCodes(File file) throws IOException{
		Map<String,String> result = new HashMap<String, String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		try{
		String data=null;
		while((data = reader.readLine())!=null){
			String[] parsedValues = data.split("\t");
			if(parsedValues.length<5 || parsedValues[3].trim().isEmpty() || parsedValues[4].trim().isEmpty()){
				System.out.println(Arrays.asList(parsedValues));
				continue;
			}
			result.put(parsedValues[0].trim().toLowerCase()+","+parsedValues[4].trim().toLowerCase(),parsedValues[3].trim());
		}
		return result;
		}
		finally{
			reader.close();
		}
	}
	
}

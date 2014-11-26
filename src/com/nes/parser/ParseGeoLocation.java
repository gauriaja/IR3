package com.nes.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.nes.db.DatabaseExecutor;
import com.nes.db.entity.LocationData;

public class ParseGeoLocation {
	
	public ParseGeoLocation() {
	}
	
	public static void main(String[] args) throws IOException{
		ParseGeoLocation geoLocation = new ParseGeoLocation();
		File geoLocationFile = geoLocation.convertFileLocationToFile(args[0]);
		File stateCodeFile = geoLocation.convertFileLocationToFile(args[1]);
		File countryCodeFile = geoLocation.convertFileLocationToFile(args[2]);
		if(geoLocation==null || countryCodeFile ==null){
			System.out.println("Incorrect file location");
			System.exit(1);
		}
		geoLocation.parseGeoLocation(geoLocationFile,new ParseCountryAndCountryCodes().parsedCountryAndCountryCodes(countryCodeFile, true),new ParseStateAndStateCodes().parseStateAndStateCodes(stateCodeFile));
	}
	
	private File convertFileLocationToFile(String fileName){
		File file = new File(fileName);
		if(!file.exists() || file.isDirectory()){
			return null;
		}
		return file;
	}
	
	
	
	public void parseGeoLocation(File file,Map<String,String> countryMapping,Map<String,String> stateCodeMapping) throws IOException{
		List<LocationData> locations = new ArrayList<LocationData>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		DatabaseExecutor db = new DatabaseExecutor();
		try{
		String data=null;
		reader.readLine();
		while((data = reader.readLine())!=null){
			String[] parsedValues = data.split(",");
			if(parsedValues.length<7 || parsedValues[5].trim().isEmpty() || parsedValues[6].trim().isEmpty()){
				System.out.println(Arrays.asList(parsedValues));
				continue;
			}
			LocationData location = new LocationData();
			location.setCountryCode(parsedValues[0].trim());
			String countryName = countryMapping.get(parsedValues[0].trim());
			if(countryName==null)
				continue;
			location.setCountry(countryMapping.get(parsedValues[0]).trim());
			location.setPlace(parsedValues[2].trim());
			location.setState(stateCodeMapping.get(parsedValues[0].trim().toLowerCase()+","+parsedValues[3].trim().toLowerCase()));
			StringBuilder cannonicalName = new StringBuilder("");
			cannonicalName.append(parsedValues[1].trim());
			if(location.getState()!=null && !location.getState().trim().isEmpty())
				cannonicalName.append(" ,"+parsedValues[1].trim());
			cannonicalName.append(" ,"+location.getCountry()+" ,"+location.getCountryCode());
			location.setCannonicalName(cannonicalName.toString());
			location.setLatitude(Double.valueOf(parsedValues[5]));
			location.setLongitude(Double.valueOf(parsedValues[6]));
			locations.add(location);
			//System.out.println(String.valueOf(countryMapping.get(parsedValues[0]))+","+parsedValues[2]+","+parsedValues[9]+","+parsedValues[10]);
		}
		db.createLocationsTable();
		db.bulkInsertIntoLocationsTable(locations);
		}
		finally{
			reader.close();
		}
	}
}

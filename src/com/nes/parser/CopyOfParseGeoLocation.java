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

public class CopyOfParseGeoLocation {
	
	public CopyOfParseGeoLocation() {
	}
	
	public static void main(String[] args) throws IOException{
		CopyOfParseGeoLocation geoLocation = new CopyOfParseGeoLocation();
		File geoLocationFile = geoLocation .convertFileLocationToFile(args[0]);
		File countryCodeFile = geoLocation .convertFileLocationToFile(args[1]);
		if(geoLocation==null || countryCodeFile ==null){
			System.out.println("Incorrect file location");
			System.exit(1);
		}
		geoLocation.parseGeoLocation(geoLocationFile,new ParseCountryAndCountryCodes().parsedCountryAndCountryCodes(countryCodeFile, true));
	}
	
	private File convertFileLocationToFile(String fileName){
		File file = new File(fileName);
		if(!file.exists() || file.isDirectory()){
			return null;
		}
		return file;
	}
	
	public void parseGeoLocation(File file,Map<String,String> countryMapping) throws IOException{
		List<LocationData> locations = new ArrayList<LocationData>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		DatabaseExecutor db = new DatabaseExecutor();
		try{
		String data=null;
		while((data = reader.readLine())!=null){
			String[] parsedValues = data.split("\t");
			if(parsedValues.length<11 || parsedValues[9].trim().isEmpty() || parsedValues[9].trim().isEmpty()){
				System.out.println(Arrays.asList(parsedValues));
				continue;
			}
			LocationData location = new LocationData();
			location.setCountryCode(parsedValues[0].trim());
			String countryName = countryMapping.get(parsedValues[0].trim());
			if(countryName==null)
				continue;
			location.setCountry(countryMapping.get(parsedValues[0]).trim());
			location.setPinCode(parsedValues[1].trim());
			location.setPlace(parsedValues[2].trim());
			location.setState(parsedValues[3].trim());
			location.setProvince(parsedValues[5].trim());
			location.setLatitude(Double.valueOf(parsedValues[9]));
			location.setLongitude(Double.valueOf(parsedValues[10]));
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

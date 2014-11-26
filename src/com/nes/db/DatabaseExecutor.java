package com.nes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.nes.db.entity.LocationData;
import com.nes.db.entity.User;

public class DatabaseExecutor {

	private static final String DATASOURCE_CONTEXT = "java:/comp/env/jdbc/adsDb";
	private String driverName;
	private String connectionString; 
	
	public DatabaseExecutor() {
	}
	
	private Connection getConnection(String driverName,String connectionString) throws ClassNotFoundException, SQLException{
	  	this.connectionString=connectionString;
	  	this.driverName=driverName;
        String sDriverName = "org.sqlite.JDBC";
        Class.forName(sDriverName);
        String sTempDb = "//E:/Dev/ads.db";
        String sJdbc = "jdbc:sqlite";
        String sDbUrl = sJdbc + ":" + sTempDb;
        return DriverManager.getConnection(sDbUrl);   
	}
	
//	private Connection getConnection(String driverName,String connectionString) throws ClassNotFoundException, SQLException{
//		try{
//		Context initialContext = new InitialContext();
//	    DataSource datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
//	    return datasource.getConnection();
//		}catch(Exception e){
//			return null;
//		}
//	}
	
	public List<LocationData> getLocationData(String text){
		List<LocationData> results = new ArrayList<LocationData>();
		Connection conn =null;
		try {
			conn =getConnection(driverName, connectionString) ;
            String selectFromLocations ="SELECT location_id,city, state, country,country_code,cannonical_name ,latitude,longitude from Locations where cannonical_name like '%?%'";
            PreparedStatement stmt = conn.prepareStatement(selectFromLocations);
            stmt.setString(1, text);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
            	LocationData temp = new LocationData();
                temp.setLocationId(rs.getInt("location_id"));
                temp.setState(rs.getString("state"));
                temp.setPlace(rs.getString("city"));
                temp.setCountry(rs.getString("country"));
                temp.setCountryCode(rs.getString("country_code"));
                temp.setCannonicalName(rs.getString("cannonical_name"));
                temp.setLatitude(rs.getDouble("latitude"));
                temp.setLongitude(rs.getDouble("longitude"));
                results.add(temp);
            }
            rs.close();
            stmt.close();
		}
		catch(Exception ignore){
		} finally {
            try { conn.close(); } catch (Exception ignore) {}
        }
		return results;
	}
	
	public boolean createLocationsTable(){
		Connection conn =null;
		boolean success = true;
		try {
			conn =getConnection(driverName, connectionString) ;
            Statement stmt = conn.createStatement();
           	String sMakeTable = "CREATE TABLE if not exists Locations (location_id INTEGER PRIMARY KEY, city TEXT, state TEXT, country TEXT, country_code TEXT, cannonical_name TEXT, latitude REAL,longitude REAL)";
            stmt.executeUpdate( sMakeTable );
            stmt.close();
        } catch(Exception ignoreMe){
        	success=false;
        }
		finally {
            try { if(conn!=null) conn.close(); } catch (Exception ignore) {}
        }
		return success;
	}
	
	public boolean createUsersTable(){
		Connection conn =null;
		boolean success = true;
		try {
			conn =getConnection(driverName, connectionString) ;
            Statement stmt = conn.createStatement();
           	String sMakeTable = "CREATE TABLE if not exists Users (id INTEGER PRIMARY KEY, user_name UNIQUE TEXT,first_name TEXT, last_name TEXT,min_age INTEGER, max_age INTEGER)";
            stmt.executeUpdate( sMakeTable );
            stmt.close();
        } catch(Exception ignoreMe){
        	success=false;
        }
		finally {
            try { if(conn!=null) conn.close(); } catch (Exception ignore) {}
        }
		return success;
	}
	
	public boolean insertIntoUsersTable(User temp){
		Connection conn =null;
		boolean success = true;
		try {
			conn =getConnection(driverName, connectionString) ;
            String insertIntoUsers = "INSERT INTO Users(user_name,first_name,last_name,min_age,max_age) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(insertIntoUsers);
        	stmt.setString(1, temp.getUserName());
        	stmt.setString(2, temp.getFirstName());
        	stmt.setString(3, temp.getLastName());
        	stmt.setDouble(4, temp.getMinAge());
        	stmt.setDouble(5, temp.getMaxAge());
        	stmt.addBatch();
            stmt.executeBatch();
            stmt.close();
		}
		catch(Exception e){
			success=false;
		} finally {
            try { conn.close(); } catch (Exception ignore) {}
        }
		return success;
	}
	
	public boolean bulkInsertIntoLocationsTable(List<LocationData> data){
		Connection conn =null;
		boolean success = true;
		try {
			conn =getConnection(driverName, connectionString) ;
			conn.setAutoCommit(false);
            String insertIntoLocations = "INSERT INTO Locations(city,state, country,country_code,cannonical_name,latitude,longitude) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(insertIntoLocations);
            int count=1;
            int totalrecords=0;
            for(LocationData temp : data){
            	int i=1;
            	stmt.setString(i++, temp.getPlace() );
            	stmt.setString(i++, temp.getState());
            	stmt.setString(i++, temp.getCountry());
            	stmt.setString(i++, temp.getCountryCode());
            	stmt.setString(i++, temp.getCannonicalName());
            	stmt.setDouble(i++, temp.getLatitude());
            	stmt.setDouble(i++, temp.getLongitude());
            	stmt.addBatch();
            	if(count==50000){
            		totalrecords+=count;
            		stmt.executeBatch();
            		stmt.clearBatch();
            		System.out.println("Records Left : "+ (data.size() -totalrecords)) ;
            		count=0;
            	}
            	count++;
            }
            if(count!=0)
            	stmt.executeBatch();
            stmt.close();
            conn.commit();
		}
		catch(Exception e){
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (SQLException ignore) {
			}
			success=false;
		} finally {
            try { conn.close(); } catch (Exception ignore) {}
        }
		return success;
	}
	
	public boolean insertIntoLocationsTable(LocationData data){
		List<LocationData> inputData = new ArrayList<LocationData>();
		inputData.add(data);
		return bulkInsertIntoLocationsTable(inputData);
	}
}

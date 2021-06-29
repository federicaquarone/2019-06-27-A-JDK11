package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getGategorie(){
		String sql="SELECT distinct offense_category_id "
				+ "FROM events e "
				+ "GROUP BY e.offense_category_id ASC ";
		List<String> result= new ArrayList<String>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(res.getString("offense_category_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<Integer> getAnno(){
		String sql="SELECT DISTINCT Year(e.reported_date) as anno "
				+ "FROM events e "
				+ "GROUP BY Year(e.reported_date) asc";
		
		List<Integer> result= new ArrayList<Integer>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(res.getInt("anno"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getVertici(String categoria, Integer anno){
		String sql="SELECT DISTINCT e.offense_type_id "
				+ "FROM events e "
				+ "WHERE YEAR(e.reported_date)=? AND e.offense_category_id=? ";
		List<String> result= new ArrayList<String>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setString(2, categoria);
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(res.getString("e.offense_type_id"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Arco> getArchi(String categoria, int anno){
		String sql="SELECT e1.offense_type_id as tipo1, e2.offense_type_id as tipo2, COUNT(DISTINCT e1.district_id) AS peso "
				+ "FROM events e1, events e2 "
			    + "WHERE e1.offense_type_id>e2.offense_type_id "
				+ "AND YEAR(e1.reported_date)=? AND YEAR(e1.reported_date)= YEAR(e2.reported_date) "
				+ "AND e1.offense_category_id=? "
				+ "AND e1.offense_category_id=e2.offense_category_id "
				+ "and e1.district_id=e2.district_id "
				+ "GROUP BY e1.offense_type_id,e2.offense_type_id";
		List<Arco> result= new ArrayList<Arco>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setString(2, categoria);
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(new Arco(res.getString("tipo1"), res.getString("tipo2"), res.getInt("peso")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}
}

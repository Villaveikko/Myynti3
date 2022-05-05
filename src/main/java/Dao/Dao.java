package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Asiakas;

public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Myynti.sqlite";

 
	private Connection yhdista(){
	    	Connection con = null;    	
	    	String path = System.getProperty("catalina.base");    	
	    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); 
	    	String url = "jdbc:sqlite:"+path+db;    	
	    	try {	       
	    		Class.forName("org.sqlite.JDBC");
		        con = DriverManager.getConnection(url);	
		        System.out.println("Yhteys avattu.");
		     }catch (Exception e){	
		    	 System.out.println("Yhteyden avaus epäonnistui.");
		        e.printStackTrace();	         
		     }
		     return con;
		}
		
		public ArrayList<Asiakas> listaaKaikki(){
			ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
			sql = "SELECT * FROM asiakkaat";       
			try {
				con=yhdista();
				if(con!=null){ 
					stmtPrep = con.prepareStatement(sql);        		
	        		rs = stmtPrep.executeQuery();   
					if(rs!=null){ 				
						while(rs.next()){
							Asiakas asiakas = new Asiakas();
							asiakas.setEtunimi(rs.getString(1));
							asiakas.setSukunimi(rs.getString(2));
							asiakas.setSposti(rs.getString(3));	
							asiakas.setPuhelin(rs.getString(4));	
							asiakas.add(asiakas);
						}					
					}				
				}	
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return Asiakas;
		}
		
		public ArrayList<Asiakas> listaaKaikki(String hakusana){
			ArrayList<Asiakas> asiakas = new ArrayList<Asiakas>();
			sql = "SELECT * FROM asiakkaat WHERE etunimi LIKE ? or sukunimi LIKE ? or sposti LIKE ?";      
			try {
				con=yhdista();
				if(con!=null){ 
					stmtPrep = con.prepareStatement(sql);
					stmtPrep.setString(1, "%" + hakusana + "%");
					stmtPrep.setString(2, "%" + hakusana + "%");   
					stmtPrep.setString(3, "%" + hakusana + "%");  
	        		rs = stmtPrep.executeQuery();   
					if(rs!=null){ 					
						while(rs.next()){
							Asiakas asiakas = new Asiakas();
							asiakas.setEtunimi(rs.getString(1));
							asiakas.setSukunimi(rs.getString(2));
							asiakas.setSposti(rs.getString(3));	
							asiakas.setPuhelin(rs.getString(4));	
							asiakas.add(asiakas);
						}					
					}				
				}	
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return asiakas;
		}
		
		public boolean lisaaAsiakas(Asiakas asiakas){
			boolean paluuArvo=true;
			sql="INSERT INTO asiakkaat VALUES(?,?,?,?)";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, asiakas.getEtunimi());
				stmtPrep.setString(2, asiakas.getSukunimi());
				stmtPrep.setString(3, asiakas.getSposti());
				stmtPrep.setString(4, asiakas.getPuhelin());
				stmtPrep.executeUpdate();
		        con.close();
			} catch (Exception e) {				
				e.printStackTrace();
				paluuArvo=false;
			}				
			return paluuArvo;
		}
		public boolean poistaAsiakas(String Etunimi, String Sukunimi){ 
			boolean paluuArvo=true;
			sql="DELETE FROM asiakkaat WHERE etunimi AND sukunimi=?";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, etunimi);
				stmtPrep.setString(2, sukunimi);
				stmtPrep.executeUpdate();
		        con.close();
			} catch (Exception e) {				
				e.printStackTrace();
				paluuArvo=false;
			}				
			return paluuArvo;
		}	
		
		public Asiakas etsiAsiakas(String etunimi, String sukunimi) {
			Asiakas asiakas = null;
			sql = "SELECT * FROM asiakkaat WHERE etunimi AND sukunimi=?";       
			try {
				con=yhdista();
				if(con!=null){ 
					stmtPrep = con.prepareStatement(sql); 
					stmtPrep.setString(1, etunimi);
					stmtPrep.setString(2, sukunimi);
	        		rs = stmtPrep.executeQuery();  
	        		if(rs.isBeforeFirst()){ 
	        			rs.next();
	        			asiakas = new Asiakas();        			
	        			asiakas.setEtunimi(rs.getString(1));
						asiakas.setSukunimi(rs.getString(2));
						asiakas.setSposti(rs.getString(3));	
						asiakas.setPuhelin(rs.getString(4));       			      			
					}        		
				}	
				con.close();  
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return asiakas;		
		}
		
		public boolean muutaAsiakas(Asiakas asiakas, String etunimi, String sukunimi){
			boolean paluuArvo=true;
			sql="UPDATE asiakkaat SET etunimi=?, sukunimi=?, sposti=?, puhelin=? WHERE etunimi AND sukunimi=?";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, asiakas.getEtunimi());
				stmtPrep.setString(2, asiakas.getSukunimi());
				stmtPrep.setString(3, asiakas.getSposti());
				stmtPrep.setString(4, asiakas.getPuhelin());
				stmtPrep.setString(5, etunimi);
				stmtPrep.setString(6, sukunimi);
				stmtPrep.executeUpdate();
		        con.close();
			} catch (Exception e) {				
				e.printStackTrace();
				paluuArvo=false;
			}				
			return paluuArvo;
		}
	
		
	}



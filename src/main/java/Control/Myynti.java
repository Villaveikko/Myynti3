package Control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Model.dao;
import Model.Asiakas;

@WebServlet("/myynti")
public class Myynti extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public Myynti() {
        super();
        System.out.println("Asiakkaat. Asiakkaat()");
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo();				
		System.out.println("polku: "+pathInfo);		
		String strJSON="";
		ArrayList<Asiakas> asiakkaat;
		dao dao = new dao();
		if(pathInfo==null) {  
			asiakkaat = dao.listaaKaikki();
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();	
		}else if(pathInfo.indexOf("haeyksi")!=-1) {		
			int asiakas_id = Integer.parseInt(pathInfo.replace("/haeyksi/", "")); 		
			Asiakas asiakas = dao.etsiAsiakas(asiakas_id);
			if(asiakas==null){ 
				strJSON = "{}";
			}else{	
				JSONObject JSON = new JSONObject();
				JSON.put("asiakas_id", asiakas.getAsiakas_id());
				JSON.put("etunimi", asiakas.getEtunimi());
				JSON.put("sukunimi", asiakas.getSukunimi());
				JSON.put("puhelin", asiakas.getPuhelin());	
				JSON.put("sposti", asiakas.getSposti());	
				strJSON = JSON.toString();
			}			
		}else{ 
			String hakusana = pathInfo.replace("/", "");	
			asiakkaat = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();				
		}	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);			
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Myynti.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); 		
		Asiakas asiakas = new Asiakas();
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		asiakas.setPuhelin(jsonObj.getInt("puhelin"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		dao dao = new dao();			
		if(dao.lisaaAsiakas(asiakas)){ 
			out.println("{\"response\":1}");  
		}else{
			out.println("{\"response\":0}");  
		}		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");		
		JSONObject jsonObj = new JsonStrToObj().convert(request); 			
		Asiakas asiakas = new Asiakas();	
		asiakas.setAsiakas_id(Integer.parseInt(jsonObj.getString("asiakas_id"))); 
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		dao dao = new dao();			
		if(dao.muutaAsiakas(asiakas)) { 
			out.println("{\"response\":1}");  
		}else {
			out.println("{\"response\":0}");  	
		} 		
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");	
		String pathInfo = request.getPathInfo();		
		int asiakas_id = Integer.parseInt(pathInfo.replace("/", "")); 	
		dao dao = new dao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		    
		if(dao.poistaAsiakas(asiakas_id)){ 
			out.println("{\"response\":1}"); 
		}else {
			out.println("{\"response\":0}"); 
		}		
	}
	
}
	
	





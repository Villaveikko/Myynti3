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

import Dao.Dao;
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
		String myynti = request.getPathInfo();				
		System.out.println("Asiakkaat: "+myynti);	
		String myynti="";
		if(pathInfo!=null) {		
			myynti = pathInfo.replace("/", "");
		}		
		Dao dao = new Dao();
		ArrayList<Asiakas> asiakkaat = dao.listaaKaikki(myynti);
		System.out.println(asiakkaat);
		String strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Myynti.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); 		
		Asiakas asiakas = new Asiakas();
		Asiakas.setEtunimi(jsonObj.getString("etunimi"));
		Asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		Asiakas.setSposti(jsonObj.getString("sposti"));
		Asiakas.setPuhelin(jsonObj.getInt("puhelin"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.lisaaAsiakas(asiakas)){ 
			out.println("{\"response\":1}");  
		}else{
			out.println("{\"response\":0}");  
		}		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Myynti.doPut()");		
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Myynti.doDelete()");	
		String pathInfo = request.getPathInfo();			
		System.out.println("myynti: "+pathInfo);
		String poistettavaRekno = pathInfo.replace("/", "");		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.poistaAsiakas(poistaAsiakas)){ 
			out.println("{\"response\":1}");  
		}else{
			out.println("{\"response\":0}");  
		}
	}
}
	
	





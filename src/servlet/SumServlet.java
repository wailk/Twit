/*package servlet;

import java.io.IOException;
//import java.util.Map;
import services.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;

//@WebServlet({"/OperationServlet","/Operation"});
public class OperationServlet extends HttpServlet implements Servlet  {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		
	    double result = 0;
		//Map<String,String[]> pars = req.getParameterMap();
		if( req.getParameter("a") !="" && req.getParameter("b")!= ""){ 
			Operation op = new Operation();
			String valA = req.getParameter("a");
			String valB = req.getParameter("b");
			PrintWriter out = resp.getWriter();
			out.print(valA);
			out.print("\n" +valB);
			
			result = op.calcul(Double.parseDouble(valA),Double.parseDouble(valB));
			out.print("\nresultat =" +result);
			
			
			
		}
	}}
		/*
			PrintWriter out = resp.getWriter();
			resp.setContentType("plain/text");
			out.print("<head> <title> rass </title></head>");
			out.print("<body>");
			out.print("Params: a = " + valA );
			out.print("Resultat egal = " + result);
		    out.print("</body>");
	}
	
	
	
}
*/

package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.Operation;

public class SumServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		PrintWriter out = resp.getWriter();
		try{
		String valeurA = req.getParameter("a");
		String valeurB = req.getParameter("b");
		String o = req.getParameter("op");
		double a = Double.parseDouble(valeurA);
		double b = Double.parseDouble(valeurB);
		Operation op = new Operation();
		
		out.print("resultat = " + op.Calcul(a,b,o));
		}
		catch(Exception e){
			out.print("Erreur sur les parametres");
			
		}
	}
}

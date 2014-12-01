package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import services.authentification.Comments;

/**
 * Servlet implementation class CreateUser
 */
public class SearchCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

   /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//JSONObject comments = Comments.search(request.getParameter("key"), request.getParameter("com"), request.getParameter("friend"));
		JSONObject comments = null;
		try {
			comments = Comments.search(request.getParameter("key"), request.getParameter("query"), request.getParameter("date"));
			
			//comments = Comments.search(request.getParameter("key"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().println("hello " +comments.toString());
	//Comments.search(request.getParameter("key"), request.getParameter("com"), request.getParameter("friend"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
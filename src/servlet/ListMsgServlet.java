package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import services.authentification.Comments;

public class ListMsgServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String id = req.getParameter("uid");
			String username = req.getParameter("login");
			String nbMessage = req.getParameter("maxr");
			String offset = req.getParameter("off");
			String last = req.getParameter("mid");
			

			JSONObject json = new JSONObject() ;
			try {
				json = Comments.listMessages(id,username,nbMessage,offset,last);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MongoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}
}
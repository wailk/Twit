package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import services.authentification.Comments;

public class LikeCommentServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String comment_id = req.getParameter("comment_id");
			String liker_id = req.getParameter("liker_id");
			
			

			JSONObject json = new JSONObject() ;
			
				try {
					
					json = Comments.like(comment_id,liker_id);
					
					
				} catch (MongoException | SQLException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			
			resp.setContentType("text/plain");
			resp.getWriter().print(json);
	}
}
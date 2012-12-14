package edu.trafficsim.framework.servelet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.trafficsim.framework.actions.Action;
import edu.trafficsim.framework.actions.DemoAction;
import edu.trafficsim.framework.model.Model;

/**
 * Servlet implementation class DemoServlet
 */
@WebServlet(name = "trafficsim", urlPatterns = { "/trafficsim" })
public class DefaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DefaultServlet() {
        super();
    }

    public void init() throws ServletException {
    	Model model = new Model(getServletConfig());
    	
    	Action.add(new DemoAction(model));
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = performTheAction(request);
        sendToNextPage(nextPage,request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String performTheAction(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		return Action.perform(action,request);
    }

	private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
			return;
		}
		
		// response.sendRedirect("http://"+host+port+nextPage);
		
		RequestDispatcher d = request.getRequestDispatcher("/"+nextPage);
		d.forward(request,response);
	}

	private String getActionName(String path) {
	    int slash = path.lastIndexOf('/');
	    return path.substring(slash+1);
	}
}

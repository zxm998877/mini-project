

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String id = request.getParameter("id");
		String pin = request.getParameter("pin");
		String payment = request.getParameter("topup");
		request.setAttribute("id", id);
		request.setAttribute("payment", payment);
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		session.setAttribute("payment", payment);
	        	//call web service from Bank A to approve payment
		        if (isApproved(id,payment)) {
		        	processOTP(id);
		        	request.getRequestDispatcher("otp.jsp").forward(request, response);
		        }else {
		        	request.setAttribute("errorMessage", "Error: Not enough balance!");
		        	request.getRequestDispatcher("mobile_topup.jsp").forward(request, response);
		        	return;
		        }
	}

	public boolean isApproved(String id,String payment) {
		boolean paymentApproved = false;
		try {
		// Create a URL object for the web service endpoint
        URL url = new URL("http://localhost:8080/Bank_A/checkBalance?acc="+id+"&payment="+payment);

        // Open a connection to the web service
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        con.setRequestMethod("POST");

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
       
        String c = content.toString();
        paymentApproved = Boolean.parseBoolean(c);
        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return paymentApproved;
        
	}
	public void processOTP(String id) {
		try {
			// Create a URL object for the web service endpoint
	        URL url = new URL("http://localhost:8080/Bank_A/sendOTP?id="+id);

	        // Open a connection to the web service
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        // Set the request method to POST
	        con.setRequestMethod("POST");

	        // Read the response
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer content = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	       
	       
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
}

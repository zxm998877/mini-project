package com.otp.ejb;
import javax.ejb.*;
import javax.ws.rs.*;

@Path("/")
public class handleOtpEJB {

	@EJB
	private OTPSessionBean otpSession;
	
	@POST
	@Path("/sendOTP")
	public void processOTP(@QueryParam("id")String id) {
		String sotp = otpSession.getOTPFromCloud();
		String motp = sotp.substring(1, sotp.length()-1);
		int otp = Integer.valueOf(motp);
		//otpSession.sendEmail();
		otpSession.storeOTP(otp,id);
		//return otp;
	}
	
	@POST
	@Path("/verifyOTP")
	@Produces("text/plain")
	public boolean verifyOTP(@QueryParam("id")String id, @QueryParam("otp")int otp) {
		boolean is_ValidOTP = otpSession.verifyOTP(id,otp);
		return is_ValidOTP;
		
	}
}


public class OTP {

	public static String generateOTP() {
		int randomNo = (int)(Math.random()*9000)+1000;
		String otp = String.valueOf(randomNo);
		return otp;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String otpStr = generateOTP();
		System.out.println(otpStr);
	}

}

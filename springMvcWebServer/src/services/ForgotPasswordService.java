package services;

public interface ForgotPasswordService {

	public void sendEmail(String email,String url,String country);
	
	public boolean updateAccountByEmailId(String id, String newPassword);
	
}

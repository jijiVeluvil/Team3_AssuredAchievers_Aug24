package utilities;

public class APIConstant {
	public static String POST="POST";
	public static String GET="GET";
	public static String DELETE="DELETE";
	public static String PUT="PUT";
	
	public static String BASE_URL="https://dietician-july-api-hackathon-80f2590665cc.herokuapp.com/dietician";
	
	
	
	public static final String USER_LOGIN_ENDPOINT = "/login";
	public static final String CREATE_DIETICIAN_ENDPOINT = "/dietician";
	public static final String CREATE_Patient_ENDPOINT = "/patient";
	public static final String UPDATE_Patient_ENDPOINT = "/patient/newReports";
	public static final String viewFile_endpoint = "/patient/testReports/viewFile";
	public static final String CREATE_Patient_TestReport_ENDPOINT = "/patient/testReports";
	public static final String USER_LOGOUT_ENDPOINT = "/logoutdietician";
	
	public static  final String GET_ALL_MORBIDITY = "/morbidity";
	public static final  String GET_MORBIDITY_BY_TESTNAME = "/morbidity/{morbidityName}";
	public static final String adminToken ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUZWFtMy5hZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE3MjM4NTMyNzAsImV4cCI6MTcyMzg4MjA3MH0.CAA7-cTUiUxh1Jm-n9EQh4lRCQX7AYI-Gwhbxam1MWMbIWt6r2gtEIDRBYHvdVn_50D2sYs2I2ZHPWF_7s0Wmg";
	public static final String dieticianToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTaWFAZ21haWwuY29tIiwiaWF0IjoxNzIzODUzMzgyLCJleHAiOjE3MjM4ODIxODJ9.dMr4zeBDZhK6ws-xsKJ8zbcJxvKc5sd_TgpzD-NV-qyME3PJ5kyH2nMJY_It3WikW84-ablydYnDTESzurfdVg";
}

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
	public static final String GET_ALL_MORBIDITY_INVALID ="/morbidityin";
	
}

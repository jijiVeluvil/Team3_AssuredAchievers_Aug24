package DietitianPojo;

import java.io.File;

public class PatientDataFieldsVo {

	private String patientInfo;
	private String vitals;
	private File reportFile;
	public String getPatientInfo() {
		return patientInfo;
	}
	public void setPatientInfo(String patientInfo) {
		this.patientInfo = patientInfo;
	}
	public String getVitals() {
		return vitals;
	}
	public void setVitals(String vitals) {
		this.vitals = vitals;
	}
	public File getReportFile() {
		return reportFile;
	}
	public void setReportFile(File reportFile) {
		this.reportFile = reportFile;
	}


}

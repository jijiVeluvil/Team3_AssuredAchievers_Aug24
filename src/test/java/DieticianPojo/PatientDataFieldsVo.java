package DieticianPojo;

import java.io.File;

import lombok.Data;

@Data
public class PatientDataFieldsVo {

	private String patientInfo;
	private String vitals;
	private File reportFile;

}

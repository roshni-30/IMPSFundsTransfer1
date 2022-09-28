package com.impsfundtransfer.Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_imps_res")
public class ClientImpsResponse implements Serializable {
	@Id
	private Long checksum;
	@Id
	private Long requestId;

	private String responseCode;

	private Boolean isSuccess;

	private String failureReason;

	private String retrivalReferenceNo;

	private String transactionDate;
	
	private String BeneficiaryName;

}

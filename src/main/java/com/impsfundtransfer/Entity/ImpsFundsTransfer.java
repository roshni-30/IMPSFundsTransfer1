package com.impsfundtransfer.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UniqPk.class)
@Table(name = "imps_fund_transfer")
public class ImpsFundsTransfer {

	@Id
	@GenericGenerator(name = "custuniq_ref", strategy = "com.impsfundtransfer.Entity.CustomGenId")
	@GeneratedValue(generator = "custuniq_ref")
	private Long custuniqref;

	@Id
	@GenericGenerator(name = "check_sum", strategy = "com.impsfundtransfer.Entity.CustomIdGen")
	@GeneratedValue(generator = "check_sum")
	private Long checkSum;
	
    @Column
	private String beneAccNum;
	@Column
	private String beneName;
	@Column
	private String corpCode;
	@Column
	private String corpAccNum;
	@Column
	private String valueDate;
	@Column
	private String txnAmount;
	@Column
	private String bene_Code;
	@Column
	private String requestUUID;
	@Column
	private String serviceRequestId;
	@Column
	private String serviceRequestVersion;
	@Column
	private String channelId;

}
package com.impsfundtransfer.Service;

import java.util.Map;
import com.sun.jersey.api.client.Client;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.impsfundtransfer.Entity.ClientImpsResponse;
import com.impsfundtransfer.Entity.ImpsFundsTransfer;
import com.impsfundtransfer.Repository.ImpsFundsTransferRepository;
import com.sun.jersey.api.client.WebResource;

@Service
public class ImpsFundsTransferService {

	@Autowired
	private ImpsFundsTransferRepository repository;

	

	

	
	  public String ImpsTxn( ImpsFundsTransfer impsfund) {
		  repository.save(impsfund); 
		return "success";
	  
	  /*Map<String, Object> map = new HashMap<String, Object>(); map.put("checksum",
	  impsfund.getChecksum());
	  System.out.println("checksum"+impsfund.getChecksum());
	  
		
		  map.put("requestId", impsfund.getRequestId()); map.put("beneficiaryIFSC",
		  impsfund.getBeneficiaryIFSC()); map.put("beneficiaryAccountNo",
		  impsfund.getBeneficiaryAccountNo()); map.put("amount", impsfund.getAmount());
		  map.put("remarks", impsfund.getRemarks()); map.put("txnInitChannel",
		  impsfund.getTxnInitChannel()); map.put("beneficiaryName",
		  impsfund.getBeneficiaryName());
		 
	  JSONObject object = new JSONObject(map); String Jsonstring =
	  object.toString(); System.out.println("json"+Jsonstring);
	  
	  repository.save(impsfund); System.out.println("Query generated==========:" +
	  impsfund);
	  
	  ClientImpsResponse response = null;
	  
	  Client client = Client.create(); System.out.println("line 41"); WebResource
	  resource = client.resource(
	  "https://apiportal.axisbank.com/gateway/openapi/v1/imps/funds-transfer");
	  response = resource.header("content-type",
	  "application/json").header("accept", "application/json")
	  .header("x-axis-test-id", "1234").header("x-ibm-client-id",
	  "6958adf8d6ddf22e98826d7a46efebf0") .header("x-ibm-client-secret",
	  "049c3e78df63d8f190f35f889684f7ce") .post(ClientImpsResponse.class,
	  Jsonstring);
	  
	  System.out.println("client hitted===============:" + response);
		
		  ClientImpsResponse clientImpsResponse = new ClientImpsResponse();
		  clientImpsResponse.setBeneficiaryName(impsfund.getBeneficiaryName());
		  clientImpsResponse.setChecksum(impsfund.getChecksum());
		  clientImpsResponse.getFailureReason();
		  clientImpsResponse.setRequestId(impsfund.getRequestId());
		  clientImpsResponse.getResponseCode(); clientImpsResponse.getIsSuccess();
		  clientImpsResponse.getRetrivalReferenceNo();
		  clientImpsResponse.getTransactionDate();
		 
	  //System.out.println("response==============:" + clientImpsResponse);
	  
	  return impsfund;
	  }

       public void ImpsTxns( ImpsFundsTransferReq impsfund) {
    	   repository.save(impsfund);
		
		
	}

	public String addImpsTxn( ImpsFundsTransferReq impsfund) {
		
		repository.save(impsfund);
		return "";
	}
	 
	

}
*/
}
}
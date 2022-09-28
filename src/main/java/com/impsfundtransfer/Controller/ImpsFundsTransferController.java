package com.impsfundtransfer.Controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.xml.security.utils.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.impsfundtransfer.Entity.ImpsFundsTransfer;
import com.impsfundtransfer.Service.ImpsFundsTransferService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@RestController
public class ImpsFundsTransferController {

	@Autowired
	private ImpsFundsTransferService service;

	@PostMapping("/saveImpsTxnaxis")
	public String addImpsTxn(@RequestBody ImpsFundsTransfer impsfund) throws Exception {

		Map<String, Object> map = new LinkedHashMap<>();
		Map<String, Map<String, Object>> map1 = new LinkedHashMap<>();
		map.put("TransferPaymentRequest", map1);

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("requestUUID", impsfund.getRequestUUID());
		map2.put("serviceRequestId", impsfund.getServiceRequestId());
		map2.put("serviceRequestVersion", impsfund.getServiceRequestVersion());
		map2.put("channelId", impsfund.getChannelId());

		map1.put("SubHeader", map2);

		Map<String, Object> map3 = new LinkedHashMap<String, Object>();
		map3.put("channelId", impsfund.getChannelId());
		map3.put("corpCode", impsfund.getCorpCode());
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		Map<String, Object> map4 = new HashMap<>();
		map4.put("checkSum", impsfund.getCheckSum());
		System.out.println("checksum===" + impsfund.getCheckSum());
		map4.put(" beneAccNum", impsfund.getBeneAccNum());
		map4.put("beneName", impsfund.getBeneName());
		map4.put("custuniqref", impsfund.getCustuniqref());
		map4.put("corpAccNum", impsfund.getCorpAccNum());
		map4.put("valueDate", impsfund.getValueDate());
		map4.put("txnAmount", impsfund.getTxnAmount());
		map4.put("bene_code", impsfund.getBene_Code());
		map4.put("bene_code", impsfund.getBene_Code());

		map3.put("PaymentDetails", map4);
		list.add(map4);
		System.out.println("list==============="+list);

		map1.put("TransferPaymentRequestBody", map3);
       // System.out.println("print======= " + map);

		JSONObject object = new JSONObject(map);
		String Jsonstring = object.toString();
		// System.out.println("object" + object);

		System.out.println("json" + Jsonstring);
		String input = aes128Encrypt(Jsonstring);
		System.out.println("encrypted message -->" + input);

		//System.out.println("Query generated==========:" + impsfund);

		// ClientImpsResponse response = null;
		ClientResponse response4 = null;
		String decOutput = "decryption fail";
		String requestMethod4 = "POST";
		String url = "https://sakshamuat.axisbank.co.in/gateway/api/txb/v1/payments/transfer-payment";

		try {
			ClientConfig clientConfig4 = new DefaultClientConfig();
			clientConfig4.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client4 = Client.create(clientConfig4);
			client4.setConnectTimeout(5000 * 60 * 30);
			client4.setReadTimeout(5000 * 60 * 30);
			 

			WebResource resource4 = client4.resource(url);
			if (requestMethod4.equalsIgnoreCase("POST")) 
				response4 = resource4.header("Content-Type", "application/json").header("Accept", "application/json")
						.header("X-IBM-Client-Id", "6958adf8d6ddf22e98826d7a46efebf0")
						.header("X-IBM-Client-Secret", "049c3e78df63d8f190f35f889684f7ce").post(ClientResponse.class,input);
              
			String output = response4.getEntity(String.class);
			decOutput = aes128Decrypt(output);
			JSONObject jsonObject = new JSONObject(decOutput);
			System.out.println("json formate Decrypted Response " + jsonObject);
			System.out.println("decOutput==============:" + decOutput);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("client hitted===============:" + response);
		/*
		 * ClientImpsResponse clientImpsResponse = new ClientImpsResponse();
		 * clientImpsResponse.setBeneficiaryName(impsfund.getBeneficiaryName());
		 * clientImpsResponse.setChecksum(impsfund.getChecksum());
		 * clientImpsResponse.getFailureReason();
		 * clientImpsResponse.setRequestId(impsfund.getRequestId());
		 * clientImpsResponse.getResponseCode(); clientImpsResponse.getIsSuccess();
		 * clientImpsResponse.getRetrivalReferenceNo();
		 * clientImpsResponse.getTransactionDate();
		 * 
		 * System.out.println("response==============:" + clientImpsResponse);
		 */
		return decOutput;
	}

	
	private static final String ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHAM = "AES/CBC/PKCS5PADDING";
	private static final String KEY = "AB46240EB683A44F38288E6D6FB913D3";

	// Encrypt request
	public String aes128Encrypt(String plainText) throws Exception {

		byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A, (byte) 0x8E, 0x12, 0x39,
				(byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		/** Generate a secret key from the hex string as key */
		SecretKeySpec skeySpec = getSecretKeySpecFromHexString(ALGORITHM, KEY);
		/** Creating a cipher instance with the algorithm and padding */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHAM);
		SecureRandom secureRandom = new SecureRandom();
		// String keyFile = "/home/ec2-user/key/first.cer";
		String keyFile = "C:\\keys\\Second.cer";
		InputStream inStream = new FileInputStream(keyFile);
		System.out.println(inStream);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
		inStream.close();
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, paramSpec);
		/** generating the encrypted result */
		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
		// To add iv in encrypted string.
		byte[] encryptedWithIV = copyIVAndCipher(encrypted, iv);
		String encryptedResult = Base64.encode(encryptedWithIV);
		return encryptedResult;
	}

	// Encrypt response
	public String aes128Decrypt(String encryptedText) throws Exception {
		SecretKeySpec skeySpec = getSecretKeySpecFromHexString(ALGORITHM, KEY);
		byte[] encryptedIVandTextAsBytes = Base64.decode(encryptedText);
		/** First 16 bytes are always the IV */
		byte[] iv = Arrays.copyOf(encryptedIVandTextAsBytes, 16);
		byte[] ciphertextByte = Arrays.copyOfRange(encryptedIVandTextAsBytes, 16, encryptedIVandTextAsBytes.length);

		// Decrypt the message
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHAM);
		SecureRandom secureRandom = new SecureRandom();
		// String keyFile = "/home/webapp/key/private_key.pem.pem"; // server
		String keyFile = "C:\\private_key.pem.pem"; // local
		InputStream inStream = new FileInputStream(keyFile);
		System.out.println("private key");
		System.out.println(inStream);
		byte[] encKey = new byte[inStream.available()];
		inStream.read(encKey);
		inStream.close();
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
		byte[] decryptedTextBytes = cipher.doFinal(ciphertextByte);
		String decryptedResult = new String(decryptedTextBytes, "UTF-8");
		return decryptedResult;
	}

	private static SecretKeySpec getSecretKeySpecFromHexString(String algoCommonName, String hexString)
			throws Exception {
		byte[] encodedBytes = hexStrToByteArray(hexString);
		return new SecretKeySpec(encodedBytes, algoCommonName);
	}

	private static byte[] hexStrToByteArray(String hex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(hex.length() / 2);
		for (int i = 0; i < hex.length(); i += 2) {
			String output = hex.substring(i, i + 2);
			int decimal = Integer.parseInt(output, 16);
			baos.write(decimal);
		}
		return baos.toByteArray();
	}

	public static byte[] copyIVAndCipher(byte[] encryptedText, byte[] iv) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.write(iv);
		os.write(encryptedText);
		return os.toByteArray();
	}
}

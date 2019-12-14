package id.co.hanoman.sakpole.util;

import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.Decoder.Binary;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.binary.BinaryCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.hanoman.config.YAMLConfig;
import id.co.hanoman.sakpole.model.ErrorResponse;
import id.co.hanoman.sakpole.model.Payment;
import id.co.hanoman.sakpole.model.Response;
import id.co.hanoman.sakpole.model.Check_Bill;

@Component
public class NetClient {
	static Logger log = LoggerFactory.getLogger(NetClient.class);	 

	@Autowired
	YAMLConfig config ;
	
	public Object checkbill(Check_Bill req) throws Exception{
		Object resCall = null;
		try {				
			String code = (req.getCode()== null ? "null" : req.getCode());

			resCall = callUrl("code="+code+"&reqTime="+getTimeStamp(),"checked_billing_client");
			log.info("request  :"+resCall);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return resCall;
	}
	
	public Object reqpayment(Check_Bill req) throws Exception{
		Object resCall = null;
		try {				
			String code = (req.getCode()== null ? "null" : req.getCode());

			resCall = callUrl("code="+code+"&reqTime="+getTimeStamp(),"req_billing_payment");
			log.info("request  :"+resCall);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return resCall;
	}
	
	public Object payment(Payment req) throws Exception{
		Object resCall = null;
		try {				
			
			String code = (req.getCode()== null ? "null" : req.getCode());
			String nobb = (req.getNobb()== null ? "null" : req.getNobb());
			
			resCall = callUrl("code="+code+"&nobb="+nobb+"&dt="+getTimeStamp()+"&res_code=&res_desc=&log_no=","accept_payment");
			log.info("request  :"+resCall);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return resCall;
	}

	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs = 1 min

	private  static String getTimeStamp(){
		String pattern = "yyyyMMddHHmmss";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Calendar date = Calendar.getInstance();
		long t= date.getTimeInMillis();
		Date addTime=new Date(t - (	1 * ONE_MINUTE_IN_MILLIS));
		
		String timest = simpleDateFormat.format(addTime);
		return timest;
	}	

	public static String Encrypt(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		String xkey = "BEKKYYK8mfBiyQL4wwYkt2GvaSD8iWczNADTr8ElNYGKTqw8lRZ20MVPrcSq";
		
		log.info("encrypt  :"+ data);
		
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv); 
		
        byte[] sha256 = xkey.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] sha = md.digest(sha256);
        log.info("hash : " + sha );
		
        Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(sha, "AES");
        ciper.init(Cipher.ENCRYPT_MODE, key, ivspec);
        
        String encrypt = Base64.getEncoder().encodeToString(ciper.doFinal(data.getBytes()));
        log.info("hasil encrypt :" + encrypt);
        return encrypt;
	}

	private  Object callUrl(String data, String subPath) throws Exception{
		URL url = new URL(config.getBaseUrl()+subPath);
		log.info("callUrl url  :"+url);
		try {
			
			String enCode = Encrypt(data);
			String reqNode = "{\"code\":\""+data+"\"}";
			
			
			String request = "{\"code\":\""+enCode+"\",\"key_id\":\"200\"}";
			log.info("request  :"+ request);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(20000);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			if(data!=null){
				OutputStream os = conn.getOutputStream();
				os.write(request.getBytes());
				os.flush();
				os.close();
			}
	        
			int rc = conn.getResponseCode();
			log.info("rc:"+rc);
	        
			if (conn.getResponseCode() != 200) {
				ErrorResponse errRes = new ErrorResponse();
				errRes.setCode(String.valueOf(conn.getResponseCode()));
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
				String message = org.apache.commons.io.IOUtils.toString(br);
						log.info("message:"+message);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode resJson = mapper.readTree(message);
				JsonNode reqJson = mapper.readTree(reqNode);
				Response resp = new Response();
				resp.setRequest(reqJson);
				resp.setResponse(resJson);
				return resp;

			}else {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String message = org.apache.commons.io.IOUtils.toString(br);
						log.info("message:"+message);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode resJson = mapper.readTree(message);
				JsonNode reqJson = mapper.readTree(reqNode);
				JsonNode resJson_array = resJson.get("_res_array");
				Response resp = new Response();
				resp.setRequest(reqJson);
				resp.setResponse(resJson_array);
				return resp;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String strErrMsg = e.toString();
			ErrorResponse errRes = new ErrorResponse();
			
			errRes.setCode("400");
			if(strErrMsg.equals("java.net.ConnectException: Connection refused: connect")){
				errRes.setFaultcode("G02");
				errRes.setFaultMessage("Failed to Connect");
		
			} else if(strErrMsg.equals("java.net.SocketTimeoutException: Read timed out")){
				errRes.setFaultcode("G01");
				errRes.setFaultMessage("Timeout from Ewallet");
				
			}else {
				errRes.setFaultcode("G99");
				errRes.setFaultMessage("General Error");
			}
			return errRes;
		}
	}
}




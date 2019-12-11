package id.co.hanoman.controllers;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import id.co.hanoman.sakpole.model.Check_Bill;
import id.co.hanoman.sakpole.model.Payment;
import id.co.hanoman.sakpole.util.NetClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value="sakpole", description="Gateway for Sakpole api")
@RestController
@RequestMapping("/sakpole/btn")
public class SakpoleController {
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfDate2 = new SimpleDateFormat("dd MMM yyyy");
	@Autowired
	NetClient netClient;
	
	static Logger log = LoggerFactory.getLogger(NetClient.class);	 

	@ApiOperation(value = "Check Bill",response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
			)
	
	@RequestMapping(value = "/checkbill", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> checkbill(@RequestBody Check_Bill req){
		Object res = null;
		try {
			log.info("request service : "+getJson(req));
			res = netClient.checkbill(req);
			log.info("response service : "+getJson(res));
		} catch (Exception e) {
			log.error("service",e);
		}
		return ResponseEntity.ok(res);
	}
	
	
	@ApiOperation(value = "Req Payment",response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
			)
	
	@RequestMapping(value = "/reqpayment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> reqpayment(@RequestBody Check_Bill req){
		Object res = null;
		try {
			log.info("request service : "+getJson(req));
			res = netClient.reqpayment(req);
			log.info("response service : "+getJson(res));
		} catch (Exception e) {
			log.error("service",e);
		}
		return ResponseEntity.ok(res);
	}


	@ApiOperation(value = "Acc Payment",response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
			)
	
	@RequestMapping(value = "/accpayment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> accpayment(@RequestBody Payment req){
		Object res = null;
		try {
			log.info("request service : "+getJson(req));
			res = netClient.payment(req);
			log.info("response service : "+getJson(res));
		} catch (Exception e) {
			log.error("service",e);
		}
		return ResponseEntity.ok(res);
	}

	public JsonNode getJson(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode reqJson = mapper.valueToTree(obj);
		return reqJson;
	}
	
}

package id.co.hanoman.sakpole.model;

import javax.validation.constraints.NotNull;

public class Check_Bill {
	
	@NotNull
	String code ;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

}

package id.co.hanoman.sakpole.model;

import javax.validation.constraints.NotNull;

public class Payment {
	@NotNull
	String code ;
	
	@NotNull
	String nobb ;


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getNobb() {
		return nobb;
	}

	public void setNobb(String nobb) {
		this.nobb = nobb;
	}
	
}

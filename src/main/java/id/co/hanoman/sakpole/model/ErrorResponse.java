package id.co.hanoman.sakpole.model;

public	class ErrorResponse {
		String code;
		String faultcode;
		String faultmessage;
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getFaultcode() {
			return faultcode;
		}
		public void setFaultcode(String faultcode) {
			this.faultcode = faultcode;
		}
		public String getFaultMessage() {
			return faultmessage;
		}
		public void setFaultMessage(String faultmessage) {
			this.faultmessage = faultmessage;
		}
		
	}


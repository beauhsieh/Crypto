package com.crypto.crypto.errorHandling;

public class ErrorHandlingConstants {
	public enum ERROR_BUNDLE {
		internalError("internalError"),
		invalidToken("Token was either missing or invalid."),
		quotaLimitation("Exceed quota limitation");

		private ERROR_BUNDLE(String desc) {
			this.desc = desc;
		}

		private String desc;

		public String getDesc() {
			return desc;
		}
	}
}

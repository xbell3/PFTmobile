package com.utec.pft.controlador.servicios;

public class ClientResponse{

	public static final String ALTA_PERSONA_CORRECTA = "AP200";
	public static final String ALTA_ENCARGADO_CORRECTA = "AP-E200";
	public static final String ALTA_PERSONAL_CORRECTA = "AP-P200";
	
	public static final String ALTA_TERNERA_CORRECTA = "AT200";	
	
	public static final String BAJA_TERNERA_CORRECTA = "BT-200";
	
	public static final String MODIFICACION_TERNERA_CORRECTA = "AT200";
	
	public static final String ALTA_MEDICAMENTO_CORRECTA = "AM200";
	public static final String BAJA_MEDICAMENTO_CORRECTA = "BM200";
	public static final String MODIFICACION_MEDICAMENTO_CORRECTA = "MM200";
	
	public static final String REGISTRO_MEDICACION_CORRECTA = "RM200";
	
	public static final String REGISTRO_ALIMENTACION_CORRECTA = "RA200";
	
	private int statusCode;
	private String internalCode;
	private String message;

	public ClientResponse() {}

	public ClientResponse(int statusCode, String internalCode, String message) {
		this.statusCode = statusCode;
		this.internalCode = internalCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

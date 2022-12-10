package com.utec.pft.modelo.Ternera;

import java.io.Serializable;


public class TipoPartoDTO implements Serializable {

	private int idParto;
	
	private String tipoParto;

	public TipoPartoDTO() {
	}

	public TipoPartoDTO(int idParto, String tipoParto) {
		this.idParto = idParto;
		this.tipoParto = tipoParto;
	}

	public int getIdParto() {
		return idParto;
	}

	public void setIdParto(int idParto) {
		this.idParto = idParto;
	}

	public String getTipoParto() {
		return tipoParto;
	}

	public void setTipoParto(String tipoParto) {
		this.tipoParto = tipoParto;
	}
}

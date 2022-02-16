package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.IRoleService;

@Component //Para poder inyectarlo en el FormController
public class RolesEditor extends PropertyEditorSupport {
	
	@Autowired
	private IRoleService service;

	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(idString);
			this.setValue(this.service.obtenerPorId(id));
		}catch(NumberFormatException e) {
			this.setValue(null);
		}
	}
	
	

}

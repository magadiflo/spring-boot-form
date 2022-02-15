package com.bolsadeideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsadeideas.springboot.form.app.services.IPaisService;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private IPaisService service;

	@Override
	public void setAsText(String idString) throws IllegalArgumentException {
		try {
			Integer id = Integer.parseInt(idString);
			this.setValue(this.service.obtenerPorId(id));
		} catch (NumberFormatException e) {
			this.setValue(null);
		}
	}

}

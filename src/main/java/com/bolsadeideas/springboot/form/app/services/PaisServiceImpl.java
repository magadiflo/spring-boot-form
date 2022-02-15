package com.bolsadeideas.springboot.form.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;

@Service
public class PaisServiceImpl implements IPaisService {
	
	private List<Pais> lista;
	
	public PaisServiceImpl() {
		this.lista = Arrays.asList(
				new Pais(1, "ES", "España"),
				new Pais(2, "PE", "Perú"),
				new Pais(3, "CO", "Colombia"),
				new Pais(4, "BR", "Brasil"),
				new Pais(5, "CA", "Canadá"));
	}

	@Override
	public List<Pais> listar() {
		return this.lista;
	}

	@Override
	public Pais obtenerPorId(Integer id) {
		return this.lista.stream().filter(pais -> pais.getId().equals(id))
				.collect(Collectors.toList()).get(0);
	}

}

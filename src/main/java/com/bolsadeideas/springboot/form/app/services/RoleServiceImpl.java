package com.bolsadeideas.springboot.form.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.form.app.models.domain.Role;

@Service
public class RoleServiceImpl implements IRoleService {
	
	private List<Role> roles;
	
	public RoleServiceImpl() {
		this.roles = new ArrayList<>();
		this.roles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
		this.roles.add(new Role(2, "Usuario", "ROLE_USER"));
		this.roles.add(new Role(3, "Moderador", "ROLE_MODERATOR"));		
	}

	@Override
	public List<Role> listar() {
		return this.roles;
	}

	@Override
	public Role obtenerPorId(Integer id) {
		return this.roles.stream().filter(role -> role.getId().equals(id))
				.collect(Collectors.toList()).get(0);
	}

}

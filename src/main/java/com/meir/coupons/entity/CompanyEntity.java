package com.meir.coupons.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Company")
public class CompanyEntity {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;
	
	@OneToMany (mappedBy = "company", cascade = CascadeType.REMOVE)
	private List<UserEntity> users;
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

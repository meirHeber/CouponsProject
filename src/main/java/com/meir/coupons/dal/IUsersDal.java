package com.meir.coupons.dal;

import com.meir.coupons.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUsersDal extends CrudRepository<UserEntity, Long> {
	
	public List<UserEntity> findByName(String name);
	public List<UserEntity> findByNameAndAge(String name, int age);
	
	@Query("SELECT u FROM UserEntity u WHERE u.name= :userName and u.age=:age")
	public List<UserEntity> getByData(@Param("userName") String name, @Param("age") int age);
}

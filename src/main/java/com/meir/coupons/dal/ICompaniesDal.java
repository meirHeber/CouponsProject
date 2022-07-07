package com.meir.coupons.dal;

import com.meir.coupons.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

public interface ICompaniesDal extends CrudRepository<CompanyEntity, Long> {

}

package com.irelint.ttt.user.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.irelint.ttt.user.model.Address;

public interface AddressDao extends CrudRepository<Address, Long> {

	List<Address> findByUserId(Long userId);
}

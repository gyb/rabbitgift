package com.irelint.ttt.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AddressDao extends CrudRepository<Address, Long> {

	List<Address> findByUserId(Long userId);
}

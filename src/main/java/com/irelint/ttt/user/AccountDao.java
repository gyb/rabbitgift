package com.irelint.ttt.user;

import org.springframework.data.repository.CrudRepository;


public interface AccountDao extends CrudRepository<Account, Long> {
}

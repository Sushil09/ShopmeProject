package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<Role,Integer> {
}

package com.shopme.admin.services;

import com.shopme.admin.user.RolesRepository;
import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) rolesRepository.findAll();
    }

    public void save(User user) {
        encryptPassword(user);
        userRepository.save(user);
    }

    private void encryptPassword(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(String email){
        User newUser=userRepository.getUserByEmail(email);
        return newUser==null;
    }
}

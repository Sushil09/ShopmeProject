package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUser() {
        //User with a single role
        Role adminRole = testEntityManager.find(Role.class, 1);
        User adminUser1 = new User("sjsushil69@gmail.com", "12345678q", "Sushil", "Jaiswal");
        adminUser1.addRole(adminRole);
        User savedAdminUser1 = userRepository.save(adminUser1);
        assertThat(savedAdminUser1.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithMultipleRoles(){
        //User with more than 1 role
        Role adminRole = testEntityManager.find(Role.class, 1);
        Role editorRole = testEntityManager.find(Role.class, 3);
        User editorUser1 = new User("vsvarun09@gmail.com", "12345678q", "Varun", "Sharma");
        editorUser1.addRole(adminRole);
        editorUser1.addRole(editorRole);
        User savedEditorUser1 = userRepository.save(editorUser1);
        assertThat(savedEditorUser1.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> users=userRepository.findAll();
        users.forEach(user-> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        Optional<User> user=userRepository.findById(1);
        System.out.println(user);
        assertThat(user.get().getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateUser(){
        Optional<User> user=userRepository.findById(1);
        user.get().setEnabled(true);
        userRepository.save(user.get());
    }

    @Test
    public void updateUserRoles(){
        Role editorRole=new Role(3);
        User userVarun=userRepository.findById(3).get();
        userVarun.getRoles().remove(editorRole);
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteById(3);
    }

    @Test
    public void testgetUserByEmail(){
        String testEmail="akku09@gmail.com";
        User user=userRepository.getUserByEmail(testEmail);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisabledUser(){
        Integer id=9;
        userRepository.setEnabledStatus(id,false);
    }

    @Test
    public void testEnabledUser(){
        Integer id=7;
        userRepository.setEnabledStatus(id,true);
    }

    @Test
    public void testPagination(){
        int pageNumber=1;
        int pageSize=4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> userPage = userRepository.findAll(pageable);

        List<User> userList = userPage.getContent();

        userList.forEach(user-> System.out.println(user));

        assertThat(userList.size()).isEqualTo(4);
    }
}

package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String testPassword="12345678q";
        String encodedPassword=bCryptPasswordEncoder.encode(testPassword);

        System.out.println(encodedPassword);

        boolean matches=bCryptPasswordEncoder.matches(testPassword,encodedPassword);
        assertThat(matches).isTrue();
    }
}

package com.ligthspeed.kirandeep.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class FakePasswordEncoder extends BCryptPasswordEncoder {
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}

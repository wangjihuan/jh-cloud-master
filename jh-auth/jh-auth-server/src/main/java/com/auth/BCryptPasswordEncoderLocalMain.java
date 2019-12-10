package com.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderLocalMain {

    static final ThreadLocal threadLocal = new ThreadLocal();
    public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("user-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().encode("client-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().encode("code-secret-8888"));


        threadLocal.set("1000");
        Object o = threadLocal.get();
        System.out.println(o);
    }

}

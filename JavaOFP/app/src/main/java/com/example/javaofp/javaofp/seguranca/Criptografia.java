package com.example.javaofp.javaofp.seguranca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rafaelcavalcante on 03/12/19.
 */

public class Criptografia {

    public static String criptografar(String senha, String tipoCriptografia) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(tipoCriptografia);

        messageDigest.update(senha.getBytes());

        String senhaEncriptada = new String(messageDigest.digest());

        return senhaEncriptada;

    }
}

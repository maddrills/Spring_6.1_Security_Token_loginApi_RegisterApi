package com.tryOne.AuthonBack.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
//key 1.0 / 3
public class KeyGeneratorUtility {

    public static KeyPair generateRsaKey(){

        KeyPair keypair;

        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keypair = keyPairGenerator.generateKeyPair();
        }catch (Exception e){
            throw new IllegalStateException();
        }

        return keypair;
    }

}

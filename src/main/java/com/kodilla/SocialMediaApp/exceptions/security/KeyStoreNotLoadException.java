package com.kodilla.SocialMediaApp.exceptions.security;

public class KeyStoreNotLoadException extends RuntimeException{
    public KeyStoreNotLoadException(String message) {
        super("Exception occurred while loading keystore with message: " + message);
    }
}

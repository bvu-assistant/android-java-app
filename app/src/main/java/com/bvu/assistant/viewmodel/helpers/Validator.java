package com.bvu.assistant.viewmodel.helpers;



public class Validator {

    public static boolean isEmail(String input) {
        return input.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
    }



}

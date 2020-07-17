package com.sandeepkamathkr.isbntools;

public class ValidateISBN {

    public boolean checkISBN(String isbnNumber) {
        if (isbnNumber.length() != 10) {
            throw new NumberFormatException("ISBN number must be 10 digit long");
        }
        int total = 0;
        for (int i = 0; i < 10; i++) {
            if (!Character.isDigit(isbnNumber.charAt(i))) {
                if (i == 9 && isbnNumber.charAt(i) == 'X') {
                    total += 10;
                } else {
                    throw new NumberFormatException("ISBN numbers " +
                            "can only contain numeric digits");
                }
            }else {
                total += Character.getNumericValue(isbnNumber.charAt(i)) * (10 - i);
            }
        }
        if (total % 11 == 0) {
            return true;
        } else {
            return false;
        }
    }
}

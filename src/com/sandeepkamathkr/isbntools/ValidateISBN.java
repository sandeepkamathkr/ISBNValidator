package com.sandeepkamathkr.isbntools;

public class ValidateISBN {

    public static final int LONG_ISBN_LENGTH = 13;
    public static final int SHORT_ISBN_LENGTH = 10;
    public static final int SHORT_ISBN_MULTIPLIER = 11;
    public static final int LONG_ISBN_MULTIPLIER = 10;

    public boolean checkISBN(String isbnNumber) {
        if (isbnNumber.length() == LONG_ISBN_LENGTH) {
            return isThisAValidLongISBN(isbnNumber);
        } else if (isbnNumber.length() == SHORT_ISBN_LENGTH){
            return isThisAValidShortISBN(isbnNumber);
        }
        throw new NumberFormatException("ISBN number must be 10 or 13 digit long");
    }

    private boolean isThisAValidShortISBN(String isbnNumber) {
        int total = 0;
        for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
            if (!Character.isDigit(isbnNumber.charAt(i))) {
                if (i == 9 && isbnNumber.charAt(i) == 'X') {
                    total += 10;
                } else {
                    throw new NumberFormatException("ISBN numbers " +
                            "can only contain numeric digits");
                }
            } else {
                total += Character.getNumericValue(isbnNumber.charAt(i)) * (SHORT_ISBN_LENGTH - i);
            }
        }
        return total % SHORT_ISBN_MULTIPLIER == 0;
    }

    private boolean isThisAValidLongISBN(String isbnNumber) {
        int total = 0;
        for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
            if (i % 2 == 0) {
                total += Character.getNumericValue(isbnNumber.charAt(i));
            } else {
                total += Character.getNumericValue(isbnNumber.charAt(i)) * 3;
            }
        }
        return total % LONG_ISBN_MULTIPLIER == 0;
    }
}

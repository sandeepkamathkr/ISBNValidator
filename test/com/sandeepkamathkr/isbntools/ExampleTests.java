package com.sandeepkamathkr.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleTests {

    @Test
    public void checkAValidISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue("first value",result);
        result = validator.checkISBN("0140177396");
        assertTrue("second value",result);
    }

    @Test
    public void tenDigitISBNNumbersEndingInAnXAreValid(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("012000030X");
        assertTrue(result);
    }
    @Test
    public void checkAnInValid10DigitISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void checkAnInValid13DigitISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853267336");
        assertFalse(result);
    }

    @Test
    public void checkAValid13DigitISBN(){
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853260087");
        assertTrue("first value",result);
        result = validator.checkISBN("9781853267338");
        assertTrue("second value",result);
    }

    @Test(expected = NumberFormatException.class)
    public void nineDigitISBNsAreNotAllowed(){
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void characterOtherThanNumberAsISBNsAreNotAllowed(){
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("helloworld");
    }


}

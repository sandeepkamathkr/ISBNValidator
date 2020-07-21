package com.sandeepkamathkr.isbntools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTests {

    ExternalISBNDataService testWebService;
    ExternalISBNDataService testDataBaseService;
    StockManager stockManager;

    @Before
    public void setup() {
        testWebService = mock(ExternalISBNDataService.class);
        testDataBaseService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDataBaseService);
    }

    @Test
    public void testCanGetACorrectLocatorCode() {
        String isbn = "0140177396";
        when(testWebService.lookup(anyString())).thenReturn(new Book(isbn, "Of Mice And Men", "J. Steinbeck"));
        when(testDataBaseService.lookup(anyString())).thenReturn(null);
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        String isbn = "0140177396";
        when(testDataBaseService.lookup(isbn)).thenReturn(new Book(isbn, "abc", "abc"));
        stockManager.getLocatorCode(isbn);
        verify(testDataBaseService).lookup(isbn);
        verify(testWebService, never()).lookup(anyString());
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
        String isbn = "0140177396";
        when(testDataBaseService.lookup(isbn)).thenReturn(null);
        when(testWebService.lookup(isbn)).thenReturn(new Book(isbn, "abc", "abc"));
        stockManager.getLocatorCode(isbn);
        verify(testDataBaseService).lookup(isbn);
        verify(testWebService).lookup(isbn);
    }
}

package com.sandeepkamathkr.isbntools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class StockManagementTests {

    @Test
    public void testCanGetACorrectLocatorCode(){
        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn,"Of Mice And Men","J. Steinbeck");
            }
        };

        ExternalISBNDataService testDataBaseService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };
        String isbn ="0140177396";
        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDataBaseService);
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4",locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent(){
        String isbn ="0140177396";
        ExternalISBNDataService dataBaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(dataBaseService.lookup(isbn)).thenReturn(new Book(isbn,"abc","abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dataBaseService);
        stockManager.getLocatorCode(isbn);
        verify(dataBaseService).lookup(isbn);
        verify(webService,never()).lookup(anyString());
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase(){
        String isbn ="0140177396";
        ExternalISBNDataService dataBaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(dataBaseService.lookup(isbn)).thenReturn(null);
        when(webService.lookup(isbn)).thenReturn(new Book(isbn,"abc","abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dataBaseService);
        stockManager.getLocatorCode(isbn);
        verify(dataBaseService).lookup(isbn);
        verify(webService).lookup(isbn);
    }
}

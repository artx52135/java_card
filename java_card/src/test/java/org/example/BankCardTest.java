package org.example;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class BankCardTest {
    private Client client;
    private BankCard bankCard;

    @Before
    public void setUp() {
        client = new Client("Mister X", new Date());
        bankCard = new BankCard(client, "59436494906954", new Date(), new Date(System.currentTimeMillis() + 10000));
    }

    @Test
    public void testGetCardNumber() {
        assertEquals("59436494906954", bankCard.getCardNumber());
    }

    @Test
    public void testGetClient() {
        assertEquals(client, bankCard.getClient());
    }

    @Test
    public void testGetExpiryDate() {
        assertEquals(new Date(System.currentTimeMillis() + 10000), bankCard.getExpiryDate());
    }

    @Test
    public void testGetIsSentNote() {
        assertFalse(bankCard.getIsSentNote());
    }

    @Test
    public void testSetIsSentNote() {
        bankCard.setIsSentNote(true);
        assertTrue(bankCard.getIsSentNote());
    }
}
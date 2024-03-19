package org.example;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class BankDatabaseTest {
    private BankDatabase bankDatabase;
    private Client client;
    private BankCard bankCard;

    @Before
    public void setUp() {
        bankDatabase = new BankDatabase();
        client = new Client("James Bond", new Date());
        bankCard = new BankCard(client, "1234567890123456", new Date(), new Date(System.currentTimeMillis() + 10000));
    }

    @Test
    public void testCreateClient() {
        String clientId = bankDatabase.createClient(client);
        assertEquals(client, bankDatabase.getClients().get(clientId));
    }

   @Test
    public void testCreateCard() {
        bankDatabase.createClient(client);
        bankDatabase.createCard(bankCard);
        assertEquals(bankCard, bankDatabase.getBankCards().get("1234567890123456"));
    }
}

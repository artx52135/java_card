package org.example;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Date;

public class ClientTest {
    private Client client;

    @Before
    public void setUp() {
        client = new Client("Petr Petrov", new Date());
    }

    @Test
    public void testGetId() {
        assertNotNull(client.getId());
        assertTrue(client.getId().length() > 0);
    }

    @Test
    public void testGetFullName() {
        assertEquals("Petr Petrov", client.getFullName());
    }

   @Test
    public void testGetWriter() {
        try {
            BufferedWriter writer = client.getWriter();
            assertNotNull(writer);
            writer.write("Test");
            writer.flush();
            writer.close();
            assertTrue(new File("Petr Petrov.txt").exists());
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
   }

    @Test
    public void testGetWriterFileContent() {
        try {
            BufferedWriter writer = client.getWriter();
            writer.write("Test");
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader("C:\\JAVA\\java_card\\java_card\\Petr Petrov.txt"));
            String line = reader.readLine();
            assertEquals("Test", line);
            reader.close();
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }
}
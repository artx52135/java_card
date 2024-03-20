package org.example;

import static org.junit.Assert.*;

import org.junit.After;
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
            writer.close();
            assertTrue(new File("Petr Petrov.txt").exists());
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
   }
    private void clearFile(String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetWriterFileContent() {
        clearFile("C:\\JAVA\\java_card\\java_card\\Petr Petrov.txt"); // Очистка файла перед чтением
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
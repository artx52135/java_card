package org.example;

import com.sun.security.jgss.GSSUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

// Класс клиента
class Client {
    private String id;
    private String fullName;
    private Date dateOfBirth;
    private BufferedWriter writer;


    public Client(String fullName, Date dateOfBirth) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        try {
            this.writer = new BufferedWriter(new FileWriter(fullName + ".txt", true)); // Создание файла для уведомлений
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getInfo(){
        System.out.println("Имя клиента:" + fullName);
        System.out.println("Дата рождения клиента:" + dateOfBirth);
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
    public BufferedWriter getWriter() {
        return writer;
    }
}

// Класс банковской карты
class BankCard{
    private String cardNumber;
    private Client client;
    private Date issueDate;
    private Date expiryDate;
    private boolean isSentNote;


    public BankCard(Client client, String cardNumber, Date issueDate, Date expiryDate) {
        this.client = client;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.isSentNote = false;
    }
    public void getInfo(){
        System.out.println("Номер карты:" + cardNumber);
        System.out.println("Дата создания карты:" + issueDate);
        System.out.println("Дата окончания срока действия карты:" + expiryDate);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Client getClient() {
        return client;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    public boolean getIsSentNote(){
        return isSentNote;
    }
    public void setIsSentNote(boolean flag){
        isSentNote = flag;
    }
}


// Класс для уведомлений
class NotificationService {
    public static void sendNotification(Client client, String message) {
        try {
            BufferedWriter writer = client.getWriter();
            writer.write("Sending notification: " + message + "\n");
            writer.flush();
            System.out.println("Отправка уведомления " + client.getFullName() + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Класс для хранения данных клиентов и их карт
class BankDatabase{
    private Map<String, Client> clients;
    private Map<String, BankCard> bankCards;


    public BankDatabase() {
        clients = new HashMap<>();
        bankCards = new HashMap<>();
    }
    public Map<String, Client> getClients(){
        return clients;
    }

    public String createClient(Client client) {
        clients.put(client.getId(), client);
        return client.getId();
    }

    public void createCard(BankCard card) {
        bankCards.put(card.getCardNumber(), card);
    }


    public String createClient(String fullName, Date dateOfBirth) {
        Client client = new Client(fullName, dateOfBirth);
        clients.put(client.getId(), client);
        return client.getId();
    }

    public void createCard(String clientId, String cardNumber, Date issueDate, Date expiryDate) {
        Client client = clients.get(clientId);
        if (client != null) {
            BankCard card = new BankCard(client, cardNumber, issueDate, expiryDate);
            bankCards.put(cardNumber, card);
        }
    }

    public void cancelCard(String cardNumber) {
        BankCard card = bankCards.get(cardNumber);
        if (card != null) {
            bankCards.remove(cardNumber);
        }
    }


    // Проверка и отправка уведомлений о завершении срока действия карты
    public void checkAndSendNotifications() {
        Date currentDate = new Date();
        for (BankCard card : bankCards.values()) {
            if (currentDate.after(card.getExpiryDate()) && card.getIsSentNote() == false) {
                Client client = card.getClient();
                NotificationService.sendNotification(client, "Срок действий вашей карты под номером: " + card.getCardNumber() + " истёк!");
                card.setIsSentNote(true);
            }
        }
    }
    public void getInfo() {
        System.out.println("Информация о клиентах и их картах:");
        for (Client client : clients.values()) {
            System.out.println("Клиент:");
            client.getInfo();
            System.out.println("Карты:");
            for (BankCard card : bankCards.values()) {
                if (card.getClient().getId().equals(client.getId())) {
                    card.getInfo();
                }
            }
            System.out.println();
        }
    }
}

// Класс для потока рассылки уведомлений
class NotificationThread extends Thread {
    private BankDatabase bankDatabase;
    private long interval; // Интервал между проверками в миллисекундах

    public NotificationThread(BankDatabase bankDatabase, long interval) {
        this.bankDatabase = bankDatabase;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            bankDatabase.checkAndSendNotifications();
            try {
                Thread.sleep(interval); // Пауза между проверками
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankDatabase bankDatabase = new BankDatabase();
        Client cl1 = new Client("Tom Smith", new Date());
        BankCard bc1 = new BankCard(cl1,"123", new Date(), new Date(System.currentTimeMillis() + 10000));
        bankDatabase.createClient(cl1);
        bankDatabase.createCard(bc1);
        //bc1.getInfo();



        String clientId = bankDatabase.createClient("Иван Иванович Иванов", new Date());
        bankDatabase.createCard(clientId, "1234567890123456", new Date(), new Date(System.currentTimeMillis() + 10000));
        bankDatabase.createCard(clientId, "9999999999999999", new Date(), new Date(System.currentTimeMillis() + 20000));
        bankDatabase.createCard(clientId, "7777777777777777", new Date(), new Date(System.currentTimeMillis() + 50000));

        NotificationThread notificationThread = new NotificationThread(bankDatabase, 5000); // Проверяем каждую минуту
        notificationThread.start();
        bankDatabase.getInfo();
    }
}

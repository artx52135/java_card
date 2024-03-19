package org.example;

import java.util.Date;
import java.util.UUID;

class Client {
    private String id;
    private String fullName;
    private Date dateOfBirth;



    public Client(String fullName, Date dateOfBirth) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
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

}
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
}

public class Main {
    public static void main(String[] args) {

    }
}

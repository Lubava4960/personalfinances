package org.example.model;

import org.example.enumeration.TransactionType;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class Transaction {
    private double amount;
    private String category;
    private LocalDateTime date;
    private String description;
    private TransactionType type;


    public Transaction(double amount, String category, TransactionType type, LocalDateTime date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.type = type;


    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public TransactionType getType() {
        return type;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public void setType(TransactionType newType) {
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "сумма: " + amount +
                ", категория: " + category + '\'' +
                ", дата: " + date +
                ", описание: " + description + '\'' +
                ", тип расход или доход: " + type +
                '}';
    }
}

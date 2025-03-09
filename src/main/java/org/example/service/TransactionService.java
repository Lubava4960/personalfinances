package org.example.service;

import org.example.enumeration.TransactionType;
import org.example.model.Budget;
import org.example.model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private Budget budget;
    private List<Transaction> transactions = new ArrayList<>();
    public TransactionService() {
        this.transactions = new ArrayList<>();
    }
    public void setMonthlyBudget(double amount) {
        this.budget = new Budget(amount);
        System.out.println("Месячный бюджет установлен: " + amount);
    }
    public void addTransaction(double amount, String category,  TransactionType type, String description) {
        transactions.add(new Transaction(amount, category, type, LocalDateTime.now(), description));
        if (type == TransactionType.EXPENSE && budget != null) {
            budget.addExpense(amount);
        }

    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    public Transaction getTransactionByIndex(int index) {
        if (index >= 0 && index < transactions.size()) {
            return transactions.get(index);
        } else {
            return null; // Возвращаем null, если индекс вне диапазона
        }
    }

    public List<Transaction> getTransactionsByCategory(String category) {
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        if (filteredTransactions.isEmpty()) {
            System.out.println("Нет транзакций с указанной категорией: " + category);
        }

        return filteredTransactions;
    }

//    public List<Transaction> getTransactionsByAmountGreaterThan(double amount) {
//        return transactions.stream()
//                .filter(t -> t.getAmount() > amount)
//                .collect(Collectors.toList());
//    }
    public List<Transaction> getTransactionsByDate(LocalDateTime date) {
        return transactions.stream()
                .filter(t -> t.getDate().toLocalDate().isEqual(date.toLocalDate()))
                .collect(Collectors.toList());
    }
    public boolean updateTransaction(int index, double newAmount, String newCategory, TransactionType newType, String newDescription) {
        if (index >= 0 && index < transactions.size()) {
            Transaction transaction = transactions.get(index);

            if (transaction.getType() == TransactionType.EXPENSE && budget != null) {
                budget.addExpense(-transaction.getAmount());
            }
            transaction.setAmount(newAmount);
            transaction.setCategory(newCategory);
            transaction.setType(newType);
            transaction.setDescription(newDescription);

            if (newType == TransactionType.EXPENSE && budget != null) {
                budget.addExpense(newAmount);
            }

            return true;
        }
        return false;
    }

    public boolean deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
            return true;

        }
        return false;
    }



}

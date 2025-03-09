package org.example.service;

import org.example.enumeration.TransactionType;
import org.example.model.Budget;

import org.example.model.SavingsGoal;
import org.example.model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class TransactionManager {

    private TransactionService transactionService;
    private Scanner scanner;
    private List<Transaction> transactions;
    private Budget budget;
    private GoalService goalService;
    private List<SavingsGoal> savingsGoals;

    public TransactionManager(TransactionService transactionService, GoalService goalService, Scanner scanner) {
        this.transactionService = transactionService;
        this.goalService = goalService;
        this.scanner = scanner;
        this.savingsGoals = new ArrayList<>();
    }



    public void manageTransactions () {
            while (true) {
                System.out.println("1. Создать транзакцию");
                System.out.println("2. Редактировать транзакцию");
                System.out.println("3. Удалить транзакцию");
                System.out.println("4. Просмотреть все транзакции");
                System.out.println("5. Фильтрация транзакций");
                System.out.println("6. Установить месячный расход бюджета");
                System.out.println("7. Просмотреть текущий бюджет");
                System.out.println("8. Установить цель накопления");
                System.out.println("0. Назад");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        createTransaction();
                        break;
                    case 2:
                        editTransaction();
                        break;
                    case 3:
                        deleteTransaction();
                        break;
                    case 4:
                        viewTransactions();
                        break;
                    case 5:
                        filterTransactions();
                        break;
                    case 6:
                        setMonthlyBudget();
                        break;
                    case 7:
                        viewCurrentBudget();
                        break;
                    case 8:
                       goalService.manageGoals();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        }



    private void createTransaction() {
        System.out.println("Введите сумму:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите категорию:");
        String category = scanner.nextLine();
        System.out.println("Введите описание:");
        String description = scanner.nextLine();
        System.out.println("Введите тип операции: (1 - Доход, 2 - Расход)");
        int typeInput = scanner.nextInt();
        TransactionType type = (typeInput == 1) ? TransactionType.INCOME : TransactionType.EXPENSE;
        scanner.nextLine();
        if (type == TransactionType.EXPENSE && budget != null) {
            if (budget.getTotalExpenses() + amount > budget.getMonthlyBudget()) {
                System.out.println("Ошибка: Превышение месячного расхода бюджета. Транзакция не добавлена.");
                return;
            }
            budget.addExpense(amount);
        }
        transactionService.addTransaction(amount, category, type, description);
        System.out.println("Транзакция успешно добавлена!");
    }

    private void editTransaction() {
        System.out.println("Введите индекс транзакции для редактирования (начиная с 0):");
        int editIndex = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите новую сумму:");
        double newAmount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Введите новую категорию:");
        String newCategory = scanner.nextLine();

        System.out.println("Введите новый тип операции: (1 - Доход, 2 - Расход)");
        int typeInput = scanner.nextInt();

        TransactionType newType = (typeInput == 1) ? TransactionType.INCOME : TransactionType.EXPENSE;

        System.out.println("Введите новое описание:");
        scanner.nextLine();
        String newDescription = scanner.nextLine();
        Transaction oldTransaction = transactionService.getTransactionByIndex(editIndex); // Предполагается, что у вас есть метод для получения транзакции по индексу

        if (oldTransaction != null && oldTransaction.getType() == TransactionType.EXPENSE && budget != null) {
            budget.addExpense(-oldTransaction.getAmount());
        }

        if (newType == TransactionType.EXPENSE && budget != null) {
            if (budget.getTotalExpenses() + newAmount > budget.getMonthlyBudget()) {
                System.out.println("Ошибка: Превышение месячного расхода бюджета. Транзакция не обновлена.");
                return;
            }
            budget.addExpense(newAmount);
        }

        if (transactionService.updateTransaction(editIndex, newAmount, newCategory, newType, newDescription)) {
            System.out.println("Транзакция успешно обновлена!");
        } else {
            System.out.println("Ошибка: индекс вне диапазона.");
        }
    }


    private void deleteTransaction() {
        System.out.println("Введите индекс транзакции для удаления (начиная с 0):");
        int deleteIndex = scanner.nextInt();
        if (transactionService.deleteTransaction(deleteIndex)) {
            System.out.println("Транзакция успешно удалена!");
        } else {
            System.out.println("Ошибка: индекс вне диапазона.");
        }
    }

    private void viewTransactions() {
        System.out.println("Транзакции:");
        List<Transaction> transactions = transactionService.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("Список транзакций пуст.");
        } else {
            for (int i = 0; i < transactions.size(); i++) {
                System.out.println(i + ": " + transactions.get(i));
            }
        }
    }

    private void filterTransactions() {
        System.out.println("Фильтрация по (1 - Дате, 2 - Категории):");
        int filterChoice = scanner.nextInt();
        scanner.nextLine();

        switch (filterChoice) {
            case 1:

                System.out.println("Введите дату в формате yyyy-MM-dd:");
                String dateInput = scanner.nextLine();
                LocalDateTime date = LocalDateTime.parse(dateInput + "T00:00:00"); // Форматирование даты
                List<Transaction> transactionsByDate = transactionService.getTransactionsByDate(date);
                displayFilteredTransactions(transactionsByDate);
                break;
            case 2:

                System.out.println("Введите категорию:");
                String category = scanner.nextLine();
                List<Transaction> transactionsByCategory = transactionService.getTransactionsByCategory(category);
                displayFilteredTransactions(transactionsByCategory);
                break;
        }
    }

    private void displayFilteredTransactions(List<Transaction> transactions) {
        System.out.println("Отфильтрованные транзакции:");
        if (transactions.isEmpty()) {
            System.out.println("Нет транзакций по заданным критериям.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    private void setMonthlyBudget() {
        System.out.println("Введите месячный бюджет:");
        double amount = scanner.nextDouble();
        budget = new Budget(amount);
        System.out.println("Месячный бюджет установлен: " + amount);
    }

    private void viewCurrentBudget() {
        if (budget != null) {
            System.out.println("Текущий бюджет: " + budget.getMonthlyBudget());
            System.out.println("Общие расходы: " + budget.getTotalExpenses());
            if (budget.getTotalExpenses() > budget.getMonthlyBudget()) {
                System.out.println("Вы превысили расход бюджета на " + (budget.getTotalExpenses() - budget.getMonthlyBudget()) + " единиц.");
            } else {
                System.out.println("Вы в пределах бюджета.");
            }
        } else {
            System.out.println("Бюджет не установлен.");
        }
    }
}

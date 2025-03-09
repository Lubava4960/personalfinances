package org.example.service;

import org.example.model.SavingsGoal;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoalService {
    private List<SavingsGoal> savingsGoals;
    private Scanner scanner;

    public GoalService() {
        this.savingsGoals = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void manageGoals() {
        while (true) {
            System.out.println("1. Установить цель");
            System.out.println("2. Обновить прогресс по цели");
            System.out.println("3. Показать все цели");
            System.out.println("0. Назад");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createSavingsGoal();
                    break;
                case 2:
                    updateProgress();
                    break;
                case 3:
                    viewGoals();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void createSavingsGoal() {
        System.out.println("Введите сумму цели накопления:");
        double targetAmount = scanner.nextDouble();
        scanner.nextLine();
        SavingsGoal goal = new SavingsGoal(targetAmount);
        savingsGoals.add(goal);
        System.out.println("Цель накопления установлена: " + goal);
    }

    private void updateProgress() {
        System.out.println("Введите индекс цели, для обновления прогресса (начиная с 0):");
        int index = scanner.nextInt();
        scanner.nextLine(); // Считываем лишний символ

        if (index >= 0 && index < savingsGoals.size()) {
            System.out.println("Введите сумму для добавления в прогресс:");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            savingsGoals.get(index).addAmount(amount);
            System.out.println("Прогресс обновлен: " + savingsGoals.get(index));
        } else {
            System.out.println("Ошибка: индекс вне диапазона.");
        }
    }

    private void viewGoals() {
        if (savingsGoals.isEmpty()) {
            System.out.println("Нет установленных целей.");
        } else {
            System.out.println("Список целей накопления:");
            for (int i = 0; i < savingsGoals.size(); i++) {
                System.out.println(i + ": " + savingsGoals.get(i));
            }
        }
    }
}

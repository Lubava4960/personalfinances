package org.example.model;

public class SavingsGoal {
    private double targetAmount; //  цель
    private double currentAmount; // Текущая сумма накоплений
    private boolean achieved; // Достигнута ли цель

    public SavingsGoal(double targetAmount) {
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
        this.achieved = false;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void addAmount(double amount) {
        if (!achieved) {
            currentAmount += amount;
            if (currentAmount >= targetAmount) {
                achieved = true;
            }
        }
    }

    @Override
    public String toString() {
        return "Цель: " + targetAmount + ", Текущий прогресс: " + currentAmount + ", Достигнута: " + (achieved ? "Да" : "Нет");
    }
}

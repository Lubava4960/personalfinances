package org.example.model;

public class Budget {
    private double monthlyBudget;
    private double totalExpenses;

    public Budget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
        this.totalExpenses = 0.0;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public void addExpense(double amount) {
        totalExpenses += amount;
        checkBudget();
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    private void checkBudget() {
        if (totalExpenses > monthlyBudget) {
            System.out.println("Предупреждение: Вы превысили месячный бюджет на " + (totalExpenses - monthlyBudget) + " сумму.");
        }
    }


}

package org.example;

import org.example.model.SavingsGoal;
import org.example.model.User;
import org.example.service.GoalService;
import org.example.service.TransactionManager;
import org.example.service.TransactionService;
import org.example.service.UserService;

import java.util.Scanner;

public class FinanceManagerApp {
    public static void main(String[] args) {
        UserService userService = new UserService();
        TransactionService transactionService = new TransactionService();
        GoalService goalService = new GoalService();
        Scanner scanner = new Scanner(System.in);
        TransactionManager transactionManager = new TransactionManager(transactionService, goalService, scanner);

        User authenticatedUser = null;



        while (true) {
            if (authenticatedUser == null) {
                System.out.println("1. Регистрация");
                System.out.println("2. Вход");
                System.out.println("0. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Введите email:");
                        String email = scanner.nextLine();
                        System.out.println(" Введите пароль");
                        String password = scanner.nextLine();
                        System.out.println("Введите имя:");
                        String name = scanner.nextLine();

                        if (userService.register(email, password, name)) {
                            System.out.println("Регистрация прошла успешно!");
                        } else {
                            System.out.println("Ошибка: email уже занят.");
                        }
                        break;
                    case 2:

                        System.out.println("Введите email:");
                        String loginEmail = scanner.nextLine();
                        System.out.println("Введите пароль:");
                        String loginPassword = scanner.nextLine();

                        authenticatedUser = userService.authenticate(loginEmail, loginPassword);
                        if (authenticatedUser != null) {
                            System.out.println("Вход выполнен успешно! Добро пожаловать, " + authenticatedUser.getName());

                        } else {
                            System.out.println("Ошибка: неверный email или пароль.");
                        }
                        break;

                    case 0:
                        System.out.println("Выход из приложения.");
                        return;

                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } else {
                System.out.println("1. Редактировать профиль");
                System.out.println("2. Удалить аккаунт");
                System.out.println("3. Выйти из аккаунта");
                System.out.println("4. Управление транзакциями");
                System.out.println("0. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        //  редактирования профиля
                        System.out.println("Введите новое имя (оставьте пустым для пропуска):");
                        String newName = scanner.nextLine();
                        System.out.println("Введите новый email (оставьте пустым для пропуска):");
                        String newEmail = scanner.nextLine();
                        System.out.println("Введите новый пароль (оставьте пустым для пропуска):");
                        String newPassword = scanner.nextLine();

                        if (!newEmail.isEmpty() || !newPassword.isEmpty() || !newName.isEmpty()) {
                            if (!newEmail.isEmpty() && userService.authenticate(newEmail, authenticatedUser.getPassword()) != null) {
                                System.out.println("Ошибка: email уже занят.");
                            } else {
                                if (!newEmail.isEmpty()) {
                                    authenticatedUser.setEmail(newEmail);
                                }
                                if (!newPassword.isEmpty()) {
                                    authenticatedUser.setPassword(newPassword);
                                }
                                if (!newName.isEmpty()) {
                                }
                                if (!newPassword.isEmpty()) {
                                    authenticatedUser.setPassword(newPassword);
                                }
                                if (!newName.isEmpty()) {
                                    authenticatedUser.setName(newName);
                                }
                                System.out.println("Профиль успешно обновлен!");
                            }
                        } else {
                            System.out.println("Все поля пустые. Обновление отменено.");
                        }
                        break;

                    case 2:
                        //  удаления аккаунта
                        System.out.println("Вы уверены, что хотите удалить аккаунт? (да/нет):");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("да")) {
                            userService.deleteUser(authenticatedUser.getEmail());
                            System.out.println("Ваш аккаунт успешно удален.");
                            authenticatedUser = null; // Сбросить аутентификацию
                        } else {
                            System.out.println("Удаление аккаунта отменено.");
                        }
                        break;

                    case 3:
                        authenticatedUser = null; // Выйти из аккаунта
                        System.out.println("Вы вышли из аккаунта.");
                        break;

                    case 4:
                        System.out.println("Вы вошли в управление транзакциями");
                        transactionManager.manageTransactions();
                        break;
                    case 0:
                        System.out.println("Выход из приложения.");
                        return;

                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }


            }


        }
    }


}

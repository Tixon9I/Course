package my_coursework;// Пакети, які використовуються у програмі
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import my_coursework.GrafWindows.ExplanationWindow;
import my_coursework.GrafWindows.GUIAdmin;
import my_coursework.GrafWindows.OrderForm;
import my_coursework.Interface.DataInitializer;
import my_coursework.Interface.DataManager;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static my_coursework.GrafWindows.ExplanationWindow.closeWindow;

// Головний клас програми, що розширює Application від JavaFX
public class TestW extends Application {
    // Об'єкт для управління системою
    private static ControllerSystem controller;

    // Списки для збереження даних про замовлення, майстрів, аварії та клієнтів
    private static List<Order> ordersList;
    private static List<Master> mastersList;
    private static List<Accident> accidentsList;
    private static List<Client> clientsList;

    // Дати початку і кінця періоду роботи програми
    private static LocalDate startDate;
    private static LocalDate endDate;

    // Флаг для зміни опції роботи
    private static boolean Changeoption = false;

    // Об'єкт для головного вікна програми
    private static Stage primaryStage;

    // Флаг для показу пояснень роботи програми
    private static boolean explained = false;

    // Жорстко закодований пароль
    private static final String ADMIN_PASSWORD = "admin123";

    // Головний метод, який запускає програму
    public static void main(String[] args) {
        // Ініціалізація даних
        initializeData();
        Scanner scanner = new Scanner(System.in);

        // Флаг для виходу з програми
        boolean exitProgram = false;
        // Флаг для перевірки чи було пояснення роботи програми користувачу
        boolean programExplained = false;

        // Головний цикл програми
        while (!exitProgram) {
            // Перевірка, чи було пояснення програми
            if (!programExplained) {
                System.out.println("\nЛаскаво просимо! Оберіть опцію:");
                System.out.println("1. Ознайомтесь з можливостями програми");
                System.out.println("0. Вихід з програми\n");
                System.out.print("Введіть знак опції: ");

                // Отримання вводу користувача
                String userInput = scanner.next();
                System.out.println();

                try {
                    int userType = Integer.parseInt(userInput);

                    // Обробка введеного варіанту
                    switch (userType) {
                        case 1:
                            // Виклик методу для показу пояснень
                            showProgramExplanation();
                            programExplained = true;
                            break;
                        case 0:
                            System.out.println("Дякую за використання програми. До побачення!");
                            closeWindow(); // Виклик методу для закриття вікна
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Невірний вибір опції. Будь ласка, виберіть знову.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Невірний ввід. Будь ласка, введіть число.");
                }
            } else {
                System.out.println("Оберіть опцію:");
                System.out.println("1. Клієнт");
                System.out.println("2. Адміністратор");
                System.out.println("0. Вихід з програми\n");
                System.out.print("Введіть знак опції: ");

                // Отримання вводу користувача
                String userInput = scanner.next();
                System.out.println();

                try {
                    int userType = Integer.parseInt(userInput);

                    // Обробка введеного варіанту
                    switch (userType) {
                        case 1:
                            // Обробка меню клієнта
                            handleClientMenu(scanner);
                            break;
                        case 2:
                            // Перевірка пароля перед наданням доступу
                            System.out.println("Введіть пароль для адміністраторського доступу:");
                            String enteredPassword = scanner.next();
                            if (checkPassword(enteredPassword)) {
                                // Обробка меню адміністратора
                                handleAdminMenu(scanner);
                            } else {
                                System.out.println("Невірний пароль. Доступ заборонено.");
                            }
                            break;
                        case 0:
                            System.out.println("Дякую за використання програми. До побачення!");
                            closeWindow(); // Виклик методу для закриття вікна
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Невірний вибір опції. Будь ласка, виберіть знову.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Невірний ввід. Будь ласка, введіть число.");
                }
            }
        }
    }

    // Перевизначення методу start, який викликається при запуску JavaFX додатка
    @Override
    public void start(Stage primaryStage) {
        // Встановлюємо головний етап для класу, щоб мати доступ до нього в інших методах
        TestW.primaryStage = primaryStage;

        // Перевірка умови Changeoption
        if (Changeoption) {
            // Якщо Changeoption істинний, викликаємо метод для відображення форми замовлення
            showOrderForm();
            // Позначаємо, що Changeoption більше не активний
            Changeoption = false;
        } else {
            // Якщо Changeoption хибний, викликаємо метод для відображення вікна адміністратора
            showAdminWindow();
        }
    }

    // Метод для відображення форми замовлення
    private static void showOrderForm() {
        // Створюємо об'єкт форми замовлення з передачею списків майстрів, замовлень, клієнтів та нещасних випадків
        OrderForm orderForm = new OrderForm(mastersList, ordersList, clientsList, accidentsList);
        // Викликаємо метод start для відображення форми
        orderForm.start(new Stage());
    }

    // Метод для відображення вікна адміністратора
    private static void showAdminWindow() {
        // Створюємо об'єкт графічного інтерфейсу адміністратора з передачею списків майстрів, замовлень, клієнтів та нещасних випадків
        GUIAdmin guiAdmin = new GUIAdmin(mastersList, ordersList, clientsList, accidentsList);
        // Викликаємо метод start для відображення вікна адміністратора
        guiAdmin.start(new Stage());
    }

    // Метод для відображення вікна пояснення роботи програми
    private static void showProgramExplanation() {
        // Перевірка, чи вже вікно пояснення не було показано
        if (!explained) {
            // Запускаємо виконання коду на головному потоці JavaFX для відображення вікна пояснення
            Platform.runLater(() -> {
                ExplanationWindow explanationWindow = new ExplanationWindow();
                explanationWindow.start(new Stage());
            });

            // Позначаємо, що пояснення вже було показано
            explained = true;
        } else {
            // Виводимо повідомлення, що пояснення вже відбулося
            System.out.println("Ви вже ознайомилися з кроками роботи програми.");
        }
    }

    // Метод для обробки меню для клієнта
    private static void handleClientMenu(Scanner scanner) {
        // Флаг для виходу до головного меню
        boolean backToMain = false;

        // Цикл для обробки опцій меню клієнта
        while (!backToMain) {
            // Виведення опцій меню для клієнта
            System.out.println("Для клієнта:");
            System.out.println("1. Оформити замовлення");
            System.out.println("2. Перевірка стану замовлення");
            System.out.println("3. Повернутись до головного меню");
            System.out.println("0. Вихід з програми\n");
            System.out.print("Введіть знак опції: ");

            try {
                // Зчитування вибору клієнта
                int clientOption = scanner.nextInt();
                scanner.nextLine(); // Додавання цього рядка для зчитування символу нового рядка
                System.out.println();

                // Обробка вибору клієнта за допомогою конструкції switch
                switch (clientOption) {
                    case 1:
                        // Виклик методу для оформлення замовлення
                        boolean changeOption = true;
                        Changeoption = changeOption;
                        Platform.runLater(() -> showOrderForm());
                        break;
                    case 2:
                        // Виклик методу для перевірки статусу замовлення
                        // Запросити введення ПІБ клієнта від користувача (використовуйте Scanner або інший механізм введення)
                        System.out.println("Введіть ПІБ клієнта:");
                        String clientFullNameInput = scanner.nextLine();

                        // Отримання списку замовлень за введеним ПІБ клієнта
                        List<Order> ordersByClientFullName = Order.getOrdersByClientFullName(clientFullNameInput, ordersList);

                        // Перевірка наявності замовлень для введеного ПІБ клієнта
                        if (!ordersByClientFullName.isEmpty()) {
                            // Виведення інформації про кожне замовлення клієнта
                            System.out.println("\nСтан замовлень для клієнта " + clientFullNameInput + ":");
                            for (Order order : ordersByClientFullName) {
                                order.printInfo();  // Виклик методу для виведення інформації про замовлення
                                System.out.println("Стан замовлення: Виконано\n");
                            }
                        } else {
                            System.out.println("Замовлення для введеного ПІБ клієнта не знайдено" + "\n");
                        }
                        break;
                    case 3:
                        backToMain = true;
                        break;
                    case 0:
                        System.out.println("Дякую за використання програми. До побачення!");
                        closeWindow(); // Виклик методу для закриття вікна
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Невірний вибір опції. Будь ласка, виберіть знову.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Некоректний ввід. Будь ласка, введіть ціле число.");
                scanner.nextLine(); // Очищення буфера введення
            }
        }
    }


    // Метод для обробки введення адміністраторського меню
    private static void handleAdminMenu(Scanner scanner) {
        boolean backToMain = false;

        // Безкінечний цикл, доки користувач не вибере повернутися до головного меню
        while (!backToMain) {
            // Виведення опцій адміністраторського меню
            System.out.println();
            System.out.println("Для Адміністратора:");
            System.out.println("1. Перегляд/Додавання");
            System.out.println("2. Кількість майстрів");
            System.out.println("3. Кількість аварій з... по...");
            System.out.println("4. Середня вартість усунення аварій");
            System.out.println("5. Типи аварій");
            System.out.println("6. Заробіток майстрів з... по...");
            System.out.println("7. Кількість бригад");
            System.out.println("8. Повернутись до головного меню");
            System.out.println("0. Вихід з програми\n");
            System.out.print("Введіть знак опції: ");

            try {
                // Зчитування вибору користувача
                int adminOption = scanner.nextInt();
                System.out.println();

                // Обробка вибору користувача за допомогою switch
                switch (adminOption) {
                    case 1:
                        // Виклик методу для перегляду/додавання
                        Platform.runLater(() -> showAdminWindow());
                        backToMain = true;
                        break;
                    case 2:
                        // Виклик методу для отримання кількості майстрів
                        int numberOfMasters = controller.countMasters();  //Кількість майстрів
                        System.out.println("Кількість майстрів: " + numberOfMasters);
                        break;
                    case 3:
                        // Виклик методу для отримання кількості аварій у заданому діапазоні дат
                        LocalDate startDate1 = null;
                        boolean invalidInputStartDate = false;

                        while (startDate1 == null) {
                            // Обробка введення початкової дати
                            if (invalidInputStartDate) {
                                System.out.println("Некоректний ввід. Будь ласка, введіть дату.");
                            } else {
                                System.out.println("Введіть початкову дату у форматі YYYY-MM-DD:");
                            }

                            String startDateString = scanner.next();

                            // Перевірка наявності пробілів
                            if (startDateString.contains(" ")) {
                                invalidInputStartDate = true;
                                continue;
                            }

                            try {
                                startDate1 = LocalDate.parse(startDateString);
                            } catch (DateTimeParseException e) {
                                invalidInputStartDate = true;
                            }
                        }

                        // Запит на введення кінцевої дати
                        LocalDate endDate1 = null;
                        boolean invalidInputEndDate = false;

                        while (endDate1 == null || endDate1.isBefore(startDate1)) {
                            // Обробка введення кінцевої дати
                            if (invalidInputEndDate) {
                                System.out.println("Некоректний ввід. Будь ласка, введіть дату.");
                            } else {
                                System.out.println("Введіть кінцеву дату у форматі YYYY-MM-DD:");
                            }

                            String endDateString = scanner.next();

                            // Перевірка наявності пробілів
                            if (endDateString.contains(" ")) {
                                invalidInputEndDate = true;
                                continue;
                            }

                            try {
                                endDate1 = LocalDate.parse(endDateString);
                            } catch (DateTimeParseException e) {
                                invalidInputEndDate = true;
                            }
                        }

                        // Виклик методу для отримання кількості аварій у заданому діапазоні дат
                        int countAccidents = controller.countAccidentsInDateRange(startDate1, endDate1);
                        System.out.printf("\nКількість аварій з %s по %s: %s %n", startDate1, endDate1, countAccidents);
                        break;

                    case 4:
                        // Виклик методу для отримання середньої вартості усунення аварій
                        double avgEliminationCost = controller.calculateAverageEliminationCost();  // Середня вартість усунення аварії
                        String formattedValue = String.format("%.2f", avgEliminationCost);
                        System.out.println("Середня вартість усунення аварій: " + formattedValue + " грн");
                        break;
                    case 5:
                        // Виклик методу для отримання типів аварій
                        Set<String> uniqueAccidentTypes = controller.getUniqueAccidentTypes(accidentsList);  // Унікальні типи аварій
                        System.out.println("Унікальні типи аварій: " + uniqueAccidentTypes);
                        break;
                    case 6:
                        // Введення діапазону дат для розрахунку заробітку майстрів
                        LocalDate startDate2 = null;
                        boolean invalidInputStartDate1 = false;

                        while (startDate2 == null) {
                            // Обробка введення початкової дати
                            if (invalidInputStartDate1) {
                                System.out.println("Некоректний ввід. Будь ласка, введіть дату.");
                            } else {
                                System.out.println("Введіть початкову дату у форматі YYYY-MM-DD:");
                            }

                            String startDateString = scanner.next();

                            // Перевірка наявності пробілів
                            if (startDateString.contains(" ")) {
                                invalidInputStartDate1 = true;
                                continue;
                            }

                            try {
                                startDate2 = LocalDate.parse(startDateString);
                            } catch (DateTimeParseException e) {
                                invalidInputStartDate1 = true;
                            }
                        }

                        // Запит на введення кінцевої дати
                        LocalDate endDate2 = null;
                        boolean invalidInputEndDate1 = false;

                        while (endDate2 == null) {
                            // Обробка введення кінцевої дати
                            if (invalidInputEndDate1) {
                                System.out.println("Некоректний ввід. Будь ласка, введіть дату.");
                            } else {
                                System.out.println("Введіть кінцеву дату у форматі YYYY-MM-DD:");
                            }

                            String endDateString = scanner.next();

                            // Перевірка наявності пробілів
                            if (endDateString.contains(" ")) {
                                invalidInputEndDate1 = true;
                                continue;
                            }

                            try {
                                endDate2 = LocalDate.parse(endDateString);
                            } catch (DateTimeParseException e) {
                                invalidInputEndDate1 = true;
                            }
                        }

                        // Викликаємо метод calculateEarningsForMaster для розрахунку заробітку конкретного майстра
                        Map<Master, Double> earningsMap = new HashMap<>();

                        for (Order order : ordersList) {
                            double earnings = order.calculateEarningsInDateRange(startDate2, endDate2);
                            Master master = order.getMaster();

                            // Додаємо заробіток до відповідного майстра в мапі
                            earningsMap.merge(master, earnings, Double::sum);
                        }

                        System.out.println();
                        // Виводимо результати
                        for (Map.Entry<Master, Double> entry : earningsMap.entrySet()) {
                            Master master = entry.getKey();
                            double earnings = entry.getValue();
                            String fullName = master.getFullName().FullName();

                            System.out.printf("Майстер: %s, Заробіток: %.2f грн%n", fullName, earnings);
                        }
                        break;
                    case 7:
                        // Виклик методу для отримання кількості бригад
                        int numberOfBrigades = controller.countBrigades(ordersList);  // Кількість бригад
                        System.out.println("Кількість бригад: " + numberOfBrigades);
                        break;
                    case 8:
                        backToMain = true;
                        break;
                    case 0:
                        System.out.println("Дякую за використання програми. До побачення!");
                        closeWindow(); // Виклик методу для закриття вікна
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Невірний вибір опції. Будь ласка, виберіть знову.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Некоректний ввід. Будь ласка, введіть ціле число.");
                scanner.nextLine(); // Очистити буфер введення
            }
        }
    }


    // Метод для перевірки пароля
    private static boolean checkPassword(String enteredPassword) {
        return enteredPassword.equals(ADMIN_PASSWORD);
    }

    // метод для ініціалізації даних
    private static void initializeData() {
        // Ініціалізуємо дані
        DataInitializer.initializeAndSaveData();

        // Завантажуємо дані
        DataManager dataManager = new DataManager();
        List<Master> masters = dataManager.loadMasters();
        List<Client> clients = dataManager.loadClients();
        List<Accident> accidents = dataManager.loadAccidents();
        List<Order> orders = dataManager.loadOrders();

        // Ініціалізуємо контроллер та списки об'єктів
        controller = new ControllerSystem(orders, masters, accidents);
        ordersList = orders;
        mastersList = masters;
        clientsList = clients;
        accidentsList = accidents;
        // Ініціалізуємо дати
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2023, 10, 12);
    }
}

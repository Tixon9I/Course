package my_coursework.abstractC;

import my_coursework.Composition;
import my_coursework.Order;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

// Клас Master розширює абстрактний клас Person та реалізує інтерфейс Serializable
public class Master extends Person implements Serializable {

    // Унікальний ідентифікатор для майстра
    private UUID masterId;

    // Стаж майстра
    private int workExperience;

    // Номер бригади
    private int teamNumber;

    // Стан майстра (зайнятий або вільний)
    private boolean state = true;

    // Набір для зберігання вже використаних номерів бригад
    private static final Set<Integer> usedTeamNumbers = new HashSet<>();

    // Список замовлень майстра
    private List<Order> orders;

    // Конструктор класу з параметрами
    public Master(Composition fullName, Composition address, Composition mobileNum, int departmentNumber, int workEx) {
        super(fullName, address, mobileNum, departmentNumber);
        this.workExperience = workEx;
        this.teamNumber = generateRandomTeamNumber(); // Генерація випадкового номера бригади
        this.masterId = UUID.randomUUID(); // Генерація унікального ідентифікатора
        this.orders = new ArrayList<>(); // Ініціалізація списку замовлень
    }

    // Конструктор класу з параметрами, включаючи номер бригади
    public Master(Composition fullName, Composition address, Composition mobileNum, int departmentNumber, int workEx, int teamN) {
        super(fullName, address, mobileNum, departmentNumber);
        this.workExperience = workEx;
        this.teamNumber = teamN;
        this.masterId = UUID.randomUUID(); // Генерація унікального ідентифікатора
        this.orders = new ArrayList<>(); // Ініціалізація списку замовлень
    }

    // Конструктор без параметрів для серіалізації
    public Master() {}

    // Метод для генерації випадкового номера бригади
    private int generateRandomTeamNumber() {
        Random rand = new Random();
        int teamNumber;

        while (true) {
            teamNumber = rand.nextInt(10) + 1; // Генерація від 1 до 10

            // Перевірка, чи цей номер бригади не використовується іншим майстром
            if (!usedTeamNumbers.contains(teamNumber)) {
                usedTeamNumbers.add(teamNumber);
                break;
            }
        }

        return teamNumber;
    }

    // Геттер для отримання стажу майстра
    public int getWorkExperience() {return workExperience;}

    // Геттер для отримання номера бригади майстра
    public int getTeamNumber() {return teamNumber;}

    // Геттер для отримання унікального ідентифікатора майстра
    public UUID getMasterId() {return masterId;}

    // Геттер для отримання стану майстра (зайнятий чи вільний)
    public boolean isState() {return state;}

    // Сеттер для встановлення стану майстра
    public void setState(boolean state) {this.state = state;}

    // Метод для додавання замовлення до списку замовлень майстра
    public void addOrder(Order order) {orders.add(order);}

    // Геттер для отримання списку замовлень майстра
    public List<Order> getOrderList() {return orders;}

    // Метод для виведення інформації про майстра в консоль
    public void printInfo() {
        System.out.println("ПІБ: " + getFullName().FullName());
        System.out.println("Адреса: " + getAddress().Address());
        System.out.println("Номер телефону: " + getMobileNum().MobileNumber());
        System.out.println("Номер відділення: " + getDepartmentNumber());
        System.out.println("Стаж: " + getWorkExperience() + " років");
        System.out.println("Номер бригади: " + getTeamNumber());
        System.out.println("Стан майстра: " + (state ? "Зайнятий" : "Вільний"));
        System.out.println();
    }

    // Перевизначений метод toString для отримання рядкового представлення об'єкта
    @Override
    public String toString() {
        // Перетворення списку замовлень в рядок
        StringBuilder ordersString = new StringBuilder();
        if (this.orders != null) {
            for (Order order : this.orders) {
                ordersString.append(order.toString()).append("\n");
            }
        }

        // Визначення стану майстра
        String masterState = (this.orders != null && !this.orders.isEmpty()) ? "Зайнятий" : "Вільний";

        // Створення результуючого рядка
        String ordersInfo = this.orders != null && this.orders.isEmpty()
                ? "Майстер не має замовлень"
                : ordersString.toString();

        return "ПІБ: " + getFullName().FullName() + "\n" +
                "Адреса: " + getAddress().Address() + "\n" +
                "Номер телефону: " + getMobileNum().MobileNumber() + "\n" +
                "Номер відділення: " + getDepartmentNumber() + "\n" +
                "Стаж: " + workExperience + "\n" +
                "Номер бригади: " + teamNumber + "\n" +
                "Стан майстра: " + masterState + "\n" +
                "Кількість замовлень: " + (this.orders != null ? this.orders.size() : 0) + "\n\n" + ordersInfo;
    }

    // Сеттери для встановлення значень полів
    public void setFullName(Composition fullName) {this.FullName = fullName;}

    public void setAddress(Composition address) {this.Address = address;}

    public void setMobileNum(Composition mobileNum) {this.MobileNum = mobileNum;}

    public void setDepartmentNumber(int departmentNumber) {this.departmentNumber = departmentNumber;}

    // Метод для серіалізації об'єкта
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // Спочатку викликайте defaultWriteObject
        out.writeObject(getFullName()); // Викликайте writeObject для об'єкта Composition
        out.writeObject(getAddress());
        out.writeObject(getMobileNum());
        out.writeObject(getDepartmentNumber());
    }

    // Метод для десеріалізації об'єкта
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Спочатку викликайте defaultReadObject
        setFullName((Composition) in.readObject()); // Викликайте readObject і встановлюйте значення
        setAddress((Composition) in.readObject());
        setMobileNum((Composition) in.readObject());
        setDepartmentNumber((int) in.readObject());
    }
}

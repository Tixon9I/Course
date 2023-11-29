package my_coursework;

import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable
{
    private static int nextOrderNumber = 1;  // Наступний номер замовлення
    private int orderNumber;  // Номер замовлення
    private Composition clientFullName;  // ПІБ клієнта
    private Composition clientPhoneNumber;  // мобільний телефон клієнта
    private Composition clientAddress;  // Адреса клієнта
    private int departmentNumber;  // Номер відділення
    private int teamNumber;  // номер бригади
    private int accidentId;  // Id аварії
    private int eliminationCost;  // Вартість усунення аварії
    private LocalDate date;  // Дата
    private List<Order> orderList = new ArrayList<>(); // Список замовлень
    private Master master;  // об'єкт класу Master
    private boolean stateAccident; // Стан аварії

    // Конструктор класу Order, приймає інформацію про замовлення та його майстра.
    public Order(Composition clientFullName, Master master, Composition clientPhoneNumber, Composition clientAddress,
                 int departmentNumber, int teamNumber, int accidentId, int eliminationCost, LocalDate date, Accident accident)
    {
        this.orderNumber = nextOrderNumber++; // Номер замовлення присвоюється та збільшується для наступного
        this.clientFullName = clientFullName;
        this.master = master; // Встановлюємо майстра для замовлення
        this.clientPhoneNumber = clientPhoneNumber;
        this.clientAddress = clientAddress;
        this.departmentNumber = departmentNumber;
        this.teamNumber = teamNumber;
        this.accidentId = accidentId;
        this.eliminationCost = eliminationCost;
        this.date = date;
        orderList.add(this); // Додаємо поточне замовлення до списку замовлень
        master.addOrder(this); // Додаємо поточне замовлення до списку замовлень майстра
        this.stateAccident = accident.getStateAccident(); // Встановлюємо стан аварії відповідно до стану аварії з об'єкта Accident
    }

    // Метод для автоматичного створення об'єкта Order з інформацією з об'єктів Client, Master і Accident.
    public static Order createOrder(Client client, Master master, Accident accident, LocalDate date)
    {
        Composition clientFullName = client.getFullName();
        Composition clientPhoneNumber = client.getMobileNum();
        Composition clientAddress = client.getAddress();
        int departmentNumber = master.getDepartmentNumber();
        int teamNumber = master.getTeamNumber();
        int accidentId = accident.getAccidentId();
        int eliminationCost = accident.getCostOfElimination();

        if (master.isState()) {
            master.setState(false); // Якщо майстер вільний, то встановлюємо його стан як "зайнятий"
        } else {
            master.setState(false);  // Якщо майстер зайнятий, стан залишається "зайнятий"
        }

        return new Order(clientFullName, master, clientPhoneNumber, clientAddress, departmentNumber, teamNumber, accidentId, eliminationCost, date, accident);
    }

    // Метод для встановлення стану аварії
    public void setStateAccident(boolean stateAccident) {this.stateAccident = stateAccident;}

    // Метод для отримання майстра
    public Master getMaster() {
        return master;
    }

    // Метод для отримання вартості усунення аварії
    public int getEliminationCost() {
        return eliminationCost;
    }

    // Метод для отримання дати замовлення
    public LocalDate getOrderDate() {return date;}

    // Метод для розрахунку заробітку майстра в заданому діапазоні дат
    public double calculateEarningsInDateRange(LocalDate startDate, LocalDate endDate)
    {
        double earnings = 0.0;
        LocalDate orderDate = this.date;

        if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate))
        {
            // Розраховуємо заробіток для майстра, яке входить в діапазон дат
            earnings = this.getEliminationCost() * 0.20; // Від усунення аварії майстер отримує 20%
        }

        return earnings;
    }

    // Метод для отримання замовлення за ПІБ клієнта
    public static List<Order> getOrdersByClientFullName(String clientFullName, List<Order> orderList) {
        List<Order> ordersByClientFullName = new ArrayList<>();

        for (Order order : orderList) {
            if (order.clientFullName.FullName().equals(clientFullName)) {
                ordersByClientFullName.add(order);
            }
        }

        return ordersByClientFullName;
    }


    // Метод для виведення інформації про замовлення
    public void printInfo()
    {
        System.out.println("Номер замовлення: " + orderNumber);
        System.out.println("ПІБ клієнта: " + clientFullName.FullName());
        System.out.println("ПІБ майстра: " + master.getFullName().FullName());
        System.out.println("Номер телефону клієнта: " + clientPhoneNumber.MobileNumber());
        System.out.println("Адреса клієнта: " + clientAddress.Address());
        System.out.println("Номер відділення: " + departmentNumber);
        System.out.println("Номер бригади: " + teamNumber);
        System.out.println("ID аварії: " + accidentId);
        System.out.println("Вартість усунення аварії: " + eliminationCost);
        System.out.println("Дата: " + date);
        System.out.println();
    }

    // Перевизначений метод toString для отримання рядкового представлення замовлення
    @Override
    public String toString() {
        return "Замовлення № " + orderNumber + "\n" +
                "ПІБ клієнта: " + clientFullName.FullName() + "\n" +
                "ПІБ майстра: " + master.getFullName().FullName() + "\n" +
                "Телефон клієнта: " + clientPhoneNumber.MobileNumber() + "\n" +
                "Адреса клієнта: " + clientAddress.Address() + "\n" +
                "Номер відділення: " + departmentNumber + "\n" +
                "Номер бригади: " + teamNumber + "\n" +
                "ID аварії: " + accidentId + "\n" +
                "Вартість усунення аварії: " + eliminationCost + "\n" +
                "Дата: " + date + "\n";
    }
}

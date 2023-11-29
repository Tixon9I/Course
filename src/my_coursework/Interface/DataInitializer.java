package my_coursework.Interface;

import my_coursework.Accident;
import my_coursework.Composition;
import my_coursework.Order;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer
{
    public static void initializeAndSaveData() {
        // Створюємо об'єкти майстрів
        Master master1 = new Master(new Composition("Іванов", "Антон", "Сегійович"),
                new Composition("Сидир", "2в"), new Composition(50, 2040333), 1, 5);

        Master master2 = new Master(new Composition("Адруанов", "Сергій", "Петрович"),
                new Composition("Гамдав", "4а"), new Composition(50, 4034234), 2, 7);

        Master master3 = new Master(new Composition("Балан", "Микола", "Миколайович"),
                new Composition("Грушевський", "7г"), new Composition(68, 9875323), 3, 2);

        // Створюємо список майстрів
        List<Master> masters = new ArrayList<>();
        masters.add(master1);
        masters.add(master2);
        masters.add(master3);

        // Створюємо об'єкти клієнтів
        Client client1 = new Client(new Composition("Сидоренко", "Віталіна", "Петрівна"),
                new Composition("Волстріт", "12а"), new Composition(63, 1032432), 3, 111);

        Client client2 = new Client(new Composition("Петренко", "Марина", "Сергіївна"),
                new Composition("Лулпіш", "1"), new Composition(98, 1235436), 4, 222);

        Client client3 = new Client(new Composition("Грозлива", "Марія", "Андріївна"),
                new Composition("Братів Грімм", "2д"), new Composition(50, 2346635), 4, 222);

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);

        // Створюємо об'єкти аварій
        Accident accident1 = new Accident(client1, LocalDate.of(2023, 5, 12));
        Accident accident2 = new Accident(client2, LocalDate.of(2023, 1, 10));
        Accident accident3 = new Accident(client3, LocalDate.of(2023, 10, 8));
        client1.setAccidentId(accident1.getAccidentId());
        client2.setAccidentId(accident2.getAccidentId());
        client3.setAccidentId(accident3.getAccidentId());

        // Створюємо список аварій
        List<Accident> accidents = new ArrayList<>();
        accidents.add(accident1);
        accidents.add(accident2);
        accidents.add(accident3);

        // Створюємо замовлення
        Order order1 = Order.createOrder(client1, master1, accident1, LocalDate.of(2005, 10, 14));
        Order order2 = Order.createOrder(client2, master2, accident2, LocalDate.of(2005, 1, 11));
        Order order3 = Order.createOrder(client3, master3, accident3, LocalDate.of(2005, 10, 10));

        // Створюємо список замовлень
        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);


        // Створюємо об'єкт FileDataHandler
        FileDataHandler fileDataHandler = new FileDataHandler();

        // Зберігаємо дані у файли
        fileDataHandler.saveMastersToFile(masters, "C:\\Users\\Home\\IdeaProjects\\Course\\src\\my_coursework\\Files\\masters.dat");
        fileDataHandler.saveClientsToFile(clients, "C:\\Users\\Home\\IdeaProjects\\Course\\src\\my_coursework\\Files\\clients.dat");
        fileDataHandler.saveAccidentsToFile(accidents, "C:\\Users\\Home\\IdeaProjects\\Course\\src\\my_coursework\\Files\\accidents.dat");
        fileDataHandler.saveOrdersToFile(orders, "C:\\Users\\Home\\IdeaProjects\\Course\\src\\my_coursework\\Files\\orders.dat");
    }
}

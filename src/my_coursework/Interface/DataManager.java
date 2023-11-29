// Пакет, в якому знаходиться цей клас
package my_coursework.Interface;

// Імпорт необхідних класів з інших пакетів
import my_coursework.Accident;
import my_coursework.Order;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.util.List;

// Клас для управління зберіганням та завантаженням даних
public class DataManager
{
    // Шляхи до файлів для зберігання даних
    private static final String MASTERS_FILE = "C:\\Users\\Home\\IdeaProjects\\Study_\\src\\my_coursework\\Files\\masters.dat";
    private static final String CLIENTS_FILE = "C:\\Users\\Home\\IdeaProjects\\Study_\\src\\my_coursework\\Files\\clients.dat";
    private static final String ACCIDENTS_FILE = "C:\\Users\\Home\\IdeaProjects\\Study_\\src\\my_coursework\\Files\\accidents.dat";
    private static final String ORDERS_FILE = "C:\\Users\\Home\\IdeaProjects\\Study_\\src\\my_coursework\\Files\\orders.dat";

    // Об'єкт для обробки даних (збереження та завантаження)
    private DataHandler dataHandler;

    // Конструктор класу, встановлює об'єкт dataHandler як FileDataHandler
    public DataManager() {
        this.dataHandler = new FileDataHandler();
    }

    // Метод для збереження даних у файли
    public void saveData(List<Master> masters, List<Client> clients, List<Accident> accidents, List<Order> orders) {
        dataHandler.saveMastersToFile(masters, MASTERS_FILE);
        dataHandler.saveClientsToFile(clients, CLIENTS_FILE);
        dataHandler.saveAccidentsToFile(accidents, ACCIDENTS_FILE);
        dataHandler.saveOrdersToFile(orders, ORDERS_FILE);
    }

    // Метод для завантаження списку майстрів з файлу
    public List<Master> loadMasters() {
        return dataHandler.loadMastersFromFile(MASTERS_FILE);
    }

    // Метод для завантаження списку клієнтів з файлу
    public List<Client> loadClients() {
        return dataHandler.loadClientsFromFile(CLIENTS_FILE);
    }

    // Метод для завантаження списку аварій з файлу
    public List<Accident> loadAccidents() {
        return dataHandler.loadAccidentsFromFile(ACCIDENTS_FILE);
    }

    // Метод для завантаження списку замовлень з файлу
    public List<Order> loadOrders() {
        return dataHandler.loadOrdersFromFile(ORDERS_FILE);
    }
}

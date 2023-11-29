// Оголошення пакетів та імпортів, що використовуються в класі
package my_coursework.Interface;

import my_coursework.Accident;
import my_coursework.Order;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.io.*;
import java.util.List;

// Клас, що реалізує інтерфейс DataHandler та відповідає за зберігання та завантаження даних у/з файлу
public class FileDataHandler implements DataHandler {

    // Метод для збереження списку замовлень у файл
    @Override
    public void saveOrdersToFile(List<Order> orders, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для збереження списку майстрів у файл
    @Override
    public void saveMastersToFile(List<Master> masters, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(masters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для збереження списку аварій у файл
    @Override
    public void saveAccidentsToFile(List<Accident> accidents, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(accidents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для збереження списку клієнтів у файл
    @Override
    public void saveClientsToFile(List<Client> clients, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(clients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для завантаження списку замовлень з файлу
    @Override
    public List<Order> loadOrdersFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод для завантаження списку майстрів з файлу
    @Override
    public List<Master> loadMastersFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Master>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод для завантаження списку аварій з файлу
    @Override
    public List<Accident> loadAccidentsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Accident>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод для завантаження списку клієнтів з файлу
    @Override
    public List<Client> loadClientsFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Client>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

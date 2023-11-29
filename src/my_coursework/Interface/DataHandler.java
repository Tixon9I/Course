package my_coursework.Interface;
import my_coursework.Accident;
import my_coursework.Order;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.util.List;
interface DataHandler
{
    // Метод для запису даних
    void saveOrdersToFile(List<Order> orders, String filename);

    void saveMastersToFile(List<Master> masters, String filename);

    void saveAccidentsToFile(List<Accident> accidents, String filename);
    void saveClientsToFile(List<Client> clients, String filename);

    // Метод для завантаження даних
    List<Order> loadOrdersFromFile(String filename);

    List<Master> loadMastersFromFile(String filename);

    List<Accident> loadAccidentsFromFile(String filename);
    List<Client> loadClientsFromFile(String filename);
}

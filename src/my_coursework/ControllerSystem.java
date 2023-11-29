// Пакети, які імпортуються для використання в класі ControllerSystem.
package my_coursework;

import my_coursework.abstractC.Master;

import java.time.LocalDate;
import java.util.*;

// Клас, який відповідає за контролер системи
public class ControllerSystem {
    // Список для зберігання замовлень.
    private List<Order> orderList = new ArrayList<>();
    // Список для зберігання майстрів.
    private List<Master> masterList = new ArrayList<>();
    // Список для зберігання аварій.
    private List<Accident> accidentList = new ArrayList<>();

    // Конструктор класу ControllerSystem для ініціалізації списків замовлень, майстрів та аварій.
    public ControllerSystem(List<Order> orders, List<Master> masters, List<Accident> accidents) {
        this.orderList = orders;
        this.masterList = masters;
        this.accidentList = accidents;
    }

    // 1. Метод для підрахунку кількості унікальних майстрів.
    public int countMasters() {
        Set<Master> uniqueMasters = new HashSet<>();

        // Проходження через список замовлень та додавання унікальних майстрів до множини.
        for (Order order : orderList) {
            uniqueMasters.add(order.getMaster());
        }

        return uniqueMasters.size();
    }

    // 2. Метод для підрахунку кількості аварій в заданому діапазоні дат.
    public int countAccidentsInDateRange(LocalDate startDate, LocalDate endDate) {
        int count = 0;

        // Проходження через список аварій та перевірка дати на входження в заданий діапазон.
        for (Accident accident : accidentList) {
            LocalDate accidentDate = accident.getDate();
            if (accidentDate.isAfter(startDate) && accidentDate.isBefore(endDate)) {
                count++;
            }
        }

        return count;
    }

    // 3. Метод для розрахунку середньої вартості усунення аварій.
    public double calculateAverageEliminationCost() {
        if (orderList.isEmpty()) {
            return 0.0;
        }

        double totalCost = 0.0;
        // Підрахунок загальної вартості та поділ на кількість замовлень.
        for (Order order : orderList) {
            totalCost += order.getEliminationCost();
        }

        return totalCost / orderList.size();
    }

    // 4. Метод для отримання унікальних типів аварій.
    public Set<String> getUniqueAccidentTypes(List<Accident> accidents) {
        Set<String> uniqueTypes = Accident.getUniqueAccidentTypes(accidents);
        return uniqueTypes;
    }


    // 6. Метод для підрахунку кількості унікальних бригад за допомогою переданого списку замовлень.
    public int countBrigades(List<Order> orders) {
        Set<Integer> uniqueTeamNumbers = new HashSet<>();

        // Проходження через список замовлень та додавання унікальних номерів бригад до множини.
        for (Order order : orders) {
            // Отримуємо номер бригади з кожного замовлення
            int teamNumber = order.getMaster().getTeamNumber();
            uniqueTeamNumbers.add(teamNumber);
        }

        return uniqueTeamNumbers.size();
    }
}

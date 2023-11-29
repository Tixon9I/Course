package my_coursework;
import my_coursework.abstractC.Client;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;

public class Accident implements Serializable
{
    private Composition Address;  // Адреса
    private boolean stateAccident; // Стан аварії буде реалізовано пізніше
    private LocalDate Date;  // Дата
    private String typeAccident; // Тип аварії
    private int costOfElimination; //Вартість усунення
    private int accidentId;  // Id аварії

    private static List<Integer> accidentIds = new ArrayList<>(); // ID аварій
    private static Map<String, Integer> typeToCostMap = new HashMap<>(); // Вартість усунення аварій
    public static Map<String, Integer> typeToIdMap = new HashMap<>(); // Типи аварій з ID

    static {
        typeToCostMap.put("Поломка насосу", 10000);
        typeToCostMap.put("Витік води", 1500);
        typeToCostMap.put("Пошкодження водопровідних труб", 2000);
        typeToCostMap.put("Прорив водопровідної лінії", 3000);
        typeToCostMap.put("Поломка насосної станції для водопостачання", 12000);
        typeToCostMap.put("Затор каналізації через накопичення відходів", 2500);
        typeToCostMap.put("Аварія в системі опалення", 8000);
        typeToCostMap.put("Поломка в системі водоочищення", 5000);

        // Додамо унікальний ідентифікатор для кожного типу аварії
        typeToIdMap.put("Поломка насосу", 111);
        typeToIdMap.put("Витік води", 222);
        typeToIdMap.put("Пошкодження водопровідних труб", 333);
        typeToIdMap.put("Прорив водопровідної лінії", 444);
        typeToIdMap.put("Поломка насосної станції для водопостачання", 555);
        typeToIdMap.put("Затор каналізації через накопичення відходів", 666);
        typeToIdMap.put("Аварія в системі опалення", 777);
        typeToIdMap.put("Поломка в системі водоочищення", 888);
    }

    // Конструктор для генерації аварії з випадковим типом
    public Accident(Client client, LocalDate date){
        this.Address = client.getAddress();
        this.Date = date;
        this.typeAccident = getRandomAccidentType();
        this.accidentId = typeToIdMap.get(this.typeAccident);
        this.costOfElimination = getCostOfElimination();
    }

    // Конструктор для створення аварії з вказаним типом
    public Accident(Client client, LocalDate date, String accidentType) {
        this.Address = client.getAddress();
        this.Date = date;
        this.typeAccident = accidentType;
        this.accidentId = typeToIdMap.get(this.typeAccident);
        this.costOfElimination = getCostOfElimination();
        this.stateAccident = false; // При створенні аварії стан встановлюється в "в стані виконання"
    }

    // Метод для встановлення стану аварії
    public void setStateAccident(boolean stateAccident) {
        this.stateAccident = stateAccident;
    }

    // Метод для отримання стану аварії
    public boolean getStateAccident() {
        return stateAccident;
    }

    // Генерує рандомний тип аварії
    public String getRandomAccidentType() {
        Random rand = new Random();
        List<String> keys = new ArrayList<>(typeToCostMap.keySet());
        int randomIndex = rand.nextInt(keys.size());
        return keys.get(randomIndex);
    }

    // Метод для отримання ідентифікатора аварії
    public int getAccidentId() {
        return accidentId;
    }

    // Метод для отримання списку всіх ідентифікаторів аварій
    public static List<Integer> getAllAccidentIds() {
        return accidentIds;
    }

    // Метод для отримання типу аварії
    public String getType() {
        return typeAccident;
    }

    // Метод для отримання дати аварії
    public LocalDate getDate() {
        return this.Date;
    }

    // Метод для отримання адреси аварії
    public Composition getAddress() {return Address;}

    // Метод для отримання вартості усунення аварії
    public int getCostOfElimination() {
        return typeToCostMap.get(typeAccident);
    }

    // Метод для створення множини з унікальними типами аварій
    public static Set<String> getUniqueAccidentTypes(List<Accident> accidents) {
        Set<String> uniqueTypes = new HashSet<>();
        for (Accident accident : accidents) {
            uniqueTypes.add(accident.getType());
        }
        return uniqueTypes;
    }

    // Метод для виведення інформації про аварію на консоль
    public void printInfo() {
        System.out.println("Адреса: " + getAddress().Address());
        System.out.println("Дата: " + getDate());
        System.out.println("Тип аварії: " + getType());
        System.out.println("Вартість усунення: " + getCostOfElimination());
        System.out.println("Ідентифікатор аварії: " + getAccidentId());
        System.out.println();
    }

    // Перевизначений метод для представлення об'єкта у вигляді рядка
    @Override
    public String toString() {
        return "Адреса: " + getAddress().Address() + "\n" +
                "Дата: " + getDate() + "\n" +
                "Тип аварії: '" + getType() + "\'" +"\n" +
                "Вартість усунення: " + getCostOfElimination() + "\n" +
                "Ідентифікатор аварії: " + getAccidentId() + "\n";
    }
}
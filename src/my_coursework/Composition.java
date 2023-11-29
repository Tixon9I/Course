package my_coursework;

import java.io.Serializable;
import java.io.IOException;
import java.time.LocalDate;

// Клас Composition для представлення різних складових об'єктів (ПІБ, адреса, номер телефону)
public class Composition implements Serializable {
    // ПІБ
    private String surname;
    private String name;
    private String patronymic;

    // Адреса
    private String street;
    private String num_house;

    // Дата
    private LocalDate date;

    // Номер телефону
    private int codeOperator;
    private int subscriberNumber;

    // Конструктор для ініціалізації ПІБ
    public Composition(String sn, String nm, String pn) {this.surname = sn; this.name = nm; this.patronymic = pn;}

    // Конструктор для ініціалізації адреси
    public Composition(String st, String nh) {this.street = st; this.num_house = nh;}

    // Конструктор для ініціалізації номеру телефону
    public Composition(int cO, int sN) {this.codeOperator = cO; this.subscriberNumber = sN;}

    // Метод для отримання повного імені
    public String FullName() {return String.format("%s %s %s", surname, name, patronymic);}

    // Метод для отримання адреси
    public String Address() {return String.format("%s, %s", street, num_house);}

    // Метод для отримання номеру телефону у вигляді рядка
    public String MobileNumber() {return String.format("+380(%s)%s", codeOperator, subscriberNumber);}

    // Метод для серіалізації об'єкта
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(surname);
        out.writeObject(name);
        out.writeObject(patronymic);
        out.writeObject(street);
        out.writeObject(num_house);
        out.writeObject(date);
        out.writeInt(codeOperator);
        out.writeInt(subscriberNumber);
    }

    // Метод для десеріалізації об'єкта
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        surname = (String) in.readObject();
        name = (String) in.readObject();
        patronymic = (String) in.readObject();
        street = (String) in.readObject();
        num_house = (String) in.readObject();
        date = (LocalDate) in.readObject();
        codeOperator = in.readInt();
        subscriberNumber = in.readInt();
    }
}


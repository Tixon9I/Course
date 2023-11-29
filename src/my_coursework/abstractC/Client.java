package my_coursework.abstractC;

import my_coursework.Composition;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Клас Client розширює абстрактний клас Person та реалізує інтерфейс Serializable
public class Client extends Person implements Serializable
{
    private int AccidentId;  // Id аварії

    public Client(Composition fullName, Composition address, Composition mobileNum, int departmentNumber, int accidentId) {
        super(fullName, address, mobileNum, departmentNumber);
        this.AccidentId = accidentId;
    }

    // Конструктор без параметрів для серіалізації
    public Client() {}


    // Метод для отримання значення AccidentId
    public int getAccidentId() {
        return AccidentId;
    }

    // Метод для встановлення значення AccidentId
    public void setAccidentId(int accidentId) {
        AccidentId = accidentId;
    }

    // Методи для встановлення значень
    public void setFullName(Composition fullName) {this.FullName = fullName;}
    public void setAddress(Composition address) {this.Address = address;}
    public void setMobileNum(Composition mobileNum) {this.MobileNum = mobileNum;}
    public void setDepartmentNumber(int departmentNumber) {this.departmentNumber = departmentNumber;}

    // Метод для виведення інформації про клієнта в консоль
    public void printInfo() {
        System.out.println("ПІБ: " + getFullName().FullName());
        System.out.println("Адреса: " + getAddress().Address());
        System.out.println("Номер телефону: " + getMobileNum().MobileNumber());
        System.out.println("Номер відділення: " + getDepartmentNumber());
        System.out.println("Id аварії: " + getAccidentId());
        System.out.println();
    }

    // Перевизначений метод toString для отримання рядкового представлення об'єкта
    @Override
    public String toString() {
        return "ПІБ: " + getFullName().FullName() + "\n" +
                "Адреса: " + getAddress().Address() + "\n" +
                "Номер телефону: " + getMobileNum().MobileNumber() + "\n" +
                "Номер відділення: " + getDepartmentNumber() + "\n" +
                "Id аварії: " + AccidentId + "\n";
    }

    // Метод для серіалізації об'єкта
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();  // Спочатку викликайте defaultWriteObject
        out.writeObject(getFullName());  // Викликайте writeObject для об'єкта Composition
        out.writeObject(getAddress());
        out.writeObject(getMobileNum());
        out.writeObject(getDepartmentNumber());
    }

    // Метод для десеріалізації об'єкта
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();  // Спочатку викликається defaultReadObject
        setFullName((Composition) in.readObject());  // Викликайється readObject і встановлються значення
        setAddress((Composition) in.readObject());
        setMobileNum((Composition) in.readObject());
        setDepartmentNumber((int) in.readObject());
    }
}

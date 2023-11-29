package my_coursework.abstractC;

import my_coursework.Composition;

// Абстрактний клас
public class Person {

    // ПІБ
    protected Composition FullName;

    // Адреса
    protected Composition Address;

    // Мобільний номер
    protected Composition MobileNum;

    // Номер відділу
    protected int departmentNumber;

    // Конструктор класу
    public Person(Composition fullName, Composition address, Composition mobileNum, int departmentNumber) {
        // Перевірка на null і при необхідності обробка таких ситуацій
        if (fullName != null && address != null && mobileNum != null) {
            this.FullName = fullName;
            this.Address = address;
            this.MobileNum = mobileNum;
            this.departmentNumber = departmentNumber;
        } else {
            // Обробка ситуації, коли передані значення - null
            throw new IllegalArgumentException("Values cannot be null.");
        }
    }

    // Конструктор за замовчуванням
    public Person() {}

    // Метод для отримання ПІБ
    public Composition getFullName() {
        return FullName;
    }

    // Метод для отримання адреси
    public Composition getAddress() {
        return Address;
    }

    // Метод для отримання мобільного номеру
    public Composition getMobileNum() {
        return MobileNum;
    }

    // Метод для отримання номера відділу
    public int getDepartmentNumber() {
        return departmentNumber;
    }
}

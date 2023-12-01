package my_coursework.GrafWindows;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import my_coursework.Accident;
import my_coursework.Composition;
import my_coursework.Order;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static my_coursework.Accident.typeToIdMap;

// Клас для створення вікна форми замовлення використовуючи JavaFX
public class OrderForm extends Application
{
    // Списки об'єктів, які будуть використовуватися в програмі
    private List<Master> mastersList;
    private List<Order> ordersList;
    private List<Client> clientsList;
    private List<Accident> accidentsList;

    // конструктор, що приймає всі списки
    public OrderForm(List<Master> mastersList, List<Order> ordersList, List<Client> clientsList, List<Accident> accidentsList)
    {
        this.mastersList = mastersList;
        this.ordersList = ordersList;
        this.clientsList = clientsList;
        this.accidentsList = accidentsList;
    }

    // конструктор без параметрів для запуску JavaFX (тестування)
    public OrderForm() {}

    // Метод main для запуску додатка JavaFX
    public static void main(String[] args) {launch(args);}

    // Перевизначений метод start для налаштування та відображення вікна форми замовлення
    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            primaryStage.setTitle("Форма замовлення");

            // Створення GridPane для розміщення елементів форми
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            // Встановлення обмежень для стовпців
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(25); // 42% ширина для першого стовпця
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(30); // 30% ширина для другого стовпця
            ColumnConstraints column3 = new ColumnConstraints();
            column3.setPercentWidth(30); // 30% ширина для другого стовпця
            grid.getColumnConstraints().addAll(column1, column2);

            // Додавання міток (Labels) на форму
            grid.add(new Label("ПІБ:"), 0, 1);
            grid.add(new Label("Адреса клієнта:"), 0, 2);
            grid.add(new Label("Номер клієнта:"), 0, 3);
            grid.add(new Label("Номер відділення:"), 0, 4);
            grid.add(new Label("Тип аварії:"), 0, 5);
            grid.add(new Label("Дата аварії:"), 0, 6);
            grid.add(new Label("Дата замовлення:"), 0, 7);

            // Додавання текстових полів (Text Fields) на форму
            TextField lastNameField = new TextField();
            lastNameField.setPromptText("Прізвище");
            grid.add(lastNameField, 1, 1);

            TextField firstNameField = new TextField();
            firstNameField.setPromptText("Ім'я");
            grid.add(firstNameField, 2, 1);

            TextField patronymicField = new TextField();
            patronymicField.setPromptText("По-батькові");
            grid.add(patronymicField, 3, 1);

            TextField streetField = new TextField();
            streetField.setPromptText("Вулиця");
            grid.add(streetField, 1, 2);

            TextField houseNumberField = new TextField();
            houseNumberField.setPromptText("Номер будинку");
            grid.add(houseNumberField, 2, 2);

            // Додавання ComboBox для вибору номеру оператора
            ComboBox<String> operatorCodeComboBox = new ComboBox<>();
            operatorCodeComboBox.getItems().addAll("50", "66", "95", "99", "67", "68", "96", "97", "98", "63", "73", "93");
            operatorCodeComboBox.setPromptText("Код оператора");
            grid.add(operatorCodeComboBox, 1, 3);

            TextField subscriberNumberField = new TextField();
            subscriberNumberField.setPromptText("Номер абонента");

            // Додаємо обмеження для вводу лише цифр та максимум 7 символів
            subscriberNumberField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                if (!newValue.matches("\\d*") || newValue.length() > 7)
                {
                    subscriberNumberField.setText(oldValue);
                }
            });

            grid.add(subscriberNumberField, 2, 3);


            // Додавання ComboBox для вибору відділення
            ComboBox<String> departmentComboBox = new ComboBox<>();
            departmentComboBox.getItems().addAll("Відділення 1", "Відділення 2", "Відділення 3", "Відділення 4");
            departmentComboBox.setPromptText("Номер відділення");
            grid.add(departmentComboBox, 1, 4);

            // Додавання ComboBox для вибору типу аварії
            ComboBox<String> accidentTypeComboBox = new ComboBox<>();
            accidentTypeComboBox.getItems().setAll("Поломка насосу", "Витік води", "Пошкодження водопровідних труб", "Прорив водопровідної лінії",
                    "Поломка насосної станції для водопостачання", "Затор каналізації через накопичення відходів",
                    "Аварія в системі опалення", "Поломка в системі водоочищення");
            accidentTypeComboBox.setPromptText("Тип аварії");
            grid.add(accidentTypeComboBox, 1, 5);


            // Додавання DatePicker для вибору дати замовлення
            DatePicker accidentDatePicker = new DatePicker();
            setDatePickerConstraints(accidentDatePicker);
            accidentDatePicker.setEditable(false); // Заблокувати введення тексту
            grid.add(accidentDatePicker, 1, 6);

            // Додавання DatePicker для вибору дати замовлення
            DatePicker orderDatePicker = new DatePicker();
            setDatePickerConstraints(orderDatePicker);
            orderDatePicker.setEditable(false); // Заблокувати введення тексту
            grid.add(orderDatePicker, 1, 7);


            // Додавання кнопки для додавання замовлення
            Button submitButton = new Button("Додати замовлення");
            submitButton.setOnAction(e ->
            {
                // Отримання введених даних з текстових полів та інших елементів
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String patronymic = patronymicField.getText();
                String street = streetField.getText();
                String houseNumber = houseNumberField.getText();
                String selectedOperatorCode = operatorCodeComboBox.getValue();
                String subscriberNumberString = subscriberNumberField.getText();
                String selectedDepartment = departmentComboBox.getValue();
                String selectedAccidentType = accidentTypeComboBox.getValue();
                LocalDate accidentDate = accidentDatePicker.getValue();
                LocalDate orderDate = orderDatePicker.getValue();

                // Список для збереження порожніх полів
                List<String> emptyFields = new ArrayList<>();

                // Поля для перевірки
                String[] fieldsToCheck =
                        {
                                lastName, "Прізвище",
                                firstName, "Ім'я",
                                patronymic, "По-батькові",
                                street, "Вулиця",
                                houseNumber, "Номер будинку",
                                selectedOperatorCode, "Код оператора",
                                subscriberNumberString, "Номер абонента",
                                selectedDepartment, "Номер відділення",
                                selectedAccidentType, "Тип аварії",
                        };

                // Перевірка порожності текстових полів та комбобоксів
                for (int i = 0; i < fieldsToCheck.length; i += 2)
                {
                    String fieldValue = fieldsToCheck[i];
                    String fieldName = fieldsToCheck[i + 1];

                    if (fieldValue == null || fieldValue.isBlank())
                    {
                        emptyFields.add(fieldName);
                    }
                }

                if (accidentDate == null)
                {
                    emptyFields.add("Дата виникнення аварії");
                }

                if (orderDate == null)
                {
                    emptyFields.add("Дата створення замовлення");
                }

                // Виведення повідомлення про порожні поля
                if (!emptyFields.isEmpty())
                {
                    StringBuilder errorMessage = new StringBuilder("Будь ласка, заповніть наступні поля:\n");

                    for (String field : emptyFields)
                    {
                        errorMessage.append("- ").append(field).append("\n");
                    }

                    showAlert("Помилка", "Не всі поля заповнені", errorMessage.toString());
                    return; // Вийти з методу, якщо не всі поля заповнені
                }

                // Перевірка, чи дата аварії не пізніше за дату замовлення
                if (accidentDate.isAfter(orderDate))
                {
                    showAlert("Помилка", "Неправильно обрані дати",
                            "Дата аварії не може бути пізніше за дату створення замовлення.");
                    return; // Вийти з методу, якщо дати обрані неправильно
                }

                // Певні параметри конвертуємо в int
                int operatorCode = Integer.parseInt(selectedOperatorCode);
                int subscriberNumber = Integer.parseInt(subscriberNumberString);
                int departmentNumber = getDepartmentNumber(selectedDepartment);
                int accidentId = getAccidentId(selectedAccidentType);

                // Перевірка вводу користувача
                ValidationResult result = validateInput(lastName, firstName, patronymic, street, houseNumber, subscriberNumberField.getText());

                // Перевірка на валідність усіх полів
                if (result.isValid())
                {
                    // Отримання майстра для замовлення
                    Master master = getMasterForOrder(getDepartmentNumber(selectedDepartment), orderDate);

                    if (master != null)
                    {
                        // Створення об'єкта Client
                        Client client = new Client(new Composition(lastName, firstName, patronymic), new Composition(street, houseNumber),
                                new Composition(operatorCode, subscriberNumber), departmentNumber, accidentId);

                        // Створення об'єкта Accident
                        Accident accident = new Accident(client, accidentDate, selectedAccidentType);

                        // Створення об'єкта Order
                        Order order = Order.createOrder(client, master, accident, orderDate);

                        // Додавання замовлення до списку
                        ordersList.add(order);
                        clientsList.add(client);
                        accidentsList.add(accident);

                        // Виклик Alert для виведення повідомлення
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Інформація");
                        alert.setHeaderText("Замовлення зареєстровано");
                        alert.setContentText("Ваше замовлення зареєстровано, оброблюється майстрем.");
                        alert.showAndWait();

                        // Закриття вікна
                        primaryStage.close();
                    } else
                    {
                        // Якщо не знайдено майстра, вивести повідомлення
                        showAlert("Помилка", "Немає доступних майстрів", "На жаль, " +
                                "немає доступних майстрів для цього замовлення. Будь ласка, спробуйте пізніше " +
                                "або зверніться за допомогою.");
                    }
                } else
                {
                    // Якщо введені дані не пройшли валідацію, вивести повідомлення про конкретне поле
                    showAlert("Помилка введення", "Перевірте введені дані", result.getErrorMessage());
                }
            });
            grid.add(submitButton, 0, 8, 2, 1);

            Scene scene = new Scene(grid, 500, 350);
            primaryStage.setScene(scene);

            primaryStage.show();

            //Обробка помилок
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Метод для виведення вікна повідомлення
    private static void showAlert(String title, String header, String content)
    {
        // Створення вікна повідомлення типу WARNING
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        // Встановлення тексту вікна
        alert.setContentText(content);
        alert.showAndWait();
    }


    //Метод для встановлення обмежень вибору дати
    private void setDatePickerConstraints(DatePicker datePicker)
    {
        // Встановлення для вибору дати
        datePicker.setDayCellFactory(picker -> new DateCell() {
            // Перевизначення методу оновлення елементу дня
            public void updateItem(LocalDate date, boolean empty) {
                // Виклик методу батьківського класу
                super.updateItem(date, empty);
                // Встановлення обмеження: дата повинна бути не раніше 1 листопада поточного року
                setDisable(date.isBefore(LocalDate.now().withMonth(11).withDayOfMonth(1)));
            }
        });
    }

    // Метод для валідації введення користувача
    public static ValidationResult validateInput(String lastName, String firstName, String patronymic, String street,
                                                 String houseNumber, String subscriberNumber)
    {
        // Перевірка ПІБ
        if (!lastName.matches("[а-яА-ЯёЁa-zA-ZґҐєЄіІїЇ]+") || !firstName.matches("[а-яА-ЯёЁa-zA-ZґҐєЄіІїЇ]+")
                || !patronymic.matches("[а-яА-ЯёЁa-zA-ZґҐєЄіІїЇ]+"))
        {
            return new ValidationResult(false, "Невірно введене ПІБ");
        }

        // Перевірка вулиці
        if (!street.matches("[а-яА-ЯёЁa-zA-ZґҐєЄіІїЇ]+"))
        {
            return new ValidationResult(false, "Невірно введена вулиця");
        } else if (!houseNumber.matches("[а-яА-ЯёЁa-zA-Z0-9ґҐєЄіІїЇ]+"))
        {
            return new ValidationResult(false, "Невірно введений номер будинку");
        }

        // Перевірка номера телефону
        if (subscriberNumber.isBlank() || !subscriberNumber.matches("\\d{7}"))
        {
            return new ValidationResult(false, "Невірно введений номер абоненту");
        }

        return new ValidationResult(true, "OK");
    }

    // Метод для отримання вільного майстра для замовлення
    private Master getMasterForOrder(int departmentNumber, LocalDate orderDate) {
        Master availableMaster = null;

        // Спочатку шукаємо вільного майстра
        for (Master master : mastersList) {
            if (master.isState() && master.getDepartmentNumber() == departmentNumber) {
                // Якщо майстр вільний і має той самий номер відділення, то обираємо його
                availableMaster = master;
                break;
            }
        }
        // Якщо вільних майстрів не знайдено, шукаємо майстра зі станом "Зайнятий"
        if (availableMaster == null) {
            for (Master master : mastersList) {
                if (!master.isState() && master.getDepartmentNumber() == departmentNumber &&
                        master.getOrderList().stream().noneMatch(order -> order.getOrderDate().equals(orderDate))) {
                    // Якщо майстр зайнятий, всі його замовлення не збігаються з поточною датою замовлення і має той самий номер відділення, обираємо його
                    availableMaster = master;
                    break;
                }
            }
        }
        // Якщо не знайдено вільного майстра, повертаємо null
        return availableMaster;
    }


    private int getDepartmentNumber(String department)
    {
        switch (department)
        {
            case "Відділення 1":
                return 1; // Наприклад, якщо 1 - номер першого відділення
            case "Відділення 2":
                return 2; // Наприклад, якщо 2 - номер другого відділення
            case "Відділення 3":
                return 3; // Наприклад, якщо 3 - номер третього відділення
            case "Відділення 4":
                return 4;
            default:
                return 0; // Якщо не вдалося визначити, можна повернути значення за замовчуванням
        }
    }


    private int getAccidentId(String accidentType)
    {
        // ID аварії на основі вибраного значення в ComboBox
        return typeToIdMap.getOrDefault(accidentType, 0);
    }

}
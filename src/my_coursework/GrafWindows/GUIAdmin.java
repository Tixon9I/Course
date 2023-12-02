package my_coursework.GrafWindows;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import my_coursework.Accident;
import my_coursework.Composition;
import my_coursework.Order;
import my_coursework.abstractC.Client;
import my_coursework.abstractC.Master;

import java.util.ArrayList;
import java.util.List;

// Імпорт статичного методу з іншого класу для валідації введення
import static my_coursework.GrafWindows.OrderForm.validateInput;

// Клас для створення графічного інтерфейсу адміністратора
public class GUIAdmin extends Application {

    // Списки об'єктів майстрів, замовлень, клієнтів та аварій
    private List<Master> mastersList;
    private List<Order> ordersList;
    private List<Client> clientsList;
    private List<Accident> accidentsList;

    // Конструктор з параметрами для ініціалізації списків
    public GUIAdmin(List<Master> mastersList, List<Order> ordersList, List<Client> clientsList, List<Accident> accidentsList)
    {
        this.mastersList = mastersList;
        this.ordersList = ordersList;
        this.clientsList = clientsList;
        this.accidentsList = accidentsList;
    }

    //Для тестування вікна без даних
    public GUIAdmin() {}

    // Поле для відображення інформації
    private TextArea displayArea;

    // Головний метод для запуску додатка
    public static void main(String[] args) {
        launch(args);
    }

    // Метод для ініціалізації та налаштування головного вікна
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Перегляд/Додавання");

        // Створення RadioButton
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton clientRadioButton = new RadioButton("Клієнти");
        clientRadioButton.setToggleGroup(toggleGroup);
        RadioButton masterRadioButton = new RadioButton("Майстри");
        masterRadioButton.setToggleGroup(toggleGroup);
        RadioButton accidentRadioButton = new RadioButton("Аварії");
        accidentRadioButton.setToggleGroup(toggleGroup);
        RadioButton orderRadioButton = new RadioButton("Замовлення");
        orderRadioButton.setToggleGroup(toggleGroup);

        // Створення TextArea для відображення інформації
        displayArea = new TextArea();
        displayArea.setEditable(false);

        // Створення елементів для введення інформації про майстра
        Label nameLabel = new Label("ПІБ майстра:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Ім'я");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Прізвище");
        TextField middleNameField = new TextField();
        middleNameField.setPromptText("По-батькові");

        Label addressLabel = new Label("Адреса майстра:");
        TextField addressStreetField = new TextField();
        addressStreetField.setPromptText("Вулиця");
        TextField addressCityField = new TextField();
        addressCityField.setPromptText("Номер будинку");

        Label phoneLabel = new Label("Номер телефону:");
        ComboBox<String> operatorCodeComboBox = new ComboBox<>();
        operatorCodeComboBox.setPromptText("Код оператора");
        operatorCodeComboBox.getItems().addAll("50", "66", "95", "99", "67", "68", "96", "97", "98", "63", "73", "93");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Номер абонента");
        // Додаємо обмеження для вводу лише цифр та максимум 7 символів
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!newValue.matches("\\d*") || newValue.length() > 7)
            {
                phoneNumberField.setText(oldValue);
            }
        });

        Label departmentLabel = new Label("Номер відділення:");
        ComboBox<String> departmentComboBox = new ComboBox<>();
        departmentComboBox.setPromptText("Відділення");
        departmentComboBox.getItems().addAll("1", "2", "3", "4");

        Label experienceLabel = new Label("Стаж майстра:");
        ComboBox<String> experienceComboBox = new ComboBox<>();
        experienceComboBox.setPromptText("Стаж роботи");
        experienceComboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        Label brigadeLabel = new Label("Номер бригади:");
        ComboBox<String> brigadeComboBox = new ComboBox<>();
        brigadeComboBox.setPromptText("Бригада");
        brigadeComboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        Button addMasterButton = new Button("Додати майстра");
        addMasterButton.setOnAction(e -> addMasterToList(
                firstNameField.getText(),
                lastNameField.getText(),
                middleNameField.getText(),
                addressStreetField.getText(),
                addressCityField.getText(),
                operatorCodeComboBox.getValue(),
                phoneNumberField.getText(),
                departmentComboBox.getValue(),
                experienceComboBox.getValue(),
                brigadeComboBox.getValue()
        ));

        // Створення SplitPane для розділення вікна на дві частини
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.5);

        // Створення TextArea для відображення інформації
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefRowCount(23); // кількість рядків у текстовому полі

        // Ліва частина з RadioButton
        VBox leftPane = new VBox(
                clientRadioButton,
                masterRadioButton,
                accidentRadioButton,
                orderRadioButton,
                new Label("Вміст списків:"),
                displayArea
        );
        leftPane.setSpacing(10);
        leftPane.setAlignment(Pos.TOP_LEFT);  // Вирівнювання вертикально вгорі

        // Створення VBox для правої частини з елементами та кнопкою
        VBox rightContent = new VBox(
                nameLabel, firstNameField, lastNameField, middleNameField,
                addressLabel, addressStreetField, addressCityField,
                phoneLabel, operatorCodeComboBox, phoneNumberField,
                departmentLabel, departmentComboBox,
                experienceLabel, experienceComboBox,
                brigadeLabel, brigadeComboBox
        );
        rightContent.setSpacing(10);

        // Розміщення кнопки "Додати майстра" посередині в правій частині
        VBox rightPane = new VBox(rightContent, addMasterButton);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setSpacing(10);


        // Додавання лівої та правої частин до SplitPane
        splitPane.getItems().addAll(leftPane, rightPane);

        // Розташування SplitPane та TextArea на макеті
        BorderPane root = new BorderPane();
        root.setTop(new Label("Оберіть категорію: Перегляд списків чи додавання майстрів"));
        root.setCenter(splitPane);

        // Встановлення сцени на підмостках
        primaryStage.setScene(new Scene(root, 600, 575));

        // Відображення вікна
        primaryStage.show();

        // Додавання обробників подій для RadioButton
        clientRadioButton.setOnAction(e -> displayInformation("Клієнти"));
        masterRadioButton.setOnAction(e -> displayInformation("Майстри"));
        accidentRadioButton.setOnAction(e -> displayInformation("Аварії"));
        orderRadioButton.setOnAction(e -> displayInformation("Замовлення"));
    }

    // Метод для відображення інформації про обрану категорію
    private void displayInformation(String category) {
        // код для відображення інформації про обрану категорію
        switch (category) {
            case "Клієнти":
                displayClientsList(clientsList);
                break;
            case "Майстри":
                displayMastersList(mastersList);
                break;
            case "Аварії":
                displayAccidentsList(accidentsList);
                break;
            case "Замовлення":
                displayOrdersList(ordersList);
                break;
        }
    }

    // Метод для відображення списку майстрів
    private void displayMastersList(List<Master> mastersList) {
        // Очищення попереднього вмісту
        displayArea.clear();

        // Перегляд кожного об'єкта майстра у списку та відображення інформації
        for (Master master : mastersList) {
            displayArea.appendText(master.toString() + "\n");
        }
    }

    // Метод для відображення списку клієнтів
    private void displayClientsList(List<Client> clientsList) {
        displayArea.clear();
        for (Client client : clientsList) {
            displayArea.appendText(client.toString() + "\n");
        }
    }

    // Метод для відображення списку аварій
    private void displayAccidentsList(List<Accident> accidentsList) {
        displayArea.clear();
        for (Accident accident : accidentsList) {
            displayArea.appendText(accident.toString() + "\n");
        }
    }

    // Метод для відображення списку замовлень
    private void displayOrdersList(List<Order> ordersList) {
        displayArea.clear();
        for (Order order : ordersList) {
            displayArea.appendText(order.toString() + "\n");
        }
    }

    // Метод для відображення повідомлення про помилку
    private static void showAlert(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Метод для додавання нового майстра до списку
    private void addMasterToList(
            String firstName, String lastName, String middleName,
            String addressStreet, String addressCity,
            String operatorCode, String phoneNumber,
            String department, String experience, String brigade) {

        // Список для збереження порожніх полів
        List<String> emptyFields = new ArrayList<>();

        // Поля для перевірки
        String[] fieldsToCheck = {
                lastName, "Прізвище",
                firstName, "Ім'я",
                middleName, "По-батькові",
                addressStreet, "Вулиця",
                addressCity, "Номер будинку",
                operatorCode, "Код оператора",
                phoneNumber, "Номер абонента",
                department, "Номер відділення",
                experience, "Стаж роботи",
                brigade, "Номер бригади"
        };

        // Перевірка порожності текстових полів та комбобоксів
        for (int i = 0; i < fieldsToCheck.length; i += 2) {
            String fieldValue = fieldsToCheck[i];
            String fieldName = fieldsToCheck[i + 1];

            if (fieldValue == null || fieldValue.isBlank()) {
                emptyFields.add(fieldName);
            }
        }

        // Виведення повідомлення про порожні поля
        if (!emptyFields.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Будь ласка, заповніть наступні поля:\n");

            for (String field : emptyFields) {
                errorMessage.append("- ").append(field).append("\n");
            }

            showAlert("Помилка", "Не всі поля заповнені", errorMessage.toString());
            return; // Вийти з методу, якщо не всі поля заповнені
        }

        // Виклик методу для валідації введення користувача
        ValidationResult validationResult = validateInput(lastName, firstName, middleName, addressStreet, addressCity, phoneNumber);

        // Перевірка результату валідації
        if (!validationResult.isValid()) {
            showAlert("Помилка", "Невірні дані", validationResult.getErrorMessage());
            return; // Вийти з методу, якщо дані невірні
        }

        int operatorCode1 = Integer.parseInt(operatorCode);
        int phoneNumber1 = Integer.parseInt(phoneNumber);

        // Створення нового об'єкту Master
        Master newMaster = new Master(
                new Composition(lastName, firstName, middleName),
                new Composition(addressStreet, addressCity),
                new Composition(operatorCode1, phoneNumber1),
                Integer.parseInt(department),
                Integer.parseInt(experience),
                Integer.parseInt(brigade)
        );

        // Додавання нового майстра до списку
        mastersList.add(newMaster);

        // Виведення інформації про нового майстра в текстовому полі
        displayArea.setText("Доданий новий майстер:\n" + newMaster.toString());
    }
}
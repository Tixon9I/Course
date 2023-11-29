package my_coursework.GrafWindows;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

// Клас, що реалізує графічний інтерфейс для довідкового вікна
public class ExplanationWindow extends Application {

    // Статичне поле для зберігання головного вікна
    private static Stage primaryStage;

    // Головний метод, який запускає графічний інтерфейс
    public static void main(String[] args) {
        launch(args);
    }

    // Метод для ініціалізації та налаштування головного вікна
    @Override
    public void start(Stage primaryStage) {
        // Створення кореневого контейнера BorderPane
        BorderPane root = new BorderPane();

        // Верхня частина вікна (текст)
        TextFlow textFlow = new TextFlow();
        Text welcomeText = new Text("\t\t\t\t\tЛаскаво просимо до програми!\n\n"
                + "Ви потрапили до довідкового вікна, де Ви зможете дізнатись інформацію про можливості програми. "
                + "Для того, аби користуватись програмою, пропоную ознайомитись з її опціями.\n"
                + "Після того як Ви обрали опцію \"Ознайомтесь з можливостями програми\""
                + ", Ви можете дізнатись про кожну з опцій детально. На данний момент, програма містить дві опції: Клієнт та Адміністратор. "
                + "Кожна з яких може виконувати ряд своїх функцій, аби дізнатись про них, оберіть одну з радіокнопок.");
        textFlow.getChildren().add(welcomeText);
        welcomeText.setTextAlignment(TextAlignment.JUSTIFY);
        VBox topBox = new VBox(textFlow);
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setPadding(new Insets(10, 10, 10, 10));  // Додаємо відступи
        root.setTop(topBox);

        // Центральна частина вікна (радіобатони та текстове поле)
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER_LEFT);

        // Додаємо радіобатони
        ToggleGroup radioGroup = new ToggleGroup();
        RadioButton clientRadioButton = new RadioButton("Переглянути розділ Клієнт");
        RadioButton adminRadioButton = new RadioButton("Переглянути розділ Адміністратор");
        clientRadioButton.setToggleGroup(radioGroup);
        adminRadioButton.setToggleGroup(radioGroup);

        // Додаємо текстове поле
        Text selectedRadioText = new Text();
        selectedRadioText.setWrappingWidth(445);  // Задаємо ширину для розтягування тексту

        // Додаємо обробник подій для радіобатонів
        radioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == clientRadioButton) {
                selectedRadioText.setText("\nВітаю! Ви увійшли в розділ \"Клієнт\".\n" +
                        "Обрана опція може виконувати ряд функцій:\n" +
                        "1. Оформлення замовлення\n" +
                        "2. Перевірка стану замовлення\n\n" +
                        "Оформлення замовлення: Користувачеві надається графічне вікно, де він зможе створити своє власне замовлення. " +
                        "Йому потрібно лише заповнити форму.\n\n" +
                        "Перевірка стану замовлення: Після створення власного замовлення, Ви зможете перевірити його стан, " +
                        "для цього потрібно лише ввести ПІБ, яке Ви ввели під час створення замовлення.");
            } else if (newValue == adminRadioButton) {
                selectedRadioText.setText("\nВітаю! Ви увійшли в розділ \"Адміністратор\".\n" +
                        "Обрана опція може виконувати ряд функцій:\n" +
                        "1. Перегляд/Додавання\n" +
                        "2. Кількість майстрів\n" +
                        "3. Кількість аварій з... по...\n" +
                        "4. Середня вартість усунення аварій\n" +
                        "5. Типи аварій\n" +
                        "6. Заробіток майстрів з... по...\n" +
                        "7. Кількість бригад\n\n" +
                        "Перегляд/Додавання: Адміністратору надається графічне вікно, де він може переглянути такі списки: " +
                        "Замовлень, майстрів, аварій та клієнтів. Також він має можливість додати нового майстра.\n\n" +
                        "Кількість майстрів: Адміністратору надається можливість переглянути кількість майстрів.\n\n" +
                        "Кількість аварій з... по...: Адміністратор може відслідити кількість аварій з певного періоду, " +
                        "для цього потрібно лише ввести початкову та кінцеву дати.\n\n" +
                        "Середня вартість усунення аварій: Адміністратор може дізнатись середню вартість усунення аварій із замовлень за весь період.\n\n" +
                        "Типи аварій: Адміністратор може дізнатись, з якими типами аварій зіткались клієнти.\n\n" +
                        "Заробіток майстрів з... по...: Адміністратор може дізнатись заробіток майстрів з певного періоду часу. " +
                        "Для цього потрібно ввести початкову та кінцеву дати.\n\n" +
                        "Кількість бригад: Адміністратор може дізнатись кількість бригад, у яку входять майстри.");
            }
        });

        // Додаємо радіобатони та текстове поле в центральну частину
        centerBox.getChildren().addAll(clientRadioButton, adminRadioButton, selectedRadioText);
        root.setCenter(centerBox);

        // Використовуємо ScrollPane для вміщення тексту у вікно без обрізань
        ScrollPane scrollPane = new ScrollPane(centerBox);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        // Створення сцени та встановлення її в головне вікно
        Scene scene = new Scene(root, 455, 400);
        primaryStage.setTitle("Довідка програми");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // Заблокувати змінення розміру вікна

        // Додавання власного обробника подій при спробі закриття вікна
        primaryStage.setOnCloseRequest(event -> {
            // Скасовувати події закриття, щоб вікно не закривалося автоматично
            event.consume();
        });

        // Відображення вікна
        primaryStage.show();
    }

    // Статичний метод для закриття вікна
    public static void closeWindow() {
        // Закриваємо вікно, якщо воно вже створене
        if (primaryStage != null) {
            primaryStage.close();
        }
    }
}

package my_coursework.GrafWindows;

// Клас ValidationResult використовується для повернення результату валідації разом із сповіщенням про помилку.

public class ValidationResult {
    private boolean isValid;        // Прапорець, який показує, чи є валідація успішною
    private String errorMessage;    // Рядок, що містить повідомлення про помилку у разі невдалої валідації

    // Конструктор класу, приймає значення isValid та errorMessage
    public ValidationResult(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    // Геттер для отримання значення isValid
    public boolean isValid() {
        return isValid;
    }

    // Геттер для отримання значення errorMessage
    public String getErrorMessage() {
        return errorMessage;
    }
}

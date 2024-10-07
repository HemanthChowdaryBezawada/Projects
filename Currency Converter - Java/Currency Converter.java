import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends Application {

    private final String API_URL = "https://v6.exchangeratesapi.io/latest?base=USD";

    private Map<String, Double> exchangeRates = new HashMap<>();

    private ComboBox<String> fromCurrencyComboBox;
    private ComboBox<String> toCurrencyComboBox;
    private TextField amountTextField;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        fromCurrencyComboBox = new ComboBox<>();
        toCurrencyComboBox = new ComboBox<>();
        amountTextField = new TextField();
        resultLabel = new Label();

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> convertCurrency());

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(convertButton);

        grid.add(new Label("From:"), 0, 0);
        grid.add(fromCurrencyComboBox, 1, 0);
        grid.add(new Label("To:"), 0, 1);
        grid.add(toCurrencyComboBox, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountTextField, 1, 2);
        grid.add(hbBtn, 1, 3);
        grid.add(resultLabel, 1, 4);

        Scene scene = new Scene(grid, 400, 275);
        primaryStage.setScene(scene);

        loadCurrencies();

        primaryStage.show();
    }

    private void loadCurrencies() {
        // Load currencies from the API
        // You can use any free API to fetch currency symbols and populate the ComboBoxes
        // For simplicity, let's assume a few currencies
        String[] topCurrencies = {"USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD"};
        fromCurrencyComboBox.getItems().addAll(topCurrencies);
        toCurrencyComboBox.getItems().addAll(topCurrencies);
    }

    private void convertCurrency() {
        String fromCurrency = fromCurrencyComboBox.getValue();
        String toCurrency = toCurrencyComboBox.getValue();
        double amount = Double.parseDouble(amountTextField.getText());

        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);

        if (exchangeRate != -1) {
            double result = amount * exchangeRate;
            resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, result, toCurrency));
        } else {
            resultLabel.setText("Error: Unable to get exchange rate.");
        }
    }

    private double getExchangeRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return 1.0;
        }

        if (exchangeRates.containsKey(fromCurrency)) {
            return exchangeRates.get(fromCurrency);
        }

        try {
            @SuppressWarnings("deprecation")
            final URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject rates = jsonResponse.getJSONObject("rates");

                for (String currency : rates.keySet()) {
                    exchangeRates.put(currency, rates.getDouble(currency));
                }

                return exchangeRates.get(toCurrency);
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}

package com.ve.weather;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.log4j.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherController {
    private static final Logger LOGGER =
            LogManager.getLogger(WeatherController.class);
    @FXML
    private TextField city;

    @FXML
    private Text feels;

    @FXML
    private Button getData;

    @FXML
    private Text max;

    @FXML
    private Text min;

    @FXML
    private Text pressure;

    @FXML
    private Text temperature;

    @FXML
    private void initialize() {
        Layout layout = new PatternLayout("%-30d{yyyy-MM-dd HH:mm:ss} [%c] %p: %m%n");
        Logger.getRootLogger().addAppender(new ConsoleAppender(layout));

        getData.setOnAction(event -> {
            String appId = "0f9fa8e7381fe4c845229f3a70dff6e4";
            String inputCity = city.getText().trim();
            String output = getUrlContent(String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                    inputCity,
                    appId
            ));
            if (!output.isEmpty()) {
                LOGGER.info(output);
                JSONObject object = new JSONObject(output);
                temperature.setText("Температура: " + object.getJSONObject("main").getDouble("temp"));
                feels.setText("Ощущается: " + object.getJSONObject("main").getDouble("feels_like"));
                max.setText("Максимум: " + object.getJSONObject("main").getDouble("temp_max"));
                min.setText("Минимум: " + object.getJSONObject("main").getDouble("temp_min"));
                pressure.setText("Давление: " + object.getJSONObject("main").getDouble("pressure"));
            }
        });
    }

    private static String getUrlContent(final String urlAddress) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new URL(urlAddress).openConnection().getInputStream()))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            LOGGER.error("City not found");
        }
        return content.toString();
    }
}
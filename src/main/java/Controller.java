import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

public class Controller implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<CityData> cityBox;

    @FXML
    private ComboBox<Timestamp> timestamp;
    
    @FXML
    private Button submitButton;

    @FXML
    private Label cityErrorLabel;

    @FXML
    private TabPane tabPane;

    @FXML
    private BarChart<String, Float> precipitationChart;

    @FXML
    private BarChart<String, Float> radiationChart;

    @FXML
    private LineChart<String, Float> temperatureChart;

    @FXML
    private LineChart<String, Float> moistureChart;

    @FXML
    private LineChart<String, Float> pressureChart;

    @FXML
    private LineChart<String, Float> dewChart;

    @FXML
    private NumberAxis moistureAxis;

    @FXML
    private NumberAxis pressureAxis;

    @FXML
    private NumberAxis dewAxis;

    @FXML
    private NumberAxis temperatureAxis;

    @FXML
    private NumberAxis radiationAxis;

    @FXML
    private NumberAxis precipitationAxis;

    /**
     * Event for enable submitButton
     */
    @FXML
    void allowSubmition(ActionEvent event)
    {
        submitButton.setDisable(false);
    }

    /**
     * Event for enable timestamp
     * @param event
     */
    @FXML
    void allowTimestampSelection(ActionEvent event)
    {
        timestamp.setDisable(false);
    }

    @Override
    public void initialize(URL URL, ResourceBundle RB)
    {
        populateCityBox();
        populateTimestamp();
        cityErrorLabel.setVisible(false);
    }

    /**
     * Fill cityBox with choices
     */
    public void populateCityBox()
    {
        ObservableList<CityData> data;
        try
        {
            data = FXCollections.observableArrayList(DatasetCollector.getCityData());
            cityBox.setItems(data);
        }
        catch (IOException e)
        {
            raiseErrorAlert(AlertSample.DOWNLOAD_ERROR);
        }

        cityBox.setConverter
        (
            new StringConverter<CityData>()
            {
                @Override
                public String toString(CityData object)
                {
                    return object.getName();
                }

                @Override
                public CityData fromString(String string)
                {
                    return cityBox.getItems().stream().filter(v -> v.getName().equals(string)).findFirst().orElse(null);
                }
            }
        );
    }

    /**
     * Fill timestamp with choices
     */
    public void populateTimestamp()
    {
        ObservableList<Timestamp> data = FXCollections.observableArrayList(Timestamp.values());
        timestamp.setItems(data);

        timestamp.setConverter
        (
            new StringConverter<Timestamp>()
            {
                @Override
                public String toString(Timestamp object)
                {
                    return object.getText();
                }
                @Override
                public Timestamp fromString(String string)
                {
                    return timestamp.getItems().stream().filter(v -> v.getText().equals(string)).findFirst().orElse(null);
                }
            }
        );
    }

    /**
     * Show custom message error dialog
     * @param text
     */
    public void raiseErrorAlert(String text)
    {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Algo deu errado");
        errorAlert.setContentText(text);
        errorAlert.showAndWait();
    }

    /**
     * Event to submit user choices and plot info into charts
     * @param event
     */
    @FXML
    void submitChoices(ActionEvent event)
    {
        List<WeatherData> dataset;
        try
        {
            dataset = DatasetCollector.getWeatherData(cityBox.getValue().getId(), timestamp.getValue());
        }
        catch (IOException e)
        {
            raiseErrorAlert(AlertSample.DOWNLOAD_ERROR);
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(timestamp.getValue() == Timestamp.TODAY ? "HH'h'" : "HH'h' dd/MM");

        Series<String, Float> temperatureInst = new Series<String, Float>();
        Series<String, Float> temperatureMin = new Series<String, Float>();
        Series<String, Float> temperatureMax = new Series<String, Float>();
        Series<String, Float> moistureInst = new Series<String, Float>();
        Series<String, Float> moistureMin = new Series<String, Float>();
        Series<String, Float> moistureMax = new Series<String, Float>();
        Series<String, Float> pressureInst = new Series<String, Float>();
        Series<String, Float> pressureMin = new Series<String, Float>();
        Series<String, Float> pressureMax = new Series<String, Float>();
        Series<String, Float> dewInst = new Series<String, Float>();
        Series<String, Float> dewMin = new Series<String, Float>();
        Series<String, Float> dewMax = new Series<String, Float>();
        Series<String, Float> precipitation = new Series<String, Float>();
        Series<String, Float> radiation = new Series<String, Float>();

        boolean flag = false;
        Float temperatureUpperBound = 0f,
              temperatureLowerBound = 101f,
              moistureUpperBound = 0f,
              moistureLowerBound = 101f,
              pressureUpperBound = 0f,
              pressureLowerBound = 10001f,
              dewUpperBound = 0f,
              dewLowerBound = 101f,
              precipitationUpperBound = 0f,
              radiationUpperBound = 0f;
        
        for (WeatherData data: dataset)
        {
            String dateTime = formatter.format(data.getDateTime());

            if (data.getTemperatureInst().compareTo(0f) >= 0)
            {
                flag = true;
                temperatureInst.getData().add(new Data<String, Float>(dateTime, data.getTemperatureInst()));
                temperatureMin.getData().add(new Data<String, Float>(dateTime, data.getTemperatureMin()));
                temperatureMax.getData().add(new Data<String, Float>(dateTime, data.getTemperatureMax()));

                temperatureUpperBound = Math.max(temperatureUpperBound, data.getTemperatureMax());
                temperatureLowerBound = Math.min(temperatureLowerBound, data.getTemperatureMin());
            }

            if (data.getMoistureInst().compareTo(0f) >= 0)
            {
                moistureInst.getData().add(new Data<String, Float>(dateTime, data.getMoistureInst()));
                moistureMin.getData().add(new Data<String, Float>(dateTime, data.getMoistureMin()));
                moistureMax.getData().add(new Data<String, Float>(dateTime, data.getMoistureMax()));

                moistureUpperBound = Math.max(moistureUpperBound, data.getMoistureMax());
                moistureLowerBound = Math.min(moistureLowerBound, data.getMoistureMin());
            }

            if (data.getPressureInst().compareTo(0f) >= 0)
            {
                pressureInst.getData().add(new Data<String, Float>(dateTime, data.getPressureInst()));
                pressureMin.getData().add(new Data<String, Float>(dateTime, data.getPressureMin()));
                pressureMax.getData().add(new Data<String, Float>(dateTime, data.getPressureMax()));

                pressureUpperBound = Math.max(pressureUpperBound, data.getPressureMax());
                pressureLowerBound = Math.min(pressureLowerBound, data.getPressureMin());
            }

            if (data.getDewInst().compareTo(0f) >= 0)
            {
                dewInst.getData().add(new Data<String, Float>(dateTime, data.getDewInst()));
                dewMin.getData().add(new Data<String, Float>(dateTime, data.getDewMin()));
                dewMax.getData().add(new Data<String, Float>(dateTime, data.getDewMax()));

                dewUpperBound = Math.max(dewUpperBound, data.getDewMax());
                dewLowerBound = Math.min(dewLowerBound, data.getDewMin());
            }

            if (data.getPrecipitation().compareTo(0f) >= 0)
            {
                precipitation.getData().add(new Data<String, Float>(dateTime, data.getPrecipitation()));

                precipitationUpperBound = Math.max(precipitationUpperBound, data.getPrecipitation());
            }

            if (data.getRadiation().compareTo(0f) >= 0)
            {
                radiation.getData().add(new Data<String, Float>(dateTime, data.getRadiation()));

                radiationUpperBound = Math.max(radiationUpperBound, data.getRadiation());
            }
        }

        temperatureAxis.setUpperBound(temperatureUpperBound);
        temperatureAxis.setLowerBound(temperatureLowerBound);
        moistureAxis.setUpperBound(moistureUpperBound);
        moistureAxis.setLowerBound(moistureLowerBound);
        pressureAxis.setUpperBound(pressureUpperBound);
        pressureAxis.setLowerBound(pressureLowerBound);
        dewAxis.setUpperBound(dewUpperBound);
        dewAxis.setLowerBound(dewLowerBound);
        precipitationAxis.setUpperBound(precipitationUpperBound);
        radiationAxis.setUpperBound(radiationUpperBound);

        temperatureInst.setName("Instântanea");
        temperatureMin.setName("Mínima");
        temperatureMax.setName("Máxima");
        moistureInst.setName("Instântanea");
        moistureMin.setName("Mínima");
        moistureMax.setName("Máxima");
        pressureInst.setName("Instântanea");
        pressureMin.setName("Mínima");
        pressureMax.setName("Máxima");
        dewInst.setName("Instântanea");
        dewMin.setName("Mínima");
        dewMax.setName("Máxima");

        temperatureChart.getData().clear();
        moistureChart.getData().clear();
        pressureChart.getData().clear();
        dewChart.getData().clear();
        precipitationChart.getData().clear();
        radiationChart.getData().clear();

        temperatureChart.getData().addAll(temperatureInst, temperatureMin, temperatureMax);
        moistureChart.getData().addAll(moistureInst, moistureMin, moistureMax);
        pressureChart.getData().addAll(pressureInst, pressureMin, pressureMax);
        dewChart.getData().addAll(dewInst, dewMin, dewMax);
        precipitationChart.getData().add(precipitation);
        radiationChart.getData().add(radiation);

        if (flag)
        {
            tabPane.setDisable(false);
            cityErrorLabel.setVisible(false);
        }
        else
        {
            tabPane.setDisable(true);
            cityErrorLabel.setVisible(true);
        }
    }

    /**
     * Get a 5% value of a bound to gaps
     * @return gaps
     */
    private Float getGaps(Float value)
    {
        return 0f + Math.round(value / 100f);
    }
}

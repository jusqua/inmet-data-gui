import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * <h1>This is an INMET data collector</h1>
 * <p>
 *   The inmet-data-collector program implements an application
 *   that displays a data collected from INMET dataset API.
 * <p>
 * <p>
 *   Choose the city, timestamp and the data will be display
 *   in a Line Graph. With this, some tabs will be shown:
 *   Temperature; Precipitation; and Wind velocity.
 * </p>
 * 
 * @author  Ádrian Gama
 * @version 0.1-alpha
 * @since   2021-12-03 
 */
public class App extends Application
{
    private Stage mainStage;
    private AnchorPane root;

    @Override
    public void start(Stage stage) throws Exception
    {
        this.mainStage = stage;
        this.mainStage.setTitle("INMET Data Collector");
        
        try
        {
            this.root = (AnchorPane) FXMLLoader.load(this.getClass().getResource("layout.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        this.mainStage.setScene(new Scene(this.root));
        this.mainStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

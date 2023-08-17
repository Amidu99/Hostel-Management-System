package lk.d24.hms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.d24.hms.controller.LoadingFormController;
import java.io.IOException;

public class launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(launcher.class.getResource("/view/loading_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
        LoadingFormController loadingController = fxmlLoader.getController();
        loadingController.initialize();
    }

    public static void main(String[] args) {launch();}
}
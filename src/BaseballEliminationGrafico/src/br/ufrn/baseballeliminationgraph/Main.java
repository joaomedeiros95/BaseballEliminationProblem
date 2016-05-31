package br.ufrn.baseballeliminationgraph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String ARQUIVOFX = "view/inicial.fxml";
    private AnchorPane rootLayout;
    private FXMLLoader loader;
    public static final String SUB_PASTA = "tests/";

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader();
        rootLayout = loader.load(getClass().getResource(ARQUIVOFX));
        primaryStage.setTitle("Baseball Elimination - UFRN");
        primaryStage.setScene(new Scene(rootLayout, 600, 550));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:resources/icon.png"));

        carregarArquivosTeste();
    }

    private void carregarArquivosTeste() {
    }


    public static void main(String[] args) {
        launch(args);
    }
}

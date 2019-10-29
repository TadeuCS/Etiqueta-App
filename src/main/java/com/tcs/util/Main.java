package com.tcs.util;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Sessao.stage = stage;
        new WindowUtil().toPrincipal();
    }
}

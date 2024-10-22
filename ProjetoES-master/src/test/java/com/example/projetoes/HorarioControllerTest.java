package com.example.projetoes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
class HorarioControllerTest  {

   @Start
    private void start(Stage stage) {
       Parent root = null;
       try {
           root = FXMLLoader.load(getClass().getResource("HorarioF.fxml"));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setResizable(false);
       stage.show();
    }


    @Test
    void updateTable(FxRobot robot) {
        TableView horario = robot.lookup("#horario").queryAs(TableView.class);
        assertFalse(horario.getItems().isEmpty());
    }

    @Test
    void search(FxRobot robot) {
        TextField searchBar = robot.lookup("#texto_pesquisa_horario").queryAs(TextField.class);
        //Button btn = robot.lookup("#btn_pesquisa_horario").queryAs(Button.class);
        TableView horario = robot.lookup("#horario").queryAs(TableView.class);

        //Primeiro teste de pesquisa

        searchBar.setText("LIGE");
        robot.clickOn("#btn_pesquisa_horario");
        assertEquals(1, horario.getItems().size());
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.util;

import java.io.IOException;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Tadeu-PC
 */
public class WindowUtil {

    public Stage stage;
    public Screen screen;
    public Parent root;

    public void toPrincipal(){
        try {
            stage = Sessao.stage;
            root = FXMLLoader.load(getClass().getResource("/fxml/PrincipalFXML.fxml"));
            setUserAgentStylesheet(STYLESHEET_MODENA);
            stage.setTitle("Emissor de Etiqueta");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            MensagemUtils.exception("Erro ao abrir a tela Principal!", e);
            e.printStackTrace();
        }
    }

    public void toConfiguracao() {
        try {
            stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/ConfiguracaoFXML.fxml"));
            setUserAgentStylesheet(STYLESHEET_MODENA);
            stage.setTitle("Configuração do Emissor");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            MensagemUtils.exception("Erro ao abrir a tela de Configuração!", e);
            e.printStackTrace();
        }
    }

    public void fechaJanela(Scene scene) {
        try {
            Stage s = (Stage) scene.getWindow();
            s.close();
        } catch (Exception e) {
            MensagemUtils.exception("Erro ao fechar a janela", e);
        }
    }
}

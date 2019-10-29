/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.controller;

import com.tcs.pojo.ParametrosPojo;
import com.tcs.util.Conexao;
import com.tcs.util.CriptoUtils;
import com.tcs.util.FileChooserUtils;
import com.tcs.util.MensagemUtils;
import com.tcs.util.OUtil;
import com.tcs.util.Sessao;
import com.tcs.util.WindowUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.print.PrintService;

/**
 * FXML Controller class
 *
 * @author Tadeu-PC
 */
public class ConfiguracaoFXMLController implements Initializable {

    @FXML
    private TextField iptLogo;
    @FXML
    private Button btnBuscarLogo;
    @FXML
    private TextField iptEtiqueta;
    @FXML
    private Button btnBuscarEtiqueta;
    @FXML
    private TextField iptBanco;
    @FXML
    private Button btnBuscarBanco;
    @FXML
    private TextField iptUsuario;
    @FXML
    private PasswordField iptSenha;
    @FXML
    private Button btnTesteConexao;
    @FXML
    private TextField iptServidor;
    @FXML
    private ComboBox<String> cbImpressoras;
    @FXML
    private CheckBox chPreVisualiza;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();
    }

    @FXML
    private void salvarParametros(ActionEvent event) {
        try {
            Sessao.ini.put("Etiqueta", "impressora", cbImpressoras.getValue());
            Sessao.ini.put("Etiqueta", "preVisualiza", chPreVisualiza.isSelected());
            Sessao.ini.put("Etiqueta", "logo", iptLogo.getText().trim());
            Sessao.ini.put("Etiqueta", "etiqueta", iptEtiqueta.getText().trim());
            Sessao.ini.put("Conexao", "banco", iptBanco.getText().trim());
            Sessao.ini.put("Conexao", "servidor", iptServidor.getText().trim());
            Sessao.ini.put("Conexao", "usuario", iptUsuario.getText().trim());
            Sessao.ini.put("Conexao", "senha", CriptoUtils.criptografaSenha(iptSenha.getText().trim()));
            Sessao.ini.store();
            MensagemUtils.info("Parametros salvos com sucesso!");
            Sessao.carregaParametros();
            Sessao.principal.carregaProdutos();
            new WindowUtil().fechaJanela(btnTesteConexao.getScene());
        } catch (Exception ex) {
            MensagemUtils.exception("Erro ao gravar os parametros no arquivo de configuração!", ex);
        }
    }

    @FXML
    private void buscarLogo(ActionEvent event) {
        iptLogo.setText(FileChooserUtils.getFile());
    }

    @FXML
    private void buscarEtiqueta(ActionEvent event) {
        iptEtiqueta.setText(FileChooserUtils.getFile());
    }

    @FXML
    private void buscarBanco(ActionEvent event) {
        iptBanco.setText(FileChooserUtils.getFile());
    }

    @FXML
    private void testarConexao(ActionEvent event) {
        try {
//            Sessao.conexao.testaConexao(iptBanco.getText().trim(), iptServidor.getText().trim(), iptUsuario.getText().trim(), iptSenha.getText().trim());
            Sessao.parametros = new ParametrosPojo();
            Sessao.parametros.setDirBanco(iptBanco.getText().trim());
            Sessao.parametros.setServidor(iptServidor.getText().trim());
            Sessao.parametros.setUsuario(iptUsuario.getText().trim());
            Sessao.parametros.setSenha(iptSenha.getText().trim());
            Sessao.conexao = new Conexao();
            MensagemUtils.info("Conexão bem sucedida!");
        } catch (Exception e) {
            MensagemUtils.exception("Erro ao conectar no banco de dados!", e);
            e.printStackTrace();
        }
    }

    private void initComponents() {
        carregaImpressoras();
        if (Sessao.parametros != null) {
            iptLogo.setText(Sessao.parametros.getDirLogo());
            iptEtiqueta.setText(Sessao.parametros.getDirEtiqueta());
            iptBanco.setText(Sessao.parametros.getDirBanco());
            iptServidor.setText(Sessao.parametros.getServidor());
            iptUsuario.setText(Sessao.parametros.getUsuario());
            iptSenha.setText(Sessao.parametros.getSenha());
            chPreVisualiza.setSelected(Sessao.parametros.getPreVisualiza());
            cbImpressoras.getSelectionModel().select(Sessao.parametros.getImpressora());
        }
    }

    private void carregaImpressoras() {
        for (PrintService printer : OUtil.listPrinters()) {
            cbImpressoras.getItems().add(printer.getName());
        }
    }

}

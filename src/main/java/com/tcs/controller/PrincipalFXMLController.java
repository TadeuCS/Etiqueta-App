/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.controller;

import com.tcs.pojo.ProdutoPojo;
import com.tcs.util.MensagemUtils;
import com.tcs.util.OUtil;
import com.tcs.util.Sessao;
import com.tcs.util.WindowUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Tadeu-PC
 */
public class PrincipalFXMLController implements Initializable {

    private ProdutoPojo produtoSelecionado;
    @FXML
    public TableView<ProdutoPojo> tbProdutos;
    @FXML
    private TableColumn<ProdutoPojo, Integer> colCod1;
    @FXML
    private TableColumn<ProdutoPojo, String> colDesc1;
    @FXML
    private TableColumn<ProdutoPojo, BigDecimal> colPreco1;
    @FXML
    private TextField iptFiltro;
    @FXML
    private TableView<ProdutoPojo> tbProdutosSelecionados;
    @FXML
    private TableColumn<ProdutoPojo, Integer> colCod2;
    @FXML
    private TableColumn<ProdutoPojo, String> colDesc2;
    @FXML
    private TableColumn<ProdutoPojo, BigDecimal> colPreco2;
    @FXML
    private TextField iptProduto;
    @FXML
    private TextField iptQtde;
    @FXML
    private TableColumn<?, ?> colQtde2;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();
        carregaProdutos();
    }

    private void initComponents() {
        Sessao.principal = this;
        colCod1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDesc1.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPreco1.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colCod2.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDesc2.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPreco2.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQtde2.setCellValueFactory(new PropertyValueFactory<>("qtde"));

        OUtil.fieldToUPPER(iptFiltro);
        OUtil.onlyDigitsValue(iptQtde, 2);
        Sessao.carregaParametros();
    }

    public void carregaProdutos() {
        try {
//            Conexao conexao = new Conexao("C:\\Hortagro\\Banco\\Resulth.fb");
            if (Sessao.parametros != null) {
                ResultSet rs = Sessao.conexao.consulta("select * from PRODUTO");
                tbProdutos.getItems().clear();
                while (rs.next()) {
                    ProdutoPojo produto = new ProdutoPojo(Integer.parseInt(rs.getString("codprod")), rs.getString("descricao").trim(), new BigDecimal(rs.getString("preco")), rs.getString("descricao3").trim());
                    tbProdutos.getItems().add(produto);
                }
            } else {
                MensagemUtils.alerta("Não foi possível conectar no banco de dados!");
            }
        } catch (Exception e) {
            MensagemUtils.exception("Erro ao carregar os produtos!", e);
            e.printStackTrace();
        } finally {
            ObservableList data = tbProdutos.getItems();
            iptFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (oldValue != null && (newValue.length() < oldValue.length())) {
                    tbProdutos.setItems(data);
                }
                String value = newValue.toLowerCase();
                ObservableList<ProdutoPojo> subentries = FXCollections.observableArrayList();

                long count = tbProdutos.getColumns().stream().count();
                for (int i = 0; i < tbProdutos.getItems().size(); i++) {
                    for (int j = 0; j < count; j++) {
                        String entry = "" + tbProdutos.getColumns().get(j).getCellData(i);
                        if (entry.toLowerCase().contains(value)) {
                            subentries.add(tbProdutos.getItems().get(i));
                            break;
                        }
                    }
                }
                tbProdutos.setItems(subentries);
            });
        }
    }

    @FXML
    private void transfereParaDestino(MouseEvent event) {
        if (tbProdutos.getSelectionModel().getSelectedItem() != null) {
            carregaItemSelecionado(tbProdutos.getSelectionModel().getSelectedItem());
        }
        if (event.getClickCount() > 1) {
            adicionaProduto(1);
        }
    }

    @FXML
    private void transfereParaOrigem(MouseEvent event) {
        if (tbProdutosSelecionados.getSelectionModel().getSelectedItem() != null) {
            tbProdutosSelecionados.getItems().remove(tbProdutosSelecionados.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void gerar(ActionEvent event) {
        List<ProdutoPojo> itensSeparados = new ArrayList<>();
        for (ProdutoPojo item : tbProdutosSelecionados.getItems()) {
            for (int i = 0; i < item.getQtde(); i++) {
                item.setCodBarras(OUtil.geraCodigoBarras(item.getCodigo()));
                itensSeparados.add(item);
            }
        }
        Map parametros = new HashMap();
        parametros.put("logo", Sessao.parametros.getDirLogo());
        parametros.put("cnpj", Sessao.empresa.getCnpj());
        parametros.put("ie", Sessao.empresa.getIe());
        parametros.put("telefone", Sessao.empresa.getFone());
        parametros.put("endereco", Sessao.empresa.getEndereco());
        parametros.put("numero", Sessao.empresa.getNumero());
        parametros.put("bairro", Sessao.empresa.getBairro());
        parametros.put("cidade", Sessao.empresa.getCidade());
        parametros.put("estado", Sessao.empresa.getUf());
        parametros.put("cep", Sessao.empresa.getCep());
        parametros.put("dtEmbalagem", new Date());
        OUtil.imprimirDefault(parametros, itensSeparados, Sessao.parametros.getDirEtiqueta(), Sessao.parametros.getPreVisualiza());
        tbProdutosSelecionados.getItems().clear();
    }

    @FXML
    private void adicionar(ActionEvent event) {
        if (produtoSelecionado == null) {
            MensagemUtils.alerta("Selecione um Produto!");
        } else if (iptQtde.getText().trim().isEmpty()) {
            MensagemUtils.alerta("Informe a Quantidade!");
        } else {
            adicionaProduto(Integer.parseInt(iptQtde.getText().trim()));
        }
    }

    private void adicionaProduto(int qtde) {
        produtoSelecionado.setQtde(qtde);
        tbProdutosSelecionados.getItems().add(produtoSelecionado);
        produtoSelecionado = null;
        iptProduto.setText(null);
        iptQtde.setText("1");
        tbProdutos.getSelectionModel().clearSelection();
        tbProdutosSelecionados.getSelectionModel().clearSelection();
    }

    private void carregaItemSelecionado(ProdutoPojo item) {
        produtoSelecionado = item;
        iptProduto.setText(item.getDescricao());
        iptQtde.requestFocus();
    }

    @FXML
    private void enter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            adicionaProduto(Integer.parseInt(iptQtde.getText().trim()));
        }
    }

    @FXML
    private void toConfigurar(ActionEvent event) {
        new WindowUtil().toConfiguracao();
    }

}

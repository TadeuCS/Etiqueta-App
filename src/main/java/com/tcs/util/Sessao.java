/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.util;

import com.tcs.controller.PrincipalFXMLController;
import com.tcs.pojo.EmpresaPojo;
import com.tcs.pojo.ParametrosPojo;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.Stage;
import org.ini4j.Ini;

/**
 *
 * @author Tadeu-PC
 */
public class Sessao {

    public static Stage stage;
    public static Ini ini;
    public static Conexao conexao;
    public static ParametrosPojo parametros;
    public static EmpresaPojo empresa;
    public static PrincipalFXMLController principal;

    public static void carregaParametros() {
        try {
            File file = new File("C:\\Hortagro\\Config.ini");
            if (!file.exists()) {
                file.createNewFile();
            }
            Sessao.ini = new Ini(file);
            if (!Sessao.ini.isEmpty()) {
                Sessao.parametros = new ParametrosPojo(
                        Sessao.ini.get("Etiqueta", "impressora", String.class),
                        Sessao.ini.get("Etiqueta", "preVisualiza", Boolean.class),
                        Sessao.ini.get("Etiqueta", "logo", String.class),
                        Sessao.ini.get("Etiqueta", "etiqueta", String.class),
                        Sessao.ini.get("Conexao", "banco", String.class),
                        Sessao.ini.get("Conexao", "servidor", String.class),
                        Sessao.ini.get("Conexao", "usuario", String.class),
                        CriptoUtils.decriptografaSenha(Sessao.ini.get("Conexao", "senha", String.class)));
                Sessao.conexao = new Conexao();
                carregaDadosEmpresa();
            }
        } catch (IOException ex) {
            MensagemUtils.exception("Erro ao carregar o arquivo de configuração!", ex);
        } catch (Exception ex) {
            MensagemUtils.exception("Erro ao conectar no banco de dados!", ex);
        }
    }

    private static void carregaDadosEmpresa() throws SQLException {
        ResultSet rs = conexao.consulta("select * from FILIAIS f where f.CODEMPRESA=0;");
        if(rs.next()){
            empresa=new EmpresaPojo();
            empresa.setCnpj(rs.getString("cgc"));
            empresa.setIe(rs.getString("inscest"));
            empresa.setFone(rs.getString("telefone"));
            empresa.setEndereco(rs.getString("endereco"));
            empresa.setNumero(rs.getString("numero"));
            empresa.setBairro(rs.getString("bairro"));
            empresa.setCidade(rs.getString("cidade"));
            empresa.setUf(rs.getString("estado"));
            empresa.setCep(rs.getString("cep"));
            if(!empresa.getCnpj().replace(".", "").replace("-", "").replace("/", "").trim().equals("11935538000104")){
                System.exit(0);
            }
        }
    }
}

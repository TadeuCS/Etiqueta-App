/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tadeu-PC
 */
public class Conexao {

    Connection con;
    ResultSet rs;
    Statement st;

    public Conexao() throws ClassNotFoundException, SQLException {
        String url = "jdbc:firebirdsql://"+Sessao.parametros.getServidor()+":3050/" + Sessao.parametros.getDirBanco();
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        con = DriverManager.getConnection(url, Sessao.parametros.getUsuario(), Sessao.parametros.getSenha());
    }

    public ResultSet consulta(String query) throws SQLException {
        if (st == null) {
            st = con.createStatement();
        }
        rs = st.executeQuery(query);
        return rs;
    }

    public void testaConexao(String banco,String servidor, String usuario, String senha) throws ClassNotFoundException, SQLException {
        String url = "jdbc:firebirdsql://"+servidor+":3050/" + banco;
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        con = DriverManager.getConnection(url, usuario, senha);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcs.pojo;

/**
 *
 * @author Tadeu-PC
 */
public class ParametrosPojo {

    private String dirLogo;
    private String dirEtiqueta;
    private String dirBanco;
    private String impressora;
    private Boolean preVisualiza;
    private String servidor;
    private String usuario;
    private String senha;

    public ParametrosPojo() {
    }

    public ParametrosPojo(String impressora,Boolean preVisualiza, String dirLogo, String dirEtiqueta, String dirBanco, String servidor, String usuario, String senha) {
        this.dirLogo = dirLogo;
        this.dirEtiqueta = dirEtiqueta;
        this.dirBanco = dirBanco;
        this.usuario = usuario;
        this.senha = senha;
        this.servidor = servidor;
        this.impressora = impressora;
        this.preVisualiza=preVisualiza;
    }

    public String getDirLogo() {
        return dirLogo;
    }

    public void setDirLogo(String dirLogo) {
        this.dirLogo = dirLogo;
    }

    public String getDirEtiqueta() {
        return dirEtiqueta;
    }

    public void setDirEtiqueta(String dirEtiqueta) {
        this.dirEtiqueta = dirEtiqueta;
    }

    public Boolean getPreVisualiza() {
        if(preVisualiza==null){
            preVisualiza=false;
        }
        return preVisualiza;
    }

    public void setPreVisualiza(Boolean preVisualiza) {
        this.preVisualiza = preVisualiza;
    }

    public String getDirBanco() {
        return dirBanco;
    }

    public void setDirBanco(String dirBanco) {
        this.dirBanco = dirBanco;
    }

    public String getImpressora() {
        return impressora;
    }

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "ParametrosPojo{" + "dirLogo=" + dirLogo + ", dirEtiqueta=" + dirEtiqueta + ", dirBanco=" + dirBanco + ", impressora=" + impressora + ", servidor=" + servidor + ", usuario=" + usuario + ", senha=" + senha + '}';
    }

}

package com.integration.nasajon.model;

import java.util.Objects;

public class LancamentoContabil {
    private String conta;
    private String historico;
    private double valor;
    private String data;
    private String natureza; // D para débito, C para crédito

    public LancamentoContabil(String conta, String historico, double valor, String data, String natureza) {
        this.conta = conta;
        this.historico = historico;
        this.valor = valor;
        this.data = data;
        this.natureza = natureza;
    }

    // Getters e Setters
    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LancamentoContabil that = (LancamentoContabil) o;
        return Double.compare(that.valor, valor) == 0 &&
                Objects.equals(conta, that.conta) &&
                Objects.equals(historico, that.historico) &&
                Objects.equals(data, that.data) &&
                Objects.equals(natureza, that.natureza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conta, historico, valor, data, natureza);
    }

    @Override
    public String toString() {
        return "LancamentoContabil{" +
                "conta='" + conta + '\'' +
                ", historico='" + historico + '\'' +
                ", valor=" + valor +
                ", data='" + data + '\'' +
                ", natureza='" + natureza + '\'' +
                '}';
    }
}

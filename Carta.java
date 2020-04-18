package br.com.zerotres.blackjack;

public class Carta {
    private Valor valor;
    private Naipe naipe;
    private boolean faceEscondida;

    public Carta(Valor valor, Naipe naipe) {
        this.valor = valor;
        this.naipe = naipe;
        faceEscondida = false;
    }

    public Valor getValor() {
        return valor;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public void setFaceEscondida(boolean faceEscondida) {
        this.faceEscondida = faceEscondida;
    }

    public boolean isFaceEscondida() {
        return faceEscondida;
    }

    @Override
    public String toString() {
        return valor.getValorTexto() + " de " + naipe.getNaipeTexto();
    }
}
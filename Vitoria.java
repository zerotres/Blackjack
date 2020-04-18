package br.com.zerotres.blackjack;

public enum Vitoria {
    DEALER(-1), EMPATE(0), JOGADOR(1);

    private int vitoria;

    Vitoria(int vitoria) {
        this.vitoria = vitoria;
    }

    public int getVitoria() {
        return vitoria;
    }
}
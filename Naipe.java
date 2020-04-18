package br.com.zerotres.blackjack;

public enum Naipe {
    COPAS(1), OUROS(2), PAUS(3), ESPADAS(4);

    private int naipe;

    Naipe(int naipe) {
        this.naipe = naipe;
    }

    public int getNaipeNumerico() {
        return naipe;
    }

    public String getNaipeTexto() {
        switch (this) {
            case COPAS:
                return "Copas";
            case OUROS:
                return "Ouros";
            case PAUS:
                return "Paus";
            case ESPADAS:
                return "Espadas";
            default:
                return null;
        }
    }
}
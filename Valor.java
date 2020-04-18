package br.com.zerotres.blackjack;

public enum Valor {
    AS(1), DOIS(2), TRES(3), QUATRO(4), CINCO(5), 
    SEIS(6), SETE(7), OITO(8), NOVE(9), DEZ(10), 
    VALETE(11), DAMA(12), REI(13);

    private int valor;

    Valor(int valor) {
        this.valor = valor;
    }

    public int getValorNumerico() {
        return valor;
    }

    public String getValorTexto() {
        switch (this) {
            case AS:
                return "Ás";
            case DOIS:
                return "Dois";
            case TRES:
                return "Três";
            case QUATRO:
                return "Quatro";
            case CINCO:
                return "Cinco";
            case SEIS:
                return "Seis";
            case SETE:
                return "Sete";
            case OITO:
                return "Oito";
            case NOVE:
                return "Nove";
            case DEZ:
                return "Dez";
            case VALETE:
                return "Valete";
            case DAMA:
                return "Dama";
            case REI:
                return "Rei";
            default:
                return null;
        }
    }

}
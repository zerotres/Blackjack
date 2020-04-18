package br.com.zerotres.blackjack;

import java.util.Collections;
import java.util.Stack;

public class Baralho extends Stack<Carta> {
    private static final long serialVersionUID = 1L;

    Baralho() {
        for (Naipe naipe : Naipe.values()) {
            for (Valor valor : Valor.values()) {
                add(new Carta(valor, naipe));
            }
        }
        embaralhar(3);
    }

    public void embaralhar(int repetir) {
        for (int contaRepetir = 0; contaRepetir < repetir; contaRepetir++)
            Collections.shuffle(this);
    }
}
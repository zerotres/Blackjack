package br.com.zerotres.blackjack;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Blackjack {
    private final int BLACKJACK = 21;
    private final int APOSTA_MINIMA = 20;
    private final int APOSTA_MAXIMA = 300;
    private final int MULTIPLICIDADE = 5;

    private Vitoria vitoria;
    private Scanner teclado = new Scanner(System.in);
    private Baralho baralho = new Baralho();
    private List<Carta> jogador = new ArrayList<>();
    private List<Carta> dealer = new ArrayList<>();
    private int fichas;
    private int valorDaAposta;
    private int rodada = 1;

    public Blackjack(int fichas) {
        this.fichas = fichas;
        valorDaAposta = 0;
        vitoria = Vitoria.EMPATE;
    }

    public void iniciar() {

        System.out.println("Blackjack Clássico");
        System.out.println("Criado por Renan Novaes");
        System.out.println("E-Mail: zerotres.br@gmail.com");

        // O jogo termina quando não há mais fichas para apostar.
        while (fichas > 0) {
            separador();
            System.out.println("[ Rodada " + rodada + " ]");

            apostar();
            distribuirCartas();

            apresentarCartas(jogador, "Jogador");
            apresentarCartas(dealer, "Dealer");

            // checar se os participantes tem um blackjack no início da partida
            if (contarPontos(jogador) == BLACKJACK && contarPontos(dealer) == BLACKJACK) {
                vitoria = Vitoria.EMPATE;
            } else if (contarPontos(jogador) == BLACKJACK) {
                vitoria = Vitoria.JOGADOR;
            } else if (contarPontos(dealer) == BLACKJACK) {
                vitoria = Vitoria.DEALER;
            } else {
                // caso não haja blackjack, apresenta a lista de jogadas até que a vez seja
                // passada para o dealer.
                int pontosJogador = apresentarJogadas();
                if (pontosJogador <= BLACKJACK && contarPontos(dealer) <= pontosJogador) {
                    dealer.get(dealer.size() - 1).setFaceEscondida(false);
                    while (contarPontos(dealer) < 17) {
                        hit(dealer, "Dealer");
                    }
                }
            }

            separador();

            determinarVencedor();
            pagarAposta();
            novaRodada();
        } // termina o loop while

        sairDoPrograma();
    }

    public void apostar() {
        while (valorDaAposta <= 0) {
            System.out.println("Fichas do jogador: " + fichas + " fichas.");
            System.out.print("Insira o valor da aposta ou -1 p/ fechar o programa: ");
            if (!teclado.hasNextInt()) {
                System.out.println("Insira um valor numérico positivo para representar a aposta.");
                teclado.next();
            } else {
                valorDaAposta = teclado.nextInt();
                if (valorDaAposta == -1) {
                    sairDoPrograma();
                }

                separador();
                boolean apostaValida = true;
                if (valorDaAposta > fichas) {
                    System.out.println("Você não possui fichas para cobrir essa aposta.");
                    apostaValida = false;
                }
                if (valorDaAposta % MULTIPLICIDADE != 0) {
                    System.out.println("O valor da aposta deve ser múltiplo de " + MULTIPLICIDADE + " fichas.");
                    apostaValida = false;
                }
                if (valorDaAposta < APOSTA_MINIMA || valorDaAposta > APOSTA_MAXIMA) {
                    System.out.println(
                            "O valor da aposta deve estar entre " + APOSTA_MINIMA + " e " + APOSTA_MAXIMA + " fichas.");
                    apostaValida = false;
                }
                if (valorDaAposta == 0 || valorDaAposta < -1) {
                    System.out.println("Insira um valor numérico positivo para representar a aposta.");
                    apostaValida = false;
                }
                if (!apostaValida) {
                    valorDaAposta = 0;
                }
            }
        } // termina o loop while
    }

    public void separador() {
        System.out.println("------------");
    }

    public void distribuirCartas() {
        for (int contaCartas = 0; contaCartas < 2; contaCartas++) {
            jogador.add(baralho.pop());
            dealer.add(baralho.pop());
        }
        dealer.get(dealer.size() - 1).setFaceEscondida(true);
    }

    public void apresentarCartas(List<Carta> participante, String nome) {
        System.out.print(nome + ": "
                + (contarPontos(participante) == 21 ? "BLACKJACK" : contarPontos(participante) + " pontos") + " - [");
        for (int indiceCarta = 0; indiceCarta < participante.size() - 1; indiceCarta++) {
            System.out.print(participante.get(indiceCarta) + ", ");
        }
        if (participante.get(participante.size() - 1).isFaceEscondida()) {
            System.out.println("OCULTA]");
        } else {
            System.out.println(participante.get(participante.size() - 1) + "]");
        }
    }

    public int contarPontos(Carta carta) {
        int valor;
        if ((carta.getValor().getValorNumerico() > 10)) {
            valor = 10;
        } else {
            valor = carta.getValor().getValorNumerico();
        }

        return valor;
    }

    public int contarPontos(List<Carta> participante) {
        int pontos = 0;
        boolean temAs = false;

        for (Carta carta : participante) {
            if (!carta.isFaceEscondida()) {
                if (carta.getValor() == Valor.AS) {
                    pontos += 1;
                    temAs = true;
                } else if (carta.getValor().getValorNumerico() > 10) {
                    pontos += 10;
                } else {
                    pontos += carta.getValor().getValorNumerico();
                }
            }
        } // termina o loop for

        // As vale 11 se não estourar um Blackjack
        if (temAs) {
            if (pontos + 10 <= BLACKJACK) {
                pontos += 10;
            }
        }

        // as + as + nove = 12

        return pontos;
    }

    public int apresentarJogadas() {
        int opcao = 0;

        do {
            System.out.println("Escolha: (1) Hit | (2) Stand");
            System.out.print("Jogador, " + contarPontos(jogador) + " pontos >> ");
            if (!teclado.hasNextInt()) {
                System.out.println("Insira o valor numérico correspondente as opções de jogada.");
                teclado.next();
            } else {
                opcao = teclado.nextInt();
                switch (opcao) {
                    case 1:
                        hit(jogador, "Jogador");
                        break;
                    case 2:
                        System.out.println("O jogador manteve suas cartas.");
                        break;
                    default:
                        System.out.println(
                                "Opção inválida.\n Insira o valor numérico correspondente as opções de jogada.");
                }
            }
        } while (opcao != 2 && contarPontos(jogador) < BLACKJACK);

        return contarPontos(jogador);
    }

    public int hit(List<Carta> participante, String nome) {
        Carta cartaSacada = baralho.pop();
        participante.add(cartaSacada);
        System.out.print("O " + nome + " retira " + cartaSacada + " (");
        if (contarPontos(cartaSacada) == Valor.AS.getValorNumerico() && participante.size() == 2) {
            System.out.println("1/11 pontos)");
        } else if (contarPontos(cartaSacada) == Valor.AS.getValorNumerico()) {
            System.out.println("1 ponto)");
        } else {
            System.out.println(contarPontos(cartaSacada) + " pontos)");
        }
        apresentarCartas(participante, nome);

        return contarPontos(participante);
    }

    public void determinarVencedor() {
        int pontosJogador = contarPontos(jogador);
        int pontosDealer = contarPontos(dealer);

        apresentarCartas(jogador, "Jogador");
        apresentarCartas(dealer, "Dealer");

        // Se o jogo empatar: configura e informa o empate
        if (pontosJogador == pontosDealer) {
            System.out.println("O jogo empatou e a aposta foi reembolsada.");
            vitoria = Vitoria.EMPATE;

            // Se não houver empate;
            // Se o jogador estiver acima de um blackjack:
            // Configura a vitória do dealer e informa o estouro da pontuação
        } else if (pontosJogador > BLACKJACK) {
            System.out.println("O Jogador estourou o limite de 21 pontos.");
            vitoria = Vitoria.DEALER;
            // Jogador abaixo de um blackjack;
            // Se tiver mais pontos que o dealer: Configura e informa a vitória do jogador
        } else {
            if ((pontosJogador > pontosDealer) && pontosDealer != BLACKJACK) {
                System.out.println("O Jogador venceu a partida com "
                        + (pontosJogador != 21 ? (pontosJogador + " pontos.") : "um Blackjack!!!"));
                vitoria = Vitoria.JOGADOR;
            }
        }

        // Se o dealer estiver acima de um blackjack:
        // Configura a vitória do jogador e informa o estouro da pontuação
        if (pontosDealer > BLACKJACK) {
            System.out.println("O Dealer estourou o limite de 21 pontos.");
            vitoria = Vitoria.JOGADOR;
            // Dealer abaixo de um blackjack;
            // Se tiver mais pontos que o jogador: Configura e informa a vitória do dealer
        } else {
            if ((pontosDealer > pontosJogador) && pontosJogador != BLACKJACK) {
                System.out.println("O Dealer venceu a partida com "
                        + (pontosDealer != 21 ? (pontosDealer + " pontos.") : "Blackjack!!!"));
                vitoria = Vitoria.DEALER;
            }
        }
    }

    public void pagarAposta() {
        if (vitoria == Vitoria.DEALER) {
            fichas -= valorDaAposta;
            System.out.println("Aposta paga para o Dealer: " + valorDaAposta + " fichas.");
        } else if (vitoria == Vitoria.JOGADOR) {
            fichas += valorDaAposta;
            System.out.println("Aposta paga para o Jogador: " + valorDaAposta + " fichas.");
        } else {
            System.out.println("Aposta reembolsada: " + valorDaAposta + " fichas.");
        }
        valorDaAposta = 0;
    }

    public void novaRodada() {
        for (Carta carta : jogador) {
            baralho.push(carta);
        }
        for (Carta carta : dealer) {
            baralho.push(carta);
        }
        jogador.clear();
        dealer.clear();
        vitoria = Vitoria.EMPATE;
        for (Carta carta : baralho) {
            carta.setFaceEscondida(false);
        }
        baralho.embaralhar(3);

        rodada++;
    }

    public void sairDoPrograma() {
        teclado.close();
        System.out.println("Obrigado por testar o Blackjack.");
        System.exit(0);
    }
}
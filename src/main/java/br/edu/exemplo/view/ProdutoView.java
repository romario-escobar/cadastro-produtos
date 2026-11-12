package br.edu.exemplo.view;

import java.math.BigDecimal;
import java.util.Scanner;
import br.edu.exemplo.controller.ProdutoController;
import br.edu.exemplo.model.Produto;

public class ProdutoView {

    private final ProdutoController controller = new ProdutoController();
    private final Scanner sc = new Scanner(System.in);

    private String ler(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private int inteiro(String msg) {
        while (true) {
            try {
                return Integer.parseInt(ler(msg));
            } catch (NumberFormatException e) {
                System.out.println("Digite um inteiro valido.");
            }
        }
    }

    private long longo(String msg) {
        while (true) {
            try {
                return Long.parseLong(ler(msg));
            } catch (NumberFormatException e) {
                System.out.println("Digite um ID valido.");
            }
        }
    }

    private BigDecimal decimal(String msg) {
        while (true) {
            try {
                return new BigDecimal(ler(msg).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor valido.");
            }
        }
    }

    private void mostrar(Produto p) {
        System.out.printf("ID: %d | Nome: %s | Quantidade: %d | R$ %s%n",
                p.getId(), p.getNome(), p.getQuantidade(), p.getValorUnitario());
    }

    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n1-Cadastrar 2-Listar 3-Consultar 4-Alterar 5-Excluir 0-Sair");
            opcao = inteiro("Opcao: ");
            try {
                switch (opcao) {
                    case 1 -> {
                        controller.cadastrar(ler("Nome: "), inteiro("Quantidade: "), decimal("Valor: "));
                        System.out.println("Produto cadastrado.");
                    }
                    case 2 -> {
                        var produtos = controller.listar();
                        if (produtos.isEmpty()) System.out.println("Nenhum produto cadastrado.");
                        else produtos.forEach(this::mostrar);
                    }
                    case 3 -> {
                        Produto p = controller.consultar(longo("ID: "));
                        if (p == null) System.out.println("Nao encontrado.");
                        else mostrar(p);
                    }
                    case 4 -> {
                        long id = longo("ID: ");
                        Produto p = controller.consultar(id);
                        if (p == null) {
                            System.out.println("Nao encontrado.");
                            break;
                        }
                        mostrar(p);
                        boolean ok = controller.alterar(id, ler("Novo nome: "),
                                inteiro("Nova quantidade: "), decimal("Novo valor: "));
                        System.out.println(ok ? "Alterado." : "Nao encontrado.");
                    }
                    case 5 -> {
                        long id = longo("ID: ");
                        Produto p = controller.consultar(id);
                        if (p == null) {
                            System.out.println("Nao encontrado.");
                            break;
                        }
                        mostrar(p);
                        if (ler("Confirma exclusao (S/N)? ").equalsIgnoreCase("S"))
                            System.out.println(controller.excluir(id) ? "Excluido." : "Nao encontrado.");
                        else
                            System.out.println("Cancelado.");
                    }
                    case 0 -> System.out.println("Encerrando.");
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }
}
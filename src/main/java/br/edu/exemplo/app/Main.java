package br.edu.exemplo.app;

import br.edu.exemplo.util.JPAUtil;
import br.edu.exemplo.view.ProdutoView;

public class Main {
    public static void main(String[] args) {
        try {
            new ProdutoView().iniciar();
        } finally {
            JPAUtil.fechar();
        }
    }
}
package br.edu.exemplo.controller;

import java.math.BigDecimal;
import java.util.List;
import br.edu.exemplo.dao.ProdutoDAO;
import br.edu.exemplo.model.Produto;

public class ProdutoController {

    private final ProdutoDAO dao = new ProdutoDAO();

    private void validar(String nome, Integer qtd, BigDecimal valor) {
        if (nome == null || nome.isBlank() || nome.trim().length() > 120)
            throw new IllegalArgumentException("Nome obrigatorio (maximo 120 caracteres).");
        if (qtd == null || qtd < 0)
            throw new IllegalArgumentException("Quantidade invalida.");
        if (valor == null || valor.signum() <= 0 || valor.scale() > 2
                || valor.precision() - valor.scale() > 10)
            throw new IllegalArgumentException("Valor unitario invalido.");
    }

    private void validarId(Long id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID invalido.");
    }

    public void cadastrar(String nome, Integer qtd, BigDecimal valor) {
        validar(nome, qtd, valor);
        dao.salvar(new Produto(nome.trim(), qtd, valor));
    }

    public List<Produto> listar() {
        return dao.listarTodos();
    }

    public Produto consultar(Long id) {
        validarId(id);
        return dao.buscarPorId(id);
    }

    public boolean alterar(Long id, String nome, Integer qtd, BigDecimal valor) {
        validarId(id);
        validar(nome, qtd, valor);
        Produto p = dao.buscarPorId(id);
        if (p == null) return false;
        p.setNome(nome.trim());
        p.setQuantidade(qtd);
        p.setValorUnitario(valor);
        dao.atualizar(p);
        return true;
    }

    public boolean excluir(Long id) {
        validarId(id);
        return dao.excluir(id);
    }
}
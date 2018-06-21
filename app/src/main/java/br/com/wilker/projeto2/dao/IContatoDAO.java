package br.com.wilker.projeto2.dao;

import java.util.List;

import br.com.wilker.projeto2.models.Contato;

public interface IContatoDAO {

    public boolean salvar(Contato contato);
    public boolean atualizar(Contato contato);
    public boolean deletar(Contato contato);
    public List<Contato> listar();

}

package br.com.tisco.dao;

import br.com.tisco.domain.Cliente;

import java.util.Collection;

public interface IClienteDAO {
    boolean cadastrar(Cliente cliente);

    public void excluir(Long cpf);

    public void alterar (Cliente cliente);

    public Cliente consultar(long cpf);

    public Collection<Cliente> buscarTodos();
}

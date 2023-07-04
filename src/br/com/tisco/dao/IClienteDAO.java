package br.com.tisco.dao;

import br.com.tisco.domain.Cliente;

import java.util.Collection;

public interface IClienteDAO {
    boolean cadastrar(Cliente cliente);

    public boolean excluir(Long cpf);

    public boolean alterar (Cliente cliente);

    public Cliente consultar(long cpf);

    public Collection<Cliente> buscarTodos();
}

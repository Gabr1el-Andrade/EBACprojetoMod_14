package br.com.tisco.dao;

import br.com.tisco.domain.Cliente;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClienteMapDAO implements IClienteDAO {

    private Map<Long, Cliente> map;

    public ClienteMapDAO(){
        map = new HashMap<>();
    }

    @Override
    public boolean cadastrar(Cliente cliente) {
        if (this.map.containsKey(cliente.getCpf())) {
            return false;
        }
       this.map.put(cliente.getCpf(), cliente);
        return true;
    }


    @Override
    public boolean excluir(Long cpf) {
       Cliente clienteCadastrado = this.map.get(cpf);

       if (clienteCadastrado != null) {
           this.map.remove(clienteCadastrado.getCpf(), clienteCadastrado);
       }

        return false;
    }

    @Override
    public boolean alterar(Cliente cliente) {
        Cliente clienteCadastrado = this.map.get(cliente.getCpf());
        if(clienteCadastrado != null){
            clienteCadastrado.setNome(cliente.getNome());
            clienteCadastrado.setCidade(cliente.getCidade());
            clienteCadastrado.setCpf(cliente.getCpf());
            clienteCadastrado.setEnd(cliente.getEnd());
            clienteCadastrado.setTel(cliente.getTel());
            clienteCadastrado.setNumero(cliente.getNumero());
            clienteCadastrado.setEstado(cliente.getEstado());

        }
        return false;
    }

    @Override
    public Cliente consultar(long cpf) {
        return this.map.get(cpf);
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return this.map.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteMapDAO that = (ClienteMapDAO) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}

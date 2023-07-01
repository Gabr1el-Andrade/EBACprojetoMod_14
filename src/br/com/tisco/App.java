package br.com.tisco;

import br.com.tisco.dao.ClienteMapDAO;
import br.com.tisco.dao.IClienteDAO;
import br.com.tisco.domain.Cliente;

import javax.swing.*;

public class App {
    private static IClienteDAO iClienteDAO;
    private static String dados;

    public static void main (String args[]){
        iClienteDAO = new ClienteMapDAO();

        String opcao = JOptionPane.showInputDialog(null,
                "Digite 1 para cadastro, 2 para consultar," + " 3 para exclusão, 4 para alteração ou 5 para sair  " ,
                " Cadastro",
                JOptionPane.INFORMATION_MESSAGE);

        while (!isOpcaoValida(opcao)){
            if("".equals(opcao)) {
                sair();
            }
            opcao = JOptionPane.showInputDialog(null,
                    "Opção invalida, Digite 1 para cadastro, 2 para consultar," + " 3 para exclusão, 4 para alteração ou 5 para sair  " ,
                    " Cadastro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        while (isOpcaoValida(opcao)){
           if (isOpcaoSair(opcao)){
               sair();
           } else if (isCadastro(opcao)) {
               JOptionPane.showInputDialog(null,
                       "Digite os dados separados por vírgula, conforme o exemplo: Nome, CPF, Telefone, Endereço, Numero, Cidade e Estado" ,
                       " Cadastro ",
                       JOptionPane.INFORMATION_MESSAGE);
               cadastar(dados);

           }


        }
    }
    private static boolean cadastar(String dados){
            String[] dadosSeparados = dados.split(",");

            // Verificar se todos os dados estão preenchidos
            if (dadosSeparados.length < 7) {
                JOptionPane.showInputDialog(null,
                        "Dados insuficientes. Preencha todos os campos." ,
                        " Erro ",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            // Verificar se algum dos dados está vazio
            for (String dado : dadosSeparados) {
                if (dado.isEmpty()) {
                    JOptionPane.showInputDialog(null,
                            "Alguns campos estão vazios. Preencha todos os campos." ,
                            " Erro ",
                            JOptionPane.INFORMATION_MESSAGE);System.out.println("Alguns campos estão vazios. Preencha todos os campos.");
                    return false;
                }
            }

            Cliente cliente = new Cliente(dadosSeparados[0], dadosSeparados[1], dadosSeparados[2],
                    dadosSeparados[3], dadosSeparados[4], dadosSeparados[5], dadosSeparados[6]);
            App.dados = dados;

        return isCadastro(String.valueOf(cliente));
    }
    public static boolean verificarClienteCadastrado(String nome, String cpf) {
        for (Cliente cliente : iClienteDAO.buscarTodos()) {
            if (cliente.getNome().equals(nome) && cliente.getCpf().equals(cpf)) {
                return true; // ENCONTRADO
            }
        }
        return false; // NÃO ENCONTRADO
    }


    private static boolean isCadastro(String opcao) {
        if ("1".equals(opcao)){
            return true;
        }
        return false;
    }

    private static boolean isOpcaoSair(String opcao) {
        if ("5".equals(opcao)){
            return true;
        }
        return false;
    }

    private static void sair() {
        JOptionPane.showInputDialog(null,
                "Até logo! " ,
                " Sair",
                JOptionPane.INFORMATION_MESSAGE);
System.exit(0);
    }

    private static boolean isOpcaoValida(String opcao) {
        if("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao) || "5".equals(opcao)){
        return true;
    }
        return false;
    }
}

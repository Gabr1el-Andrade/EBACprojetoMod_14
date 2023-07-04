package br.com.tisco;

import br.com.tisco.dao.ClienteMapDAO;
import br.com.tisco.dao.IClienteDAO;
import br.com.tisco.domain.Cliente;

import javax.swing.*;

public class App {
    private static IClienteDAO iClienteDAO;


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
        while (isOpcaoValida(opcao)) {
            if (isOpcaoSair(opcao)) {
                sair();
            } else if (isCadastro(opcao)) {
                opcao = JOptionPane.showInputDialog(null,
                        "Digite os dados separados por vírgula, conforme o exemplo: Nome, CPF, Telefone, Endereço, Numero, Cidade e Estado",
                        " Cadastro ",
                        JOptionPane.INFORMATION_MESSAGE);
                cadastar(opcao);

            } else if (isConsultar(opcao)) {
                JOptionPane.showInputDialog(null,
                        "Digite o cpf",
                        " Consultar ",
                        JOptionPane.INFORMATION_MESSAGE);
                consultar(opcao);
            } else if (isExcluir(opcao)) {
                JOptionPane.showInputDialog(null,
                        "Digite o CPF do cliente a ser excluído:",
                        "Excluir Cliente",
                        JOptionPane.INFORMATION_MESSAGE);
                excluir(opcao);
            } else if (isAlterar(opcao)) {JOptionPane.showInputDialog(null,
                    "Digite o CPF do cliente a ser alterado:",
                    "Alterar Cliente",
                    JOptionPane.INFORMATION_MESSAGE);
                alterar(opcao);
            }
            opcao = JOptionPane.showInputDialog(null,
                    "Digite 1 para cadastro, 2 para consultar," + " 3 para exclusão, 4 para alteração ou 5 para sair  " ,
                    " Cadastro",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private static void alterar(String opcao) {
            // Verifica se o CPF foi preenchido
            if (opcao.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Digite o CPF do cliente a ser alterado.",
                        "Erro",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Busca o cliente pelo CPF
            Cliente cliente = (Cliente) iClienteDAO.buscarTodos();
            if (cliente == null) {
                JOptionPane.showMessageDialog(null,
                        "Cliente com CPF " + opcao + " não encontrado.",
                        "Alterar Cliente",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Exibe os dados atuais do cliente e solicita as alterações
            String dadosAtualizados = JOptionPane.showInputDialog(null,
                    "Digite os novos dados separados por vírgula:\n" +
                            "Nome, CPF, Telefone, Endereço, Número, Cidade e Estado",
                    "Alterar Cliente",
                    JOptionPane.INFORMATION_MESSAGE);

            // Realiza as alterações no cliente
            String[] dadosSeparados = dadosAtualizados.split(",");
            cliente.setNome(dadosSeparados[0]);
            cliente.setCpf(Long.valueOf(dadosSeparados[1]));
            cliente.setTel(Long.valueOf(dadosSeparados[2]));
            cliente.setEnd(dadosSeparados[3]);
            cliente.setNumero(Integer.valueOf(dadosSeparados[4]));
            cliente.setCidade(dadosSeparados[5]);
            cliente.setEstado(dadosSeparados[6]);

            // Chama o método de alteração na implementação de IClienteDAO
            boolean alterado = iClienteDAO.alterar(cliente);
            if (alterado) {
                JOptionPane.showMessageDialog(null,
                        "Cliente com CPF " + opcao + " alterado com sucesso.",
                        "Alterar Cliente",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Ocorreu um erro ao alterar o cliente com CPF " + opcao + ".",
                        "Alterar Cliente",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }


    private static void excluir(String opcao) {
        if (opcao.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Digite o CPF do cliente a ser excluído.",
                    "Erro",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Tente converter o CPF para um número longo
        try {
            long cpfNumero = Long.parseLong(opcao);
            boolean excluido = iClienteDAO.excluir(cpfNumero);
            if (excluido) {
                JOptionPane.showMessageDialog(null,
                        "Cliente com CPF " + opcao + " excluído com sucesso.",
                        "Excluir Cliente",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Cliente com CPF " + opcao + " não encontrado.",
                        "Excluir Cliente",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "CPF inválido. Digite apenas números.",
                    "Erro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void consultar(String dados) {
       Cliente cliente = iClienteDAO.consultar(Long.parseLong(dados));
       if (cliente != null){
        JOptionPane.showMessageDialog(null,
                "Cliente encontrado " + cliente.toString(),
                " Consultar ",
                JOptionPane.INFORMATION_MESSAGE);}
       else {JOptionPane.showMessageDialog(null,
               "Cliente não encontrado ",
               " Consultar ",
               JOptionPane.INFORMATION_MESSAGE);}


    }

    private static boolean cadastar(String dados){
            String[] dadosSeparados = dados.split(",");

            // Verificar se todos os dados estão preenchidos
            if (dadosSeparados.length < 7) {
                JOptionPane.showMessageDialog(null,
                        "Dados insuficientes. Preencha todos os campos." ,
                        " Erro ",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }

            // Verificar se algum dos dados está vazio
            for (String dado : dadosSeparados) {
                if (dado.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Alguns campos estão vazios. Preencha todos os campos." ,
                            " Erro ",
                            JOptionPane.INFORMATION_MESSAGE);System.out.println("Alguns campos estão vazios. Preencha todos os campos.");
                    return false;
                }
            }
            

            Cliente cliente = new Cliente(dadosSeparados[0], dadosSeparados[1], dadosSeparados[2],
                    dadosSeparados[3], dadosSeparados[4], dadosSeparados[5], dadosSeparados[6]);

        Boolean isCadastrado = iClienteDAO.cadastrar(cliente);
        if (isCadastrado){
            JOptionPane.showMessageDialog(null,
                    "Cliente cadastrado " ,
                    " Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }else { JOptionPane.showMessageDialog(null,
                "Cliente já se encontra cadastrado " ,
                " Erro ",
                JOptionPane.INFORMATION_MESSAGE);


        }

        return false;
    }

    private static boolean isCadastro(String opcao) {
        if ("1".equals(opcao)){
            return true;
        }
        return false;
    }
    private static boolean isConsultar(String opcao){
        if ("2".equals(opcao)){
            return true;
        }
        return false;

    }
    private static boolean isExcluir (String opcao){

        if ("3".equals(opcao)){
            return true;
        }
        return false;
    }

    private static boolean isAlterar (String opcao){

        if ("4".equals(opcao)){
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
        JOptionPane.showMessageDialog(null,
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

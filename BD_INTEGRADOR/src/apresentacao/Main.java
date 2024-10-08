package apresentacao;

import java.util.ArrayList;
import java.util.Scanner;
import model.Pessoa;
import persistencia.PessoaDAO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class Main {


	public static void main(String[] args) {
		try {
			Scanner teclado = new Scanner(System.in);

			while (true) {
				System.out.println("\n== Cadastro Tabajara ==");
				System.out.println("1) Cadastrar uma pessoa");
				System.out.println("2) Mostrar o cadastro");
				System.out.println("3) Atualizar nome de uma pessoa");
				System.out.println("4) Remover uma pessoa");
				System.out.println("5) Buscar por nome");
				System.out.println("6) Encerrar");
				
				System.out.print("Escolha uma opção: ");
				int opcao = teclado.nextInt();
				teclado.nextLine();

				if (opcao == 1) {
					try {
						System.out.print("\nDigite o nome da pessoa: ");
						String nome = teclado.nextLine();
						System.out.print("Digite o CPF da pessoa: ");
						String cpf = teclado.nextLine();
						System.out.print("Digite a data de nascimento da pessoa (YYYY-MM-DD): ");
						String dataNasc = teclado.nextLine();
						Pessoa p = new Pessoa(nome, cpf, dataNasc);
						PessoaDAO pd = new PessoaDAO();
						pd.create(p);
					} catch (SQLIntegrityConstraintViolationException e) {
				
						System.out.println("\n*** Esse CPF já foi cadastrado!");
						continue;
					}
					System.out.println("\nUsuário cadastrado com sucesso!");
				}
				else if (opcao == 2) {
					PessoaDAO pd = new PessoaDAO();
					ArrayList<Pessoa> lista = pd.getAll();
					if (lista.size() == 0) {
						System.out.println("\nO cadastro está vazio.");
					} else {
						System.out.println("\nPessoas cadastradas:");
						for (Pessoa p: lista) {
							System.out.println(p);
						}
					}
				}
				else if (opcao == 3) {
					PessoaDAO pd = new PessoaDAO();
					ArrayList<Pessoa> lista = pd.getAll();
					if (lista.size() == 0) {
						System.out.println("\nO cadastro está vazio.");
					} else {
						int i = 0;
						System.out.println("\nPessoas cadastradas:");
						for (Pessoa p: lista) {
							System.out.printf("[%d] %s\n", i++, p);
						}
						System.out.print("Qual pessoa deseja atualizar? ");
						int j = teclado.nextInt();
						teclado.nextLine();
						System.out.print("Qual o novo nome? ");
						String nome = teclado.nextLine();
						Pessoa p = lista.get(j);
						p.setNome(nome);
						pd.update(p);
						System.out.println("\nCadastro atualizado com sucesso!");
					}
				}
				else if (opcao == 4) {
					PessoaDAO pd = new PessoaDAO();
					ArrayList<Pessoa> lista = pd.getAll();
					if (lista.size() == 0) {
						System.out.println("\nO cadastro está vazio.");
					} else {
						int i = 0;
						System.out.println("\nPessoas cadastradas:");
						for (Pessoa p: lista) {
							System.out.printf("[%d] %s\n", i++, p);
						}
						System.out.print("Qual pessoa deseja remover? ");
						int j = teclado.nextInt();
						teclado.nextLine();
						Pessoa p = lista.get(j);
						pd.delete(p);
						System.out.println("\nCadastro atualizado com sucesso!");
					}
				}
				else if (opcao == 5) {
					System.out.print("Digite uma parte do nome: ");
					String parte = teclado.nextLine();
					PessoaDAO pd = new PessoaDAO();
					ArrayList<Pessoa> lista = pd.findByNomeLike(parte);
					if (lista.size() == 0) {
						System.out.println("\nNenhum resultado.");
					} else {
						System.out.println("\nResultado da pesquisa:");
						for (Pessoa p: lista) {
							System.out.println(p);
						}
					}
				}
				else if (opcao == 6) {
					break;
				}
				else {
					System.out.println("Opção inválida!");
				}
			}
			teclado.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\nTenha um bom dia!");
		}
	}

}
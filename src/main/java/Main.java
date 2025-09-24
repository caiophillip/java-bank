import exception.AccountNotFoundException;
import exception.NoFundsEnoughException;
import exception.WalletNotFoundException;
import repository.AccountRepository;
import repository.InvestmentRepository;

import java.util.List;
import java.util.Scanner;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class Main {
    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem vindo ao banco!");
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar em uma conta");
            System.out.println("5 - Sacar de uma conta");
            System.out.println("6 - Transferir entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Resgatar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Historico de conta");
            System.out.println("14 - Sair");
            var option = scanner.nextInt();
            switch (option) {
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createWalletInvestment();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6 -> transfer();
                case 7 -> invest();
                case 8 -> redeem();
                case 9 -> accountRepository.list().forEach(System.out::println);
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12 -> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos atualizados");
                }
                case 13 -> accountHistory();
                case 14 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void createAccount() {
        System.out.println("Digite as chaves pix separadas por ';':");
        scanner.nextLine();
        var pix = List.of(scanner.nextLine().split(";"));
        System.out.println("Digite o valor inicial da conta:");
        var initialFunds = scanner.nextLong();
        var account = accountRepository.create(pix, initialFunds);
        System.out.println("Conta criada com sucesso: " + account);
    }

    private static void createInvestment() {
        System.out.println("Digite a taxa do investimento:");
        var tax = scanner.nextInt();
        System.out.println("Digite o valor inicial de deposito:");
        var initialFunds = scanner.nextLong();
        var investiment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado com sucesso " + investiment);
    }

    private static void createWalletInvestment() {
        System.out.println("Digite a chave pix da conta:");
        scanner.nextLine();
        var pix = scanner.nextLine();
        var account = accountRepository.findByPix(pix);
        System.out.println("Digite o id do investimento:");
        investmentRepository.list().forEach(a -> System.out.println(a.id() + " - " + a));
        var id = investmentRepository.findById(scanner.nextLong());
        var wallet = investmentRepository.initInvestiment(account, id.id());
        System.out.println("Investimento realizado com sucesso: " + wallet);
    }

    private static void deposit() {
        System.out.println("Digite a chave pix da conta:");
        scanner.nextLine();
        var pix = scanner.nextLine();
        System.out.println("Digite o valor do deposito:");
        var amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Deposito realizado com sucesso");
    }

    private static void withdraw() {
        System.out.println("Digite a chave pix da conta:");
        scanner.nextLine();
        var pix = scanner.nextLine();
        System.out.println("Digite o valor do saque:");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Saque realizado com sucesso");

    }

    private static void transfer() {
        System.out.println("Digite a chave pix da conta de origem:");
        scanner.nextLine();
        var sourcePix = scanner.nextLine();
        System.out.println("Digite a chave pix da conta de destino:");
        var targetPix = scanner.nextLine();
        System.out.println("Digite o valor da transferencia:");
        var amount = scanner.nextLong();
        accountRepository.transferMoney(sourcePix, targetPix, amount);
        System.out.println("Transferencia de " + amount + " de " + sourcePix + " para " + targetPix + " realizada com sucesso");
    }

    private static void invest() {
        System.out.println("Digite a chave pix da conta:");
        scanner.nextLine();
        var pix = scanner.nextLine();
        System.out.println("Digite o valor do investimento:");
        var amount = scanner.nextLong();
        try {
            var wallet = investmentRepository.deposit(pix, amount);
            System.out.println("Investimento realizado com sucesso: " + wallet);
        } catch (WalletNotFoundException | AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void redeem() {
        System.out.println("Digite a chave pix da conta:");
        scanner.nextLine();
        var pix = scanner.nextLine();
        System.out.println("Digite o valor do resgate:");
        var amount = scanner.nextLong();
        var wallet = investmentRepository.withdraw(pix, amount);
        System.out.println("Resgate realizado com sucesso: " + wallet);
    }

    private static void accountHistory() {
        System.out.println("Digite a chave pix da conta:");
        scanner.nextLine();
        var pix = scanner.nextLine();
        try {
            var sortedHistory = accountRepository.getHistory(pix);
            System.out.println("Historico da conta com a chave pix " + pix + ":");
            sortedHistory.forEach((k, v) -> {
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactionId());
                System.out.println(v.getFirst().description());
                System.out.println("R$" + (v.size() / 100) + (v.size() % 100));
            });
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

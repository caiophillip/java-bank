package repository;

import exception.AccountNotFoundException;
import exception.PixInUseException;
import model.AccountWallet;
import static repository.CommonsRepository.checkFundsForTransaction;

import java.util.List;

public class AccountRepository {
    private List<AccountWallet> accounts;

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        var pixInUse = accounts.stream()
                .flatMap(a -> a.getPix().stream())
                .toList();
        for (var p : pix) {
            if (pixInUse.contains(p)) {
                throw new PixInUseException("Chave pix ja em uso: " + p);
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long amunt) {
        var account = findByPix(pix);
        account.addMoney(amunt, "Deposito de " + amunt + " na conta");
    }

    public void withdraw(final String pix, final long amount) {
        var account = findByPix(pix);
        checkFundsForTransaction(account, amount);
        account.reduceMoney(amount);
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var sourceAccount = findByPix(sourcePix);
        var targetAccount = findByPix(targetPix);
        checkFundsForTransaction(sourceAccount, amount);
        var money = sourceAccount.reduceMoney(amount);
        targetAccount.addMoney(money, targetAccount.getService(), "Transferencia de " + amount + " da conta " + sourcePix);
    }

    public AccountWallet findByPix(final String pix) {
        return this.accounts.stream()
                .filter(account -> account.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Conta nao encontrada"));
    }

    public List<AccountWallet> list() {
        return this.accounts;
    }

}

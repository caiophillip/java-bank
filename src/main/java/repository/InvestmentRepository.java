package repository;

import exception.AccountWithInvestmentException;
import exception.InvestmentNotFoundException;
import exception.PixInUseException;
import exception.WalletNotFoundException;
import model.AccountWallet;
import model.Investiment;
import model.InvestimentWallet;
import model.Wallet;

import java.util.ArrayList;
import java.util.List;

import static repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {
    private long nextId;
    private final List<Investiment> investments = new ArrayList<>();
    private final List<InvestimentWallet> wallets = new ArrayList<>();

    public Investiment create(final long tax, final long initialFunds) {
        this.nextId++;
        var investiment = new Investiment(this.nextId, tax, initialFunds);
        this.investments.add(investiment);
        return investiment;
    }

    public InvestimentWallet initInvestiment(final AccountWallet account, final long id) {
        var accountInUse = wallets.stream()
                .map(InvestimentWallet::getAccount).toList();
        if (accountInUse.contains(account)) {
            throw new AccountWithInvestmentException("A conta " + account + " ja possui um investimento ativo" );
        }


        var investiment = findById(id);

        checkFundsForTransaction(account, investiment.initialFunds());
        var wallet = new InvestimentWallet(investiment, account, investiment.initialFunds());
        wallets.add(wallet);
        return wallet;

    }

    public InvestimentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Investimetto");
        return wallet;
    }

    public InvestimentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "Resgate de " + funds + " da carteira " + pix);
        if (wallet.getFunds() == 0) {
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount(final long percent) {
        this.wallets.forEach(wallet -> wallet.updateAmount(percent));
    }

    public Investiment findById(final long id) {
        return this.investments.stream()
                .filter(investiment -> investiment.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestmentNotFoundException("Investimento nao encontrado"));
    }

    public InvestimentWallet findWalletByAccountPix(final String pix) {
        return this.wallets.stream()
                .filter(wallets -> wallets.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("Carteira nao encontrada"));
    }

    public List<InvestimentWallet> listWallets() {
        return this.wallets;
    }

    public List<Investiment> list() {
        return this.investments;
    }
}

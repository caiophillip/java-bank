package model;

import exception.NoFundsEnoughException;
import lombok.Getter;

import java.util.List;
import static model.BankService.ACCOUNT;

@Getter
public class AccountWallet extends Wallet{
    private final List<String> pix;
    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amount, List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "valor de criação da conta");
    }

    public void addMoney(final long amount, final String description){
        var money = generatedMoney(amount, description);
        this.money.addAll(money);

    }

    @Override
    public String toString() {
        return super.toString() + "AccountWallet{" +
                "pix=" + pix +
                '}';
    }
}

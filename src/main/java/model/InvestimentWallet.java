package model;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static model.BankService.INVESTMENT;

public class InvestimentWallet extends Wallet{
    private final Investiment investiment;
    private final AccountWallet account;

    public InvestimentWallet(Investiment investiment, AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investiment = investiment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investimento");
    }

    public void updateAmount(final long percent) {
        var amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getService(), "rendimentos", OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }
}

package repository;

import exception.NoFundsEnoughException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.Money;
import model.Wallet;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static model.BankService.ACCOUNT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonsRepository {
    public static void checkFundsForTransaction(final Wallet source , final long amount) {
        if (source.getFunds() < amount) {
            throw new NoFundsEnoughException("Saldo insuficiente para a transacao");

        }
    }
    public static List<Money> generatedMoney(final UUID transactionId, final long amount, final String description) {
        var history = new model.MoneyAudit(transactionId, ACCOUNT, description, java.time.OffsetDateTime.now());
        return Stream.generate(() -> new Money(history)).limit(amount).toList();
    }
}

package net.prussaq.service;

import lombok.AllArgsConstructor;
import net.prussaq.entity.AccountEntity;
import net.prussaq.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void increaseMoney(BigDecimal money) {
        Optional<AccountEntity> userOptional = accountRepository.findById("USER");
        userOptional.ifPresent(accountEntity -> {
            accountEntity.setMoney(accountEntity.getMoney().add(money));
            accountRepository.save(accountEntity);
        });
    }

    public void decreaseMoney(BigDecimal money) {
        Optional<AccountEntity> userOptional = accountRepository.findById("USER");
        userOptional.ifPresent(accountEntity -> {
            accountEntity.setMoney(accountEntity.getMoney().subtract(money));
            accountRepository.save(accountEntity);
        });
    }

    public BigDecimal getMoney() {
        Optional<AccountEntity> userOptional = accountRepository.findById("USER");
        return userOptional.map(AccountEntity::getMoney).orElseThrow();
    }
}

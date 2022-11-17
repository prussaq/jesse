package net.prussaq.repository;

import net.prussaq.entity.MoneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRepository extends JpaRepository<MoneyEntity, Long> {

}

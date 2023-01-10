package net.prussaq.jesse.repository;

import net.prussaq.jesse.entity.MoneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyRepository extends JpaRepository<MoneyEntity, Long> {

}

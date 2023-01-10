package net.prussaq.jesse.repository;

import net.prussaq.jesse.entity.SellEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellRepository extends JpaRepository<SellEntity, Long> {

    List<SellEntity> findAllByBuyId(Long buyDealId);
}

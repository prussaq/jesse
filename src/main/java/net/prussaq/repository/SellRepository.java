package net.prussaq.repository;

import net.prussaq.entity.SellEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellRepository extends JpaRepository<SellEntity, Long> {

    List<SellEntity> findAllByBuyId(Long buyDealId);
}

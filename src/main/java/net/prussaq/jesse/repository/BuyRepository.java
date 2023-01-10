package net.prussaq.jesse.repository;

import net.prussaq.jesse.entity.BuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<BuyEntity, Long> {

    List<BuyEntity> findAllByTicket(String ticket);

    @Query("SELECT DISTINCT ticket FROM BuyEntity")
    List<String> getDistinctTickets();
}

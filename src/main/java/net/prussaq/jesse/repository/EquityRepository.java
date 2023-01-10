package net.prussaq.jesse.repository;

import net.prussaq.jesse.entity.EquityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquityRepository extends JpaRepository<EquityEntity, String> {

}

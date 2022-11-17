package net.prussaq.repository;

import net.prussaq.entity.EquityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquityRepository extends JpaRepository<EquityEntity, String> {

}

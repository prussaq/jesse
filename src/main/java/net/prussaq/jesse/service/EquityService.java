package net.prussaq.jesse.service;

import lombok.AllArgsConstructor;
import net.prussaq.jesse.entity.EquityEntity;
import net.prussaq.jesse.model.Equity;
import net.prussaq.jesse.repository.EquityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquityService {

    private final EquityRepository repository;
    private final ModelMapper mapper;

    public void put(Equity equityDto) {
        EquityEntity equityEntity = mapper.map(equityDto, EquityEntity.class);
        repository.save(equityEntity);
    }

    public Equity get(String ticket) {
        return repository.findById(ticket)
                .map(equityEntity -> mapper.map(equityEntity, Equity.class))
                .orElse(null);
    }

    public String getName(String ticket) {
        return repository.findById(ticket).map(EquityEntity::getName).orElse("");
    }

}

package net.prussaq.service;

import lombok.AllArgsConstructor;
import net.prussaq.entity.EquityEntity;
import net.prussaq.model.Equity;
import net.prussaq.repository.EquityRepository;
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

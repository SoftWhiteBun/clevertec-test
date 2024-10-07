package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.domain.Cake;
import ru.clevertec.entity.CakeEntity;
import ru.clevertec.exception.CakeNotFoundException;
import ru.clevertec.mapper.CakeMapper;
import ru.clevertec.repository.CakeRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CakeService {
    private final CakeRepository cakeRepository;
    private final CakeMapper cakeMapper;

    public List<Cake> getCakes() {
        List<CakeEntity> cakeEntities = cakeRepository.getCakes();
        return cakeMapper.toDomains(cakeEntities);
    }

    public Cake getCakeById(UUID cakeId) {
        CakeEntity cakeEntity = cakeRepository.getCakeById(cakeId)
                .orElseThrow(() -> CakeNotFoundException.byCakeId(cakeId));
        return cakeMapper.toDomain(cakeEntity);
    }

    public Cake create(Cake cake) {
        CakeEntity cakeEntity = cakeMapper.toEntity(cake);
        CakeEntity createdEntity = (cakeRepository.create(cakeEntity));
        return cakeMapper.toDomain(createdEntity);
    }

    public Cake update(UUID cakeId, Cake newCake) {
        CakeEntity cakeEntity = cakeMapper.toEntity(newCake);
        CakeEntity updatedEntity = (cakeRepository.update(cakeId, cakeEntity)).orElseThrow();
        return cakeMapper.toDomain(updatedEntity);
    }

    public void deleteById(UUID cakeId) {
        cakeRepository.delete(cakeId);
    }
}
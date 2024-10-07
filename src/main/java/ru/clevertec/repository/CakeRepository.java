package ru.clevertec.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.common.CakeType;
import ru.clevertec.entity.CakeEntity;
import ru.clevertec.exception.CakeNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CakeRepository {
    private static final List<CakeEntity> db = List.of(
            new CakeEntity(UUID.randomUUID(), "cake1", CakeType.HOMEMADE, OffsetDateTime.now().plusDays(1)),
            new CakeEntity(UUID.randomUUID(), "cake2", CakeType.PASTRY, OffsetDateTime.now().plusDays(2)),
            new CakeEntity(UUID.randomUUID(), "cake3", CakeType.SHOP, OffsetDateTime.now().plusDays(3)),
            new CakeEntity(UUID.randomUUID(), "cake4", CakeType.SHOP, OffsetDateTime.now().plusDays(4)),
            new CakeEntity(UUID.randomUUID(), "cake5", CakeType.PASTRY, OffsetDateTime.now().plusDays(5)),
            new CakeEntity(UUID.randomUUID(), "cake6", CakeType.HOMEMADE, OffsetDateTime.now())
    );

    public List<CakeEntity> getCakes(){
        return db;
    }

    public Optional<CakeEntity> getCakeById(UUID cakeId){
        return db.stream()
                .filter(cakeEntity -> cakeEntity.getId().equals(cakeId))
                .findFirst();
    }

    public CakeEntity create(CakeEntity cakeEntity){
        return cakeEntity;
    }

    public Optional<CakeEntity> update(UUID cakeId , CakeEntity newCakeEntity){
        return Optional.of(newCakeEntity.setId(cakeId));
    }

    public void delete(UUID cakeId){
        CakeEntity cakeEntity = getCakeById(cakeId)
                .orElseThrow(() -> CakeNotFoundException.byCakeId(cakeId));
        db.remove(cakeEntity);
    }
}
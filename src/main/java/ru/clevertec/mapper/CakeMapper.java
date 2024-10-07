package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.domain.Cake;
import ru.clevertec.entity.CakeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CakeMapper {

    Cake toDomain(CakeEntity cakeEntity);

    List<Cake> toDomains(List<CakeEntity> cakeEntities);

    CakeEntity toEntity(Cake cake);
}
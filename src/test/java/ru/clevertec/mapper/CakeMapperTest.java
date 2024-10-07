package ru.clevertec.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.common.CakeType;
import ru.clevertec.domain.Cake;
import ru.clevertec.entity.CakeEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeMapperTest {
    @Mock
    CakeMapper cakeMapper;

    @Test
    void shouldMapToDomain() {
        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        when(cakeMapper.toDomain(cakeEntity)).thenReturn(cake);

        //when
        Cake result = cakeMapper.toDomain(cakeEntity);

        //then
        assertNotNull(cake);
        assertEquals(cake, result);
    }

    @Test
    void shouldMapToDomains() {
        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        List<CakeEntity> cakeEntities = List.of(cakeEntity);
        List<Cake> cakes = List.of(cake);
        when(cakeMapper.toDomains(cakeEntities)).thenReturn(cakes);

        //when
        List<Cake> result = cakeMapper.toDomains(cakeEntities);

        //then
        assertNotNull(cake);
        assertEquals(cake, result.getFirst());
    }

    @Test
    void shouldMapToEntity() {
        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        when(cakeMapper.toEntity(cake)).thenReturn(cakeEntity);

        //when
        CakeEntity result = cakeMapper.toEntity(cake);

        //then
        assertNotNull(cake);
        assertEquals(cakeEntity, result);
    }
}
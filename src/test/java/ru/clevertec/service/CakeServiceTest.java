package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.common.CakeType;
import ru.clevertec.domain.Cake;
import ru.clevertec.entity.CakeEntity;
import ru.clevertec.mapper.CakeMapper;
import ru.clevertec.repository.CakeRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CakeServiceTest {
    @Mock
    private CakeRepository cakeRepository;
    @Mock
    private CakeMapper cakeMapper;
    @InjectMocks
    private CakeService cakeService;

    @Test
    void shouldGetAll() {

        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        List<CakeEntity> cakeEntities = List.of(cakeEntity);
        List<Cake> cakes = List.of(cake);
        when(cakeRepository.getCakes()).thenReturn(cakeEntities);
        when(cakeMapper.toDomains(cakeEntities)).thenReturn(cakes);

        //when
        List<Cake> actualCakes = cakeService.getCakes();

        //then
        assertFalse(actualCakes.isEmpty());
        assertEquals(cakes, actualCakes);
    }

    @Test
    void shouldGetCakeById() {

        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());

        when(cakeRepository.getCakeById(cakeId)).thenReturn(Optional.of(cakeEntity));
        when(cakeMapper.toDomain(cakeEntity)).thenReturn(cake);

        //when
        Cake actualCake = cakeService.getCakeById(cakeId);

        //then
        assertEquals(cake.getId(), actualCake.getId());
        verify(cakeRepository).getCakeById(cakeId);
        verify(cakeMapper).toDomain(cakeEntity);
    }

    @Test
    void shouldCreateNewCake() {
        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());


        when(cakeMapper.toEntity(cake)).thenReturn(cakeEntity);
        when(cakeRepository.create(cakeEntity)).thenReturn(cakeEntity);
        when(cakeMapper.toDomain(cakeEntity)).thenReturn(cake);

        //when
        Cake actualCake = cakeService.create(cake);

        //then
        assertEquals(cake.getId(), actualCake.getId());
        verify(cakeRepository).create(cakeEntity);
    }

    @Test
    void shouldUpdate() {

        //given
        UUID cakeId = UUID.randomUUID();
        CakeEntity cakeEntity = new CakeEntity(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());
        Cake cake = new Cake(cakeId, "cake", CakeType.HOMEMADE, OffsetDateTime.now());

        when(cakeMapper.toEntity(cake)).thenReturn(cakeEntity);
        when(cakeRepository.update(cakeId, cakeEntity)).thenReturn(Optional.of(cakeEntity));
        when(cakeMapper.toDomain(cakeEntity)).thenReturn(cake);

        //when
        cakeService.update(cakeId, cake);

        //then
        verify(cakeRepository).update(cakeId, cakeEntity);
    }

    @Test
    void shouldDeleteById() {
        //given
        UUID cakeId = UUID.randomUUID();
        //when
        cakeService.deleteById(cakeId);
        //then
        verify(cakeRepository).delete(cakeId);
    }
}
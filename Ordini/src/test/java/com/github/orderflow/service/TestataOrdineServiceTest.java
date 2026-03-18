package com.github.orderflow.service;

import com.github.orderflow.entity.TestataOrdine;
import com.github.orderflow.enumModel.StatoOrdine;
import com.github.orderflow.model.TestataOrdineDTO;
import com.github.orderflow.repository.TestataOrdineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestataOrdineServiceTest {

    @Mock
    private TestataOrdineRepository repository;

    @MockBean
    private TestataOrdineService testataOrdineService;

    @InjectMocks
    private TestataOrdineService service;

    private TestataOrdineDTO ordineDTO;
    private TestataOrdine ordine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup DTO and entity for testing
        ordineDTO = new TestataOrdineDTO();
        ordineDTO.setDescrizione("Test order");
        ordineDTO.setDataConsegna(LocalDate.now());
        ordineDTO.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);

        ordine = new TestataOrdine();
        ordine.setDescrizione("Test order");
        ordine.setDataConsegna(LocalDate.now());
        ordine.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
    }

    @Test
    void testGetAllOrdiniDTO() {
        // Given
        when(repository.findAll()).thenReturn(List.of(ordine));

        // When
        List<TestataOrdineDTO> ordini = service.getAllOrdiniDTO();

        // Then
        assertNotNull(ordini);
        assertEquals(1, ordini.size());
        assertEquals("Test order", ordini.get(0).getDescrizione());
    }

    @Test
    void testCreaOrdineDTO() {
        // Given
        when(repository.save(any(TestataOrdine.class))).thenReturn(ordine);

        // When
        TestataOrdineDTO createdOrdine = service.creaOrdineDTO(ordineDTO);

        // Then
        assertNotNull(createdOrdine);
        assertEquals("Test order", createdOrdine.getDescrizione());
        verify(repository, times(1)).save(any(TestataOrdine.class));
    }

    @Test
    void testAggiornaOrdineDTO_OrdineEsistente() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.of(ordine));
        when(repository.save(any(TestataOrdine.class))).thenReturn(ordine);

        // When
        TestataOrdineDTO updatedOrdine = service.aggiornaOrdineDTO(1, ordineDTO);

        // Then
        assertNotNull(updatedOrdine);
        assertEquals("Test order", updatedOrdine.getDescrizione());
        verify(repository, times(1)).save(any(TestataOrdine.class));
    }

    @Test
    void testAggiornaOrdineDTO_OrdineNonEsistente() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.empty());

        // When
        TestataOrdineDTO updatedOrdine = service.aggiornaOrdineDTO(1, ordineDTO);

        // Then
        assertNull(updatedOrdine);
    }

    @Test
    void testAggiornaStatoDTO() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.of(ordine));
        when(repository.save(any(TestataOrdine.class))).thenReturn(ordine);

        // When
        TestataOrdineDTO updatedOrdine = service.aggiornaStatoDTO(1, StatoOrdine.SPEDITO);

        // Then
        assertNotNull(updatedOrdine);
        assertEquals(StatoOrdine.SPEDITO, updatedOrdine.getStatoOrdine());
        verify(repository, times(1)).save(any(TestataOrdine.class));
    }

    @Test
    void testAggiornaStatoDTO_OrdineNonEsistente() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.empty());

        // When
        TestataOrdineDTO updatedOrdine = service.aggiornaStatoDTO(1, StatoOrdine.SPEDITO);

        // Then
        assertNull(updatedOrdine);
    }

    @Test
    void testCancellaOrdine() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.of(ordine));

        // When
        service.cancellaOrdine(1);

        // Then
        verify(repository, times(1)).delete(ordine);
    }

    @Test
    void testCancellaOrdine_OrdineNonEsistente() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.empty());

        // When
        try {
            service.cancellaOrdine(1);
            fail("Exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Order with id 1 not found.", e.getMessage());
        }
    }

    @Test
    void testCancellaOrdine_ErroreLockOttimistico() {
        // Given
        when(repository.findById(1)).thenReturn(Optional.of(ordine));

        doThrow(ObjectOptimisticLockingFailureException.class)
                .when(repository).delete(any(TestataOrdine.class));

        // When
        try {
            service.cancellaOrdine(1);
            fail("Exception should have been thrown");
        } catch (IllegalStateException e) {
            assertEquals("This order has already been modified or deleted by another user.", e.getMessage());
        }
    }
}
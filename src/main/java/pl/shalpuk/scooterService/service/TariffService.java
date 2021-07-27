package pl.shalpuk.scooterService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.repository.TariffRepository;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class TariffService {

    private final TariffRepository tariffRepository;

    public TariffService(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public Tariff getTariffById(UUID tariffId) {
        return tariffRepository.findById(tariffId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tariff with id = %s is not found", tariffId)));
    }

    public Page<Tariff> getAllTariffsPage(PageRequest pageRequest) {
        return tariffRepository.findAll(pageRequest);
    }
}

package com.pae.well.service.impl;

import com.pae.well.domain.Battery;
import com.pae.well.repository.BatteryRepository;
import com.pae.well.service.BatteryService;
import com.pae.well.service.dto.BatteryDTO;
import com.pae.well.service.mapper.BatteryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Battery}.
 */
@Service
@Transactional
public class BatteryServiceImpl implements BatteryService {

    private final Logger log = LoggerFactory.getLogger(BatteryServiceImpl.class);

    private final BatteryRepository batteryRepository;

    private final BatteryMapper batteryMapper;

    public BatteryServiceImpl(BatteryRepository batteryRepository, BatteryMapper batteryMapper) {
        this.batteryRepository = batteryRepository;
        this.batteryMapper = batteryMapper;
    }

    @Override
    public BatteryDTO save(BatteryDTO batteryDTO) {
        log.debug("Request to save Battery : {}", batteryDTO);
        Battery battery = batteryMapper.toEntity(batteryDTO);
        battery = batteryRepository.save(battery);
        return batteryMapper.toDto(battery);
    }

    @Override
    public BatteryDTO update(BatteryDTO batteryDTO) {
        log.debug("Request to update Battery : {}", batteryDTO);
        Battery battery = batteryMapper.toEntity(batteryDTO);
        battery = batteryRepository.save(battery);
        return batteryMapper.toDto(battery);
    }

    @Override
    public Optional<BatteryDTO> partialUpdate(BatteryDTO batteryDTO) {
        log.debug("Request to partially update Battery : {}", batteryDTO);

        return batteryRepository
            .findById(batteryDTO.getId())
            .map(existingBattery -> {
                batteryMapper.partialUpdate(existingBattery, batteryDTO);

                return existingBattery;
            })
            .map(batteryRepository::save)
            .map(batteryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BatteryDTO> findAll() {
        log.debug("Request to get all Batteries");
        return batteryRepository.findAll().stream().map(batteryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BatteryDTO> findOne(Long id) {
        log.debug("Request to get Battery : {}", id);
        return batteryRepository.findById(id).map(batteryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Battery : {}", id);
        batteryRepository.deleteById(id);
    }
}

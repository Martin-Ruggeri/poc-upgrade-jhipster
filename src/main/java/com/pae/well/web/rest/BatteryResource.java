package com.pae.well.web.rest;

import com.pae.well.repository.BatteryRepository;
import com.pae.well.service.BatteryService;
import com.pae.well.service.dto.BatteryDTO;
import com.pae.well.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pae.well.domain.Battery}.
 */
@RestController
@RequestMapping("/api")
public class BatteryResource {

    private final Logger log = LoggerFactory.getLogger(BatteryResource.class);

    private static final String ENTITY_NAME = "wellBattery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BatteryService batteryService;

    private final BatteryRepository batteryRepository;

    public BatteryResource(BatteryService batteryService, BatteryRepository batteryRepository) {
        this.batteryService = batteryService;
        this.batteryRepository = batteryRepository;
    }

    /**
     * {@code POST  /batteries} : Create a new battery.
     *
     * @param batteryDTO the batteryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new batteryDTO, or with status {@code 400 (Bad Request)} if the battery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/batteries")
    public ResponseEntity<BatteryDTO> createBattery(@Valid @RequestBody BatteryDTO batteryDTO) throws URISyntaxException {
        log.debug("REST request to save Battery : {}", batteryDTO);
        if (batteryDTO.getId() != null) {
            throw new BadRequestAlertException("A new battery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BatteryDTO result = batteryService.save(batteryDTO);
        return ResponseEntity
            .created(new URI("/api/batteries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /batteries/:id} : Updates an existing battery.
     *
     * @param id the id of the batteryDTO to save.
     * @param batteryDTO the batteryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batteryDTO,
     * or with status {@code 400 (Bad Request)} if the batteryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the batteryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/batteries/{id}")
    public ResponseEntity<BatteryDTO> updateBattery(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BatteryDTO batteryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Battery : {}, {}", id, batteryDTO);
        if (batteryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batteryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batteryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BatteryDTO result = batteryService.update(batteryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batteryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /batteries/:id} : Partial updates given fields of an existing battery, field will ignore if it is null
     *
     * @param id the id of the batteryDTO to save.
     * @param batteryDTO the batteryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated batteryDTO,
     * or with status {@code 400 (Bad Request)} if the batteryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the batteryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the batteryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/batteries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BatteryDTO> partialUpdateBattery(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BatteryDTO batteryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Battery partially : {}, {}", id, batteryDTO);
        if (batteryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, batteryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!batteryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BatteryDTO> result = batteryService.partialUpdate(batteryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, batteryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /batteries} : get all the batteries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of batteries in body.
     */
    @GetMapping("/batteries")
    public List<BatteryDTO> getAllBatteries() {
        log.debug("REST request to get all Batteries");
        return batteryService.findAll();
    }

    /**
     * {@code GET  /batteries/:id} : get the "id" battery.
     *
     * @param id the id of the batteryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the batteryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/batteries/{id}")
    public ResponseEntity<BatteryDTO> getBattery(@PathVariable Long id) {
        log.debug("REST request to get Battery : {}", id);
        Optional<BatteryDTO> batteryDTO = batteryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(batteryDTO);
    }

    /**
     * {@code DELETE  /batteries/:id} : delete the "id" battery.
     *
     * @param id the id of the batteryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/batteries/{id}")
    public ResponseEntity<Void> deleteBattery(@PathVariable Long id) {
        log.debug("REST request to delete Battery : {}", id);
        batteryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

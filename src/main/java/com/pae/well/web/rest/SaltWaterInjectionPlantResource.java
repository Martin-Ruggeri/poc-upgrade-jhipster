package com.pae.well.web.rest;

import com.pae.well.repository.SaltWaterInjectionPlantRepository;
import com.pae.well.service.SaltWaterInjectionPlantService;
import com.pae.well.service.dto.SaltWaterInjectionPlantDTO;
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
 * REST controller for managing {@link com.pae.well.domain.SaltWaterInjectionPlant}.
 */
@RestController
@RequestMapping("/api")
public class SaltWaterInjectionPlantResource {

    private final Logger log = LoggerFactory.getLogger(SaltWaterInjectionPlantResource.class);

    private static final String ENTITY_NAME = "wellSaltWaterInjectionPlant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaltWaterInjectionPlantService saltWaterInjectionPlantService;

    private final SaltWaterInjectionPlantRepository saltWaterInjectionPlantRepository;

    public SaltWaterInjectionPlantResource(
        SaltWaterInjectionPlantService saltWaterInjectionPlantService,
        SaltWaterInjectionPlantRepository saltWaterInjectionPlantRepository
    ) {
        this.saltWaterInjectionPlantService = saltWaterInjectionPlantService;
        this.saltWaterInjectionPlantRepository = saltWaterInjectionPlantRepository;
    }

    /**
     * {@code POST  /salt-water-injection-plants} : Create a new saltWaterInjectionPlant.
     *
     * @param saltWaterInjectionPlantDTO the saltWaterInjectionPlantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saltWaterInjectionPlantDTO, or with status {@code 400 (Bad Request)} if the saltWaterInjectionPlant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salt-water-injection-plants")
    public ResponseEntity<SaltWaterInjectionPlantDTO> createSaltWaterInjectionPlant(
        @Valid @RequestBody SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to save SaltWaterInjectionPlant : {}", saltWaterInjectionPlantDTO);
        if (saltWaterInjectionPlantDTO.getId() != null) {
            throw new BadRequestAlertException("A new saltWaterInjectionPlant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaltWaterInjectionPlantDTO result = saltWaterInjectionPlantService.save(saltWaterInjectionPlantDTO);
        return ResponseEntity
            .created(new URI("/api/salt-water-injection-plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salt-water-injection-plants/:id} : Updates an existing saltWaterInjectionPlant.
     *
     * @param id the id of the saltWaterInjectionPlantDTO to save.
     * @param saltWaterInjectionPlantDTO the saltWaterInjectionPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saltWaterInjectionPlantDTO,
     * or with status {@code 400 (Bad Request)} if the saltWaterInjectionPlantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saltWaterInjectionPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salt-water-injection-plants/{id}")
    public ResponseEntity<SaltWaterInjectionPlantDTO> updateSaltWaterInjectionPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SaltWaterInjectionPlant : {}, {}", id, saltWaterInjectionPlantDTO);
        if (saltWaterInjectionPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saltWaterInjectionPlantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saltWaterInjectionPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SaltWaterInjectionPlantDTO result = saltWaterInjectionPlantService.update(saltWaterInjectionPlantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saltWaterInjectionPlantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /salt-water-injection-plants/:id} : Partial updates given fields of an existing saltWaterInjectionPlant, field will ignore if it is null
     *
     * @param id the id of the saltWaterInjectionPlantDTO to save.
     * @param saltWaterInjectionPlantDTO the saltWaterInjectionPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saltWaterInjectionPlantDTO,
     * or with status {@code 400 (Bad Request)} if the saltWaterInjectionPlantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the saltWaterInjectionPlantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the saltWaterInjectionPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/salt-water-injection-plants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SaltWaterInjectionPlantDTO> partialUpdateSaltWaterInjectionPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SaltWaterInjectionPlant partially : {}, {}", id, saltWaterInjectionPlantDTO);
        if (saltWaterInjectionPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, saltWaterInjectionPlantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!saltWaterInjectionPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SaltWaterInjectionPlantDTO> result = saltWaterInjectionPlantService.partialUpdate(saltWaterInjectionPlantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saltWaterInjectionPlantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /salt-water-injection-plants} : get all the saltWaterInjectionPlants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saltWaterInjectionPlants in body.
     */
    @GetMapping("/salt-water-injection-plants")
    public List<SaltWaterInjectionPlantDTO> getAllSaltWaterInjectionPlants() {
        log.debug("REST request to get all SaltWaterInjectionPlants");
        return saltWaterInjectionPlantService.findAll();
    }

    /**
     * {@code GET  /salt-water-injection-plants/:id} : get the "id" saltWaterInjectionPlant.
     *
     * @param id the id of the saltWaterInjectionPlantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saltWaterInjectionPlantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salt-water-injection-plants/{id}")
    public ResponseEntity<SaltWaterInjectionPlantDTO> getSaltWaterInjectionPlant(@PathVariable Long id) {
        log.debug("REST request to get SaltWaterInjectionPlant : {}", id);
        Optional<SaltWaterInjectionPlantDTO> saltWaterInjectionPlantDTO = saltWaterInjectionPlantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saltWaterInjectionPlantDTO);
    }

    /**
     * {@code DELETE  /salt-water-injection-plants/:id} : delete the "id" saltWaterInjectionPlant.
     *
     * @param id the id of the saltWaterInjectionPlantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salt-water-injection-plants/{id}")
    public ResponseEntity<Void> deleteSaltWaterInjectionPlant(@PathVariable Long id) {
        log.debug("REST request to delete SaltWaterInjectionPlant : {}", id);
        saltWaterInjectionPlantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

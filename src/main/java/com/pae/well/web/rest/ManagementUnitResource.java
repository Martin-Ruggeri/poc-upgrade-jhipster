package com.pae.well.web.rest;

import com.pae.well.repository.ManagementUnitRepository;
import com.pae.well.service.ManagementUnitService;
import com.pae.well.service.dto.ManagementUnitDTO;
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
 * REST controller for managing {@link com.pae.well.domain.ManagementUnit}.
 */
@RestController
@RequestMapping("/api")
public class ManagementUnitResource {

    private final Logger log = LoggerFactory.getLogger(ManagementUnitResource.class);

    private static final String ENTITY_NAME = "wellManagementUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagementUnitService managementUnitService;

    private final ManagementUnitRepository managementUnitRepository;

    public ManagementUnitResource(ManagementUnitService managementUnitService, ManagementUnitRepository managementUnitRepository) {
        this.managementUnitService = managementUnitService;
        this.managementUnitRepository = managementUnitRepository;
    }

    /**
     * {@code POST  /management-units} : Create a new managementUnit.
     *
     * @param managementUnitDTO the managementUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new managementUnitDTO, or with status {@code 400 (Bad Request)} if the managementUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/management-units")
    public ResponseEntity<ManagementUnitDTO> createManagementUnit(@Valid @RequestBody ManagementUnitDTO managementUnitDTO)
        throws URISyntaxException {
        log.debug("REST request to save ManagementUnit : {}", managementUnitDTO);
        if (managementUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new managementUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManagementUnitDTO result = managementUnitService.save(managementUnitDTO);
        return ResponseEntity
            .created(new URI("/api/management-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /management-units/:id} : Updates an existing managementUnit.
     *
     * @param id the id of the managementUnitDTO to save.
     * @param managementUnitDTO the managementUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementUnitDTO,
     * or with status {@code 400 (Bad Request)} if the managementUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the managementUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/management-units/{id}")
    public ResponseEntity<ManagementUnitDTO> updateManagementUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ManagementUnitDTO managementUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ManagementUnit : {}, {}", id, managementUnitDTO);
        if (managementUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManagementUnitDTO result = managementUnitService.update(managementUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, managementUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /management-units/:id} : Partial updates given fields of an existing managementUnit, field will ignore if it is null
     *
     * @param id the id of the managementUnitDTO to save.
     * @param managementUnitDTO the managementUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementUnitDTO,
     * or with status {@code 400 (Bad Request)} if the managementUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the managementUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the managementUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/management-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManagementUnitDTO> partialUpdateManagementUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ManagementUnitDTO managementUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ManagementUnit partially : {}, {}", id, managementUnitDTO);
        if (managementUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManagementUnitDTO> result = managementUnitService.partialUpdate(managementUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, managementUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /management-units} : get all the managementUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managementUnits in body.
     */
    @GetMapping("/management-units")
    public List<ManagementUnitDTO> getAllManagementUnits() {
        log.debug("REST request to get all ManagementUnits");
        return managementUnitService.findAll();
    }

    /**
     * {@code GET  /management-units/:id} : get the "id" managementUnit.
     *
     * @param id the id of the managementUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the managementUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/management-units/{id}")
    public ResponseEntity<ManagementUnitDTO> getManagementUnit(@PathVariable Long id) {
        log.debug("REST request to get ManagementUnit : {}", id);
        Optional<ManagementUnitDTO> managementUnitDTO = managementUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(managementUnitDTO);
    }

    /**
     * {@code DELETE  /management-units/:id} : delete the "id" managementUnit.
     *
     * @param id the id of the managementUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/management-units/{id}")
    public ResponseEntity<Void> deleteManagementUnit(@PathVariable Long id) {
        log.debug("REST request to delete ManagementUnit : {}", id);
        managementUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

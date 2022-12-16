package com.pae.well.web.rest;

import com.pae.well.repository.WellStatusRepository;
import com.pae.well.service.WellStatusService;
import com.pae.well.service.dto.WellStatusDTO;
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
 * REST controller for managing {@link com.pae.well.domain.WellStatus}.
 */
@RestController
@RequestMapping("/api")
public class WellStatusResource {

    private final Logger log = LoggerFactory.getLogger(WellStatusResource.class);

    private static final String ENTITY_NAME = "wellWellStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WellStatusService wellStatusService;

    private final WellStatusRepository wellStatusRepository;

    public WellStatusResource(WellStatusService wellStatusService, WellStatusRepository wellStatusRepository) {
        this.wellStatusService = wellStatusService;
        this.wellStatusRepository = wellStatusRepository;
    }

    /**
     * {@code POST  /well-statuses} : Create a new wellStatus.
     *
     * @param wellStatusDTO the wellStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wellStatusDTO, or with status {@code 400 (Bad Request)} if the wellStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/well-statuses")
    public ResponseEntity<WellStatusDTO> createWellStatus(@Valid @RequestBody WellStatusDTO wellStatusDTO) throws URISyntaxException {
        log.debug("REST request to save WellStatus : {}", wellStatusDTO);
        if (wellStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new wellStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WellStatusDTO result = wellStatusService.save(wellStatusDTO);
        return ResponseEntity
            .created(new URI("/api/well-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /well-statuses/:id} : Updates an existing wellStatus.
     *
     * @param id the id of the wellStatusDTO to save.
     * @param wellStatusDTO the wellStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wellStatusDTO,
     * or with status {@code 400 (Bad Request)} if the wellStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wellStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/well-statuses/{id}")
    public ResponseEntity<WellStatusDTO> updateWellStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WellStatusDTO wellStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WellStatus : {}, {}", id, wellStatusDTO);
        if (wellStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wellStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wellStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WellStatusDTO result = wellStatusService.update(wellStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wellStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /well-statuses/:id} : Partial updates given fields of an existing wellStatus, field will ignore if it is null
     *
     * @param id the id of the wellStatusDTO to save.
     * @param wellStatusDTO the wellStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wellStatusDTO,
     * or with status {@code 400 (Bad Request)} if the wellStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wellStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wellStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/well-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WellStatusDTO> partialUpdateWellStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WellStatusDTO wellStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WellStatus partially : {}, {}", id, wellStatusDTO);
        if (wellStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wellStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wellStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WellStatusDTO> result = wellStatusService.partialUpdate(wellStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wellStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /well-statuses} : get all the wellStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wellStatuses in body.
     */
    @GetMapping("/well-statuses")
    public List<WellStatusDTO> getAllWellStatuses() {
        log.debug("REST request to get all WellStatuses");
        return wellStatusService.findAll();
    }

    /**
     * {@code GET  /well-statuses/:id} : get the "id" wellStatus.
     *
     * @param id the id of the wellStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wellStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/well-statuses/{id}")
    public ResponseEntity<WellStatusDTO> getWellStatus(@PathVariable Long id) {
        log.debug("REST request to get WellStatus : {}", id);
        Optional<WellStatusDTO> wellStatusDTO = wellStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wellStatusDTO);
    }

    /**
     * {@code DELETE  /well-statuses/:id} : delete the "id" wellStatus.
     *
     * @param id the id of the wellStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/well-statuses/{id}")
    public ResponseEntity<Void> deleteWellStatus(@PathVariable Long id) {
        log.debug("REST request to delete WellStatus : {}", id);
        wellStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

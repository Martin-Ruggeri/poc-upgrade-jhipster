package com.pae.well.web.rest;

import com.pae.well.repository.AdditionalDataRepository;
import com.pae.well.service.AdditionalDataService;
import com.pae.well.service.dto.AdditionalDataDTO;
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
 * REST controller for managing {@link com.pae.well.domain.AdditionalData}.
 */
@RestController
@RequestMapping("/api")
public class AdditionalDataResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalDataResource.class);

    private static final String ENTITY_NAME = "wellAdditionalData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalDataService additionalDataService;

    private final AdditionalDataRepository additionalDataRepository;

    public AdditionalDataResource(AdditionalDataService additionalDataService, AdditionalDataRepository additionalDataRepository) {
        this.additionalDataService = additionalDataService;
        this.additionalDataRepository = additionalDataRepository;
    }

    /**
     * {@code POST  /additional-data} : Create a new additionalData.
     *
     * @param additionalDataDTO the additionalDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalDataDTO, or with status {@code 400 (Bad Request)} if the additionalData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/additional-data")
    public ResponseEntity<AdditionalDataDTO> createAdditionalData(@Valid @RequestBody AdditionalDataDTO additionalDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdditionalData : {}", additionalDataDTO);
        if (additionalDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdditionalDataDTO result = additionalDataService.save(additionalDataDTO);
        return ResponseEntity
            .created(new URI("/api/additional-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /additional-data/:id} : Updates an existing additionalData.
     *
     * @param id the id of the additionalDataDTO to save.
     * @param additionalDataDTO the additionalDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalDataDTO,
     * or with status {@code 400 (Bad Request)} if the additionalDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/additional-data/{id}")
    public ResponseEntity<AdditionalDataDTO> updateAdditionalData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalDataDTO additionalDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdditionalData : {}, {}", id, additionalDataDTO);
        if (additionalDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdditionalDataDTO result = additionalDataService.update(additionalDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /additional-data/:id} : Partial updates given fields of an existing additionalData, field will ignore if it is null
     *
     * @param id the id of the additionalDataDTO to save.
     * @param additionalDataDTO the additionalDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalDataDTO,
     * or with status {@code 400 (Bad Request)} if the additionalDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the additionalDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/additional-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalDataDTO> partialUpdateAdditionalData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalDataDTO additionalDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdditionalData partially : {}, {}", id, additionalDataDTO);
        if (additionalDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalDataDTO> result = additionalDataService.partialUpdate(additionalDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-data} : get all the additionalData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalData in body.
     */
    @GetMapping("/additional-data")
    public List<AdditionalDataDTO> getAllAdditionalData() {
        log.debug("REST request to get all AdditionalData");
        return additionalDataService.findAll();
    }

    /**
     * {@code GET  /additional-data/:id} : get the "id" additionalData.
     *
     * @param id the id of the additionalDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/additional-data/{id}")
    public ResponseEntity<AdditionalDataDTO> getAdditionalData(@PathVariable Long id) {
        log.debug("REST request to get AdditionalData : {}", id);
        Optional<AdditionalDataDTO> additionalDataDTO = additionalDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalDataDTO);
    }

    /**
     * {@code DELETE  /additional-data/:id} : delete the "id" additionalData.
     *
     * @param id the id of the additionalDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/additional-data/{id}")
    public ResponseEntity<Void> deleteAdditionalData(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalData : {}", id);
        additionalDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

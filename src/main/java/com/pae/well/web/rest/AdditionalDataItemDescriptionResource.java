package com.pae.well.web.rest;

import com.pae.well.repository.AdditionalDataItemDescriptionRepository;
import com.pae.well.service.AdditionalDataItemDescriptionService;
import com.pae.well.service.dto.AdditionalDataItemDescriptionDTO;
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
 * REST controller for managing {@link com.pae.well.domain.AdditionalDataItemDescription}.
 */
@RestController
@RequestMapping("/api")
public class AdditionalDataItemDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalDataItemDescriptionResource.class);

    private static final String ENTITY_NAME = "wellAdditionalDataItemDescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalDataItemDescriptionService additionalDataItemDescriptionService;

    private final AdditionalDataItemDescriptionRepository additionalDataItemDescriptionRepository;

    public AdditionalDataItemDescriptionResource(
        AdditionalDataItemDescriptionService additionalDataItemDescriptionService,
        AdditionalDataItemDescriptionRepository additionalDataItemDescriptionRepository
    ) {
        this.additionalDataItemDescriptionService = additionalDataItemDescriptionService;
        this.additionalDataItemDescriptionRepository = additionalDataItemDescriptionRepository;
    }

    /**
     * {@code POST  /additional-data-item-descriptions} : Create a new additionalDataItemDescription.
     *
     * @param additionalDataItemDescriptionDTO the additionalDataItemDescriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalDataItemDescriptionDTO, or with status {@code 400 (Bad Request)} if the additionalDataItemDescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/additional-data-item-descriptions")
    public ResponseEntity<AdditionalDataItemDescriptionDTO> createAdditionalDataItemDescription(
        @Valid @RequestBody AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AdditionalDataItemDescription : {}", additionalDataItemDescriptionDTO);
        if (additionalDataItemDescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalDataItemDescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdditionalDataItemDescriptionDTO result = additionalDataItemDescriptionService.save(additionalDataItemDescriptionDTO);
        return ResponseEntity
            .created(new URI("/api/additional-data-item-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /additional-data-item-descriptions/:id} : Updates an existing additionalDataItemDescription.
     *
     * @param id the id of the additionalDataItemDescriptionDTO to save.
     * @param additionalDataItemDescriptionDTO the additionalDataItemDescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalDataItemDescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the additionalDataItemDescriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalDataItemDescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/additional-data-item-descriptions/{id}")
    public ResponseEntity<AdditionalDataItemDescriptionDTO> updateAdditionalDataItemDescription(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdditionalDataItemDescription : {}, {}", id, additionalDataItemDescriptionDTO);
        if (additionalDataItemDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalDataItemDescriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalDataItemDescriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdditionalDataItemDescriptionDTO result = additionalDataItemDescriptionService.update(additionalDataItemDescriptionDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalDataItemDescriptionDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /additional-data-item-descriptions/:id} : Partial updates given fields of an existing additionalDataItemDescription, field will ignore if it is null
     *
     * @param id the id of the additionalDataItemDescriptionDTO to save.
     * @param additionalDataItemDescriptionDTO the additionalDataItemDescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalDataItemDescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the additionalDataItemDescriptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the additionalDataItemDescriptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalDataItemDescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/additional-data-item-descriptions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalDataItemDescriptionDTO> partialUpdateAdditionalDataItemDescription(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdditionalDataItemDescription partially : {}, {}", id, additionalDataItemDescriptionDTO);
        if (additionalDataItemDescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalDataItemDescriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalDataItemDescriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalDataItemDescriptionDTO> result = additionalDataItemDescriptionService.partialUpdate(
            additionalDataItemDescriptionDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalDataItemDescriptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-data-item-descriptions} : get all the additionalDataItemDescriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalDataItemDescriptions in body.
     */
    @GetMapping("/additional-data-item-descriptions")
    public List<AdditionalDataItemDescriptionDTO> getAllAdditionalDataItemDescriptions() {
        log.debug("REST request to get all AdditionalDataItemDescriptions");
        return additionalDataItemDescriptionService.findAll();
    }

    /**
     * {@code GET  /additional-data-item-descriptions/:id} : get the "id" additionalDataItemDescription.
     *
     * @param id the id of the additionalDataItemDescriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalDataItemDescriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/additional-data-item-descriptions/{id}")
    public ResponseEntity<AdditionalDataItemDescriptionDTO> getAdditionalDataItemDescription(@PathVariable Long id) {
        log.debug("REST request to get AdditionalDataItemDescription : {}", id);
        Optional<AdditionalDataItemDescriptionDTO> additionalDataItemDescriptionDTO = additionalDataItemDescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalDataItemDescriptionDTO);
    }

    /**
     * {@code DELETE  /additional-data-item-descriptions/:id} : delete the "id" additionalDataItemDescription.
     *
     * @param id the id of the additionalDataItemDescriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/additional-data-item-descriptions/{id}")
    public ResponseEntity<Void> deleteAdditionalDataItemDescription(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalDataItemDescription : {}", id);
        additionalDataItemDescriptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.pae.well.web.rest;

import com.pae.well.repository.AdditionalDataItemRepository;
import com.pae.well.service.AdditionalDataItemService;
import com.pae.well.service.dto.AdditionalDataItemDTO;
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
 * REST controller for managing {@link com.pae.well.domain.AdditionalDataItem}.
 */
@RestController
@RequestMapping("/api")
public class AdditionalDataItemResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalDataItemResource.class);

    private static final String ENTITY_NAME = "wellAdditionalDataItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalDataItemService additionalDataItemService;

    private final AdditionalDataItemRepository additionalDataItemRepository;

    public AdditionalDataItemResource(
        AdditionalDataItemService additionalDataItemService,
        AdditionalDataItemRepository additionalDataItemRepository
    ) {
        this.additionalDataItemService = additionalDataItemService;
        this.additionalDataItemRepository = additionalDataItemRepository;
    }

    /**
     * {@code POST  /additional-data-items} : Create a new additionalDataItem.
     *
     * @param additionalDataItemDTO the additionalDataItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalDataItemDTO, or with status {@code 400 (Bad Request)} if the additionalDataItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/additional-data-items")
    public ResponseEntity<AdditionalDataItemDTO> createAdditionalDataItem(@Valid @RequestBody AdditionalDataItemDTO additionalDataItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdditionalDataItem : {}", additionalDataItemDTO);
        if (additionalDataItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalDataItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdditionalDataItemDTO result = additionalDataItemService.save(additionalDataItemDTO);
        return ResponseEntity
            .created(new URI("/api/additional-data-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /additional-data-items/:id} : Updates an existing additionalDataItem.
     *
     * @param id the id of the additionalDataItemDTO to save.
     * @param additionalDataItemDTO the additionalDataItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalDataItemDTO,
     * or with status {@code 400 (Bad Request)} if the additionalDataItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalDataItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/additional-data-items/{id}")
    public ResponseEntity<AdditionalDataItemDTO> updateAdditionalDataItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdditionalDataItemDTO additionalDataItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdditionalDataItem : {}, {}", id, additionalDataItemDTO);
        if (additionalDataItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalDataItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalDataItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdditionalDataItemDTO result = additionalDataItemService.update(additionalDataItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalDataItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /additional-data-items/:id} : Partial updates given fields of an existing additionalDataItem, field will ignore if it is null
     *
     * @param id the id of the additionalDataItemDTO to save.
     * @param additionalDataItemDTO the additionalDataItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalDataItemDTO,
     * or with status {@code 400 (Bad Request)} if the additionalDataItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the additionalDataItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the additionalDataItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/additional-data-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdditionalDataItemDTO> partialUpdateAdditionalDataItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdditionalDataItemDTO additionalDataItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdditionalDataItem partially : {}, {}", id, additionalDataItemDTO);
        if (additionalDataItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, additionalDataItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!additionalDataItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdditionalDataItemDTO> result = additionalDataItemService.partialUpdate(additionalDataItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalDataItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /additional-data-items} : get all the additionalDataItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalDataItems in body.
     */
    @GetMapping("/additional-data-items")
    public List<AdditionalDataItemDTO> getAllAdditionalDataItems() {
        log.debug("REST request to get all AdditionalDataItems");
        return additionalDataItemService.findAll();
    }

    /**
     * {@code GET  /additional-data-items/:id} : get the "id" additionalDataItem.
     *
     * @param id the id of the additionalDataItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalDataItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/additional-data-items/{id}")
    public ResponseEntity<AdditionalDataItemDTO> getAdditionalDataItem(@PathVariable Long id) {
        log.debug("REST request to get AdditionalDataItem : {}", id);
        Optional<AdditionalDataItemDTO> additionalDataItemDTO = additionalDataItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalDataItemDTO);
    }

    /**
     * {@code DELETE  /additional-data-items/:id} : delete the "id" additionalDataItem.
     *
     * @param id the id of the additionalDataItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/additional-data-items/{id}")
    public ResponseEntity<Void> deleteAdditionalDataItem(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalDataItem : {}", id);
        additionalDataItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.pae.well.web.rest;

import com.pae.well.repository.WellChangeExtractionMethodRepository;
import com.pae.well.service.WellChangeExtractionMethodService;
import com.pae.well.service.dto.WellChangeExtractionMethodDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pae.well.domain.WellChangeExtractionMethod}.
 */
@RestController
@RequestMapping("/api")
public class WellChangeExtractionMethodResource {

    private final Logger log = LoggerFactory.getLogger(WellChangeExtractionMethodResource.class);

    private static final String ENTITY_NAME = "wellWellChangeExtractionMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WellChangeExtractionMethodService wellChangeExtractionMethodService;

    private final WellChangeExtractionMethodRepository wellChangeExtractionMethodRepository;

    public WellChangeExtractionMethodResource(
        WellChangeExtractionMethodService wellChangeExtractionMethodService,
        WellChangeExtractionMethodRepository wellChangeExtractionMethodRepository
    ) {
        this.wellChangeExtractionMethodService = wellChangeExtractionMethodService;
        this.wellChangeExtractionMethodRepository = wellChangeExtractionMethodRepository;
    }

    /**
     * {@code POST  /well-change-extraction-methods} : Create a new wellChangeExtractionMethod.
     *
     * @param wellChangeExtractionMethodDTO the wellChangeExtractionMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wellChangeExtractionMethodDTO, or with status {@code 400 (Bad Request)} if the wellChangeExtractionMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/well-change-extraction-methods")
    public ResponseEntity<WellChangeExtractionMethodDTO> createWellChangeExtractionMethod(
        @Valid @RequestBody WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to save WellChangeExtractionMethod : {}", wellChangeExtractionMethodDTO);
        if (wellChangeExtractionMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new wellChangeExtractionMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WellChangeExtractionMethodDTO result = wellChangeExtractionMethodService.save(wellChangeExtractionMethodDTO);
        return ResponseEntity
            .created(new URI("/api/well-change-extraction-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /well-change-extraction-methods/:id} : Updates an existing wellChangeExtractionMethod.
     *
     * @param id the id of the wellChangeExtractionMethodDTO to save.
     * @param wellChangeExtractionMethodDTO the wellChangeExtractionMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wellChangeExtractionMethodDTO,
     * or with status {@code 400 (Bad Request)} if the wellChangeExtractionMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wellChangeExtractionMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/well-change-extraction-methods/{id}")
    public ResponseEntity<WellChangeExtractionMethodDTO> updateWellChangeExtractionMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WellChangeExtractionMethod : {}, {}", id, wellChangeExtractionMethodDTO);
        if (wellChangeExtractionMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wellChangeExtractionMethodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wellChangeExtractionMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WellChangeExtractionMethodDTO result = wellChangeExtractionMethodService.update(wellChangeExtractionMethodDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wellChangeExtractionMethodDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /well-change-extraction-methods/:id} : Partial updates given fields of an existing wellChangeExtractionMethod, field will ignore if it is null
     *
     * @param id the id of the wellChangeExtractionMethodDTO to save.
     * @param wellChangeExtractionMethodDTO the wellChangeExtractionMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wellChangeExtractionMethodDTO,
     * or with status {@code 400 (Bad Request)} if the wellChangeExtractionMethodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wellChangeExtractionMethodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wellChangeExtractionMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/well-change-extraction-methods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WellChangeExtractionMethodDTO> partialUpdateWellChangeExtractionMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WellChangeExtractionMethod partially : {}, {}", id, wellChangeExtractionMethodDTO);
        if (wellChangeExtractionMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wellChangeExtractionMethodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wellChangeExtractionMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WellChangeExtractionMethodDTO> result = wellChangeExtractionMethodService.partialUpdate(wellChangeExtractionMethodDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wellChangeExtractionMethodDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /well-change-extraction-methods} : get all the wellChangeExtractionMethods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wellChangeExtractionMethods in body.
     */
    @GetMapping("/well-change-extraction-methods")
    public ResponseEntity<List<WellChangeExtractionMethodDTO>> getAllWellChangeExtractionMethods(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of WellChangeExtractionMethods");
        Page<WellChangeExtractionMethodDTO> page = wellChangeExtractionMethodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /well-change-extraction-methods/:id} : get the "id" wellChangeExtractionMethod.
     *
     * @param id the id of the wellChangeExtractionMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wellChangeExtractionMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/well-change-extraction-methods/{id}")
    public ResponseEntity<WellChangeExtractionMethodDTO> getWellChangeExtractionMethod(@PathVariable Long id) {
        log.debug("REST request to get WellChangeExtractionMethod : {}", id);
        Optional<WellChangeExtractionMethodDTO> wellChangeExtractionMethodDTO = wellChangeExtractionMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wellChangeExtractionMethodDTO);
    }

    /**
     * {@code DELETE  /well-change-extraction-methods/:id} : delete the "id" wellChangeExtractionMethod.
     *
     * @param id the id of the wellChangeExtractionMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/well-change-extraction-methods/{id}")
    public ResponseEntity<Void> deleteWellChangeExtractionMethod(@PathVariable Long id) {
        log.debug("REST request to delete WellChangeExtractionMethod : {}", id);
        wellChangeExtractionMethodService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

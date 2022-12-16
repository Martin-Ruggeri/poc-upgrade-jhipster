package com.pae.well.web.rest;

import com.pae.well.repository.ExtractionMethodRepository;
import com.pae.well.service.ExtractionMethodService;
import com.pae.well.service.dto.ExtractionMethodDTO;
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
 * REST controller for managing {@link com.pae.well.domain.ExtractionMethod}.
 */
@RestController
@RequestMapping("/api")
public class ExtractionMethodResource {

    private final Logger log = LoggerFactory.getLogger(ExtractionMethodResource.class);

    private static final String ENTITY_NAME = "wellExtractionMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtractionMethodService extractionMethodService;

    private final ExtractionMethodRepository extractionMethodRepository;

    public ExtractionMethodResource(
        ExtractionMethodService extractionMethodService,
        ExtractionMethodRepository extractionMethodRepository
    ) {
        this.extractionMethodService = extractionMethodService;
        this.extractionMethodRepository = extractionMethodRepository;
    }

    /**
     * {@code POST  /extraction-methods} : Create a new extractionMethod.
     *
     * @param extractionMethodDTO the extractionMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extractionMethodDTO, or with status {@code 400 (Bad Request)} if the extractionMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extraction-methods")
    public ResponseEntity<ExtractionMethodDTO> createExtractionMethod(@Valid @RequestBody ExtractionMethodDTO extractionMethodDTO)
        throws URISyntaxException {
        log.debug("REST request to save ExtractionMethod : {}", extractionMethodDTO);
        if (extractionMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new extractionMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtractionMethodDTO result = extractionMethodService.save(extractionMethodDTO);
        return ResponseEntity
            .created(new URI("/api/extraction-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extraction-methods/:id} : Updates an existing extractionMethod.
     *
     * @param id the id of the extractionMethodDTO to save.
     * @param extractionMethodDTO the extractionMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extractionMethodDTO,
     * or with status {@code 400 (Bad Request)} if the extractionMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extractionMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extraction-methods/{id}")
    public ResponseEntity<ExtractionMethodDTO> updateExtractionMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExtractionMethodDTO extractionMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExtractionMethod : {}, {}", id, extractionMethodDTO);
        if (extractionMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extractionMethodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!extractionMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExtractionMethodDTO result = extractionMethodService.update(extractionMethodDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extractionMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /extraction-methods/:id} : Partial updates given fields of an existing extractionMethod, field will ignore if it is null
     *
     * @param id the id of the extractionMethodDTO to save.
     * @param extractionMethodDTO the extractionMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extractionMethodDTO,
     * or with status {@code 400 (Bad Request)} if the extractionMethodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the extractionMethodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the extractionMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/extraction-methods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExtractionMethodDTO> partialUpdateExtractionMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExtractionMethodDTO extractionMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExtractionMethod partially : {}, {}", id, extractionMethodDTO);
        if (extractionMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, extractionMethodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!extractionMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExtractionMethodDTO> result = extractionMethodService.partialUpdate(extractionMethodDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extractionMethodDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /extraction-methods} : get all the extractionMethods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extractionMethods in body.
     */
    @GetMapping("/extraction-methods")
    public List<ExtractionMethodDTO> getAllExtractionMethods() {
        log.debug("REST request to get all ExtractionMethods");
        return extractionMethodService.findAll();
    }

    /**
     * {@code GET  /extraction-methods/:id} : get the "id" extractionMethod.
     *
     * @param id the id of the extractionMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extractionMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extraction-methods/{id}")
    public ResponseEntity<ExtractionMethodDTO> getExtractionMethod(@PathVariable Long id) {
        log.debug("REST request to get ExtractionMethod : {}", id);
        Optional<ExtractionMethodDTO> extractionMethodDTO = extractionMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extractionMethodDTO);
    }

    /**
     * {@code DELETE  /extraction-methods/:id} : delete the "id" extractionMethod.
     *
     * @param id the id of the extractionMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extraction-methods/{id}")
    public ResponseEntity<Void> deleteExtractionMethod(@PathVariable Long id) {
        log.debug("REST request to delete ExtractionMethod : {}", id);
        extractionMethodService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

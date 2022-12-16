package com.pae.well.web.rest;

import com.pae.well.repository.WellRepository;
import com.pae.well.service.WellService;
import com.pae.well.service.dto.WellDTO;
import com.pae.well.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * REST controller for managing {@link com.pae.well.domain.Well}.
 */
@RestController
@RequestMapping("/api")
public class WellResource {

    private final Logger log = LoggerFactory.getLogger(WellResource.class);

    private static final String ENTITY_NAME = "wellWell";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WellService wellService;

    private final WellRepository wellRepository;

    public WellResource(WellService wellService, WellRepository wellRepository) {
        this.wellService = wellService;
        this.wellRepository = wellRepository;
    }

    /**
     * {@code POST  /wells} : Create a new well.
     *
     * @param wellDTO the wellDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wellDTO, or with status {@code 400 (Bad Request)} if the well has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wells")
    public ResponseEntity<WellDTO> createWell(@Valid @RequestBody WellDTO wellDTO) throws URISyntaxException {
        log.debug("REST request to save Well : {}", wellDTO);
        if (wellDTO.getId() != null) {
            throw new BadRequestAlertException("A new well cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WellDTO result = wellService.save(wellDTO);
        return ResponseEntity
            .created(new URI("/api/wells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wells/:id} : Updates an existing well.
     *
     * @param id the id of the wellDTO to save.
     * @param wellDTO the wellDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wellDTO,
     * or with status {@code 400 (Bad Request)} if the wellDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wellDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wells/{id}")
    public ResponseEntity<WellDTO> updateWell(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WellDTO wellDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Well : {}, {}", id, wellDTO);
        if (wellDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wellDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wellRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WellDTO result = wellService.update(wellDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wellDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /wells/:id} : Partial updates given fields of an existing well, field will ignore if it is null
     *
     * @param id the id of the wellDTO to save.
     * @param wellDTO the wellDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wellDTO,
     * or with status {@code 400 (Bad Request)} if the wellDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wellDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wellDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wells/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WellDTO> partialUpdateWell(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WellDTO wellDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Well partially : {}, {}", id, wellDTO);
        if (wellDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wellDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wellRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WellDTO> result = wellService.partialUpdate(wellDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wellDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /wells} : get all the wells.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wells in body.
     */
    @GetMapping("/wells")
    public ResponseEntity<List<WellDTO>> getAllWells(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Wells");
        Page<WellDTO> page = wellService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wells/:id} : get the "id" well.
     *
     * @param id the id of the wellDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wellDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wells/{id}")
    public ResponseEntity<WellDTO> getWell(@PathVariable Long id) {
        log.debug("REST request to get Well : {}", id);
        Optional<WellDTO> wellDTO = wellService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wellDTO);
    }

    /**
     * {@code DELETE  /wells/:id} : delete the "id" well.
     *
     * @param id the id of the wellDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wells/{id}")
    public ResponseEntity<Void> deleteWell(@PathVariable Long id) {
        log.debug("REST request to delete Well : {}", id);
        wellService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/wells/date")
    public ResponseEntity<String> getDateWell() {
        log.debug("REST request to get Date");

        return ResponseUtil.wrapOrNotFound(Optional.of(DateTimeFormatter.ISO_INSTANT.format(Instant.now())));
    }
}

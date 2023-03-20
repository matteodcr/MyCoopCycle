package com.mycoopcycle.web.rest;

import com.mycoopcycle.repository.CategorieProduitRepository;
import com.mycoopcycle.service.CategorieProduitService;
import com.mycoopcycle.service.dto.CategorieProduitDTO;
import com.mycoopcycle.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycoopcycle.domain.CategorieProduit}.
 */
@RestController
@RequestMapping("/api")
public class CategorieProduitResource {

    private final Logger log = LoggerFactory.getLogger(CategorieProduitResource.class);

    private static final String ENTITY_NAME = "categorieProduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieProduitService categorieProduitService;

    private final CategorieProduitRepository categorieProduitRepository;

    public CategorieProduitResource(
        CategorieProduitService categorieProduitService,
        CategorieProduitRepository categorieProduitRepository
    ) {
        this.categorieProduitService = categorieProduitService;
        this.categorieProduitRepository = categorieProduitRepository;
    }

    /**
     * {@code POST  /categorie-produits} : Create a new categorieProduit.
     *
     * @param categorieProduitDTO the categorieProduitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieProduitDTO, or with status {@code 400 (Bad Request)} if the categorieProduit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-produits")
    public ResponseEntity<CategorieProduitDTO> createCategorieProduit(@Valid @RequestBody CategorieProduitDTO categorieProduitDTO)
        throws URISyntaxException {
        log.debug("REST request to save CategorieProduit : {}", categorieProduitDTO);
        if (categorieProduitDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieProduit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieProduitDTO result = categorieProduitService.save(categorieProduitDTO);
        return ResponseEntity
            .created(new URI("/api/categorie-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-produits/:id} : Updates an existing categorieProduit.
     *
     * @param id the id of the categorieProduitDTO to save.
     * @param categorieProduitDTO the categorieProduitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieProduitDTO,
     * or with status {@code 400 (Bad Request)} if the categorieProduitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieProduitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-produits/{id}")
    public ResponseEntity<CategorieProduitDTO> updateCategorieProduit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategorieProduitDTO categorieProduitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieProduit : {}, {}", id, categorieProduitDTO);
        if (categorieProduitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieProduitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieProduitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieProduitDTO result = categorieProduitService.update(categorieProduitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieProduitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-produits/:id} : Partial updates given fields of an existing categorieProduit, field will ignore if it is null
     *
     * @param id the id of the categorieProduitDTO to save.
     * @param categorieProduitDTO the categorieProduitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieProduitDTO,
     * or with status {@code 400 (Bad Request)} if the categorieProduitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categorieProduitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieProduitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-produits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategorieProduitDTO> partialUpdateCategorieProduit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategorieProduitDTO categorieProduitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieProduit partially : {}, {}", id, categorieProduitDTO);
        if (categorieProduitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieProduitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieProduitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieProduitDTO> result = categorieProduitService.partialUpdate(categorieProduitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieProduitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-produits} : get all the categorieProduits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieProduits in body.
     */
    @GetMapping("/categorie-produits")
    public List<CategorieProduitDTO> getAllCategorieProduits() {
        log.debug("REST request to get all CategorieProduits");
        return categorieProduitService.findAll();
    }

    /**
     * {@code GET  /categorie-produits/:id} : get the "id" categorieProduit.
     *
     * @param id the id of the categorieProduitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieProduitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-produits/{id}")
    public ResponseEntity<CategorieProduitDTO> getCategorieProduit(@PathVariable Long id) {
        log.debug("REST request to get CategorieProduit : {}", id);
        Optional<CategorieProduitDTO> categorieProduitDTO = categorieProduitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieProduitDTO);
    }

    /**
     * {@code DELETE  /categorie-produits/:id} : delete the "id" categorieProduit.
     *
     * @param id the id of the categorieProduitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-produits/{id}")
    public ResponseEntity<Void> deleteCategorieProduit(@PathVariable Long id) {
        log.debug("REST request to delete CategorieProduit : {}", id);
        categorieProduitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

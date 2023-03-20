package com.mycoopcycle.service;

import com.mycoopcycle.domain.CategorieProduit;
import com.mycoopcycle.repository.CategorieProduitRepository;
import com.mycoopcycle.service.dto.CategorieProduitDTO;
import com.mycoopcycle.service.mapper.CategorieProduitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CategorieProduit}.
 */
@Service
@Transactional
public class CategorieProduitService {

    private final Logger log = LoggerFactory.getLogger(CategorieProduitService.class);

    private final CategorieProduitRepository categorieProduitRepository;

    private final CategorieProduitMapper categorieProduitMapper;

    public CategorieProduitService(CategorieProduitRepository categorieProduitRepository, CategorieProduitMapper categorieProduitMapper) {
        this.categorieProduitRepository = categorieProduitRepository;
        this.categorieProduitMapper = categorieProduitMapper;
    }

    /**
     * Save a categorieProduit.
     *
     * @param categorieProduitDTO the entity to save.
     * @return the persisted entity.
     */
    public CategorieProduitDTO save(CategorieProduitDTO categorieProduitDTO) {
        log.debug("Request to save CategorieProduit : {}", categorieProduitDTO);
        CategorieProduit categorieProduit = categorieProduitMapper.toEntity(categorieProduitDTO);
        categorieProduit = categorieProduitRepository.save(categorieProduit);
        return categorieProduitMapper.toDto(categorieProduit);
    }

    /**
     * Update a categorieProduit.
     *
     * @param categorieProduitDTO the entity to save.
     * @return the persisted entity.
     */
    public CategorieProduitDTO update(CategorieProduitDTO categorieProduitDTO) {
        log.debug("Request to update CategorieProduit : {}", categorieProduitDTO);
        CategorieProduit categorieProduit = categorieProduitMapper.toEntity(categorieProduitDTO);
        categorieProduit = categorieProduitRepository.save(categorieProduit);
        return categorieProduitMapper.toDto(categorieProduit);
    }

    /**
     * Partially update a categorieProduit.
     *
     * @param categorieProduitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategorieProduitDTO> partialUpdate(CategorieProduitDTO categorieProduitDTO) {
        log.debug("Request to partially update CategorieProduit : {}", categorieProduitDTO);

        return categorieProduitRepository
            .findById(categorieProduitDTO.getId())
            .map(existingCategorieProduit -> {
                categorieProduitMapper.partialUpdate(existingCategorieProduit, categorieProduitDTO);

                return existingCategorieProduit;
            })
            .map(categorieProduitRepository::save)
            .map(categorieProduitMapper::toDto);
    }

    /**
     * Get all the categorieProduits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieProduitDTO> findAll() {
        log.debug("Request to get all CategorieProduits");
        return categorieProduitRepository
            .findAll()
            .stream()
            .map(categorieProduitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categorieProduit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategorieProduitDTO> findOne(Long id) {
        log.debug("Request to get CategorieProduit : {}", id);
        return categorieProduitRepository.findById(id).map(categorieProduitMapper::toDto);
    }

    /**
     * Delete the categorieProduit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategorieProduit : {}", id);
        categorieProduitRepository.deleteById(id);
    }
}

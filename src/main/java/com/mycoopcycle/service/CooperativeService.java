package com.mycoopcycle.service;

import com.mycoopcycle.domain.Cooperative;
import com.mycoopcycle.repository.CooperativeRepository;
import com.mycoopcycle.service.dto.CooperativeDTO;
import com.mycoopcycle.service.mapper.CooperativeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cooperative}.
 */
@Service
@Transactional
public class CooperativeService {

    private final Logger log = LoggerFactory.getLogger(CooperativeService.class);

    private final CooperativeRepository cooperativeRepository;

    private final CooperativeMapper cooperativeMapper;

    public CooperativeService(CooperativeRepository cooperativeRepository, CooperativeMapper cooperativeMapper) {
        this.cooperativeRepository = cooperativeRepository;
        this.cooperativeMapper = cooperativeMapper;
    }

    /**
     * Save a cooperative.
     *
     * @param cooperativeDTO the entity to save.
     * @return the persisted entity.
     */
    public CooperativeDTO save(CooperativeDTO cooperativeDTO) {
        log.debug("Request to save Cooperative : {}", cooperativeDTO);
        Cooperative cooperative = cooperativeMapper.toEntity(cooperativeDTO);
        cooperative = cooperativeRepository.save(cooperative);
        return cooperativeMapper.toDto(cooperative);
    }

    /**
     * Update a cooperative.
     *
     * @param cooperativeDTO the entity to save.
     * @return the persisted entity.
     */
    public CooperativeDTO update(CooperativeDTO cooperativeDTO) {
        log.debug("Request to update Cooperative : {}", cooperativeDTO);
        Cooperative cooperative = cooperativeMapper.toEntity(cooperativeDTO);
        cooperative = cooperativeRepository.save(cooperative);
        return cooperativeMapper.toDto(cooperative);
    }

    /**
     * Partially update a cooperative.
     *
     * @param cooperativeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CooperativeDTO> partialUpdate(CooperativeDTO cooperativeDTO) {
        log.debug("Request to partially update Cooperative : {}", cooperativeDTO);

        return cooperativeRepository
            .findById(cooperativeDTO.getId())
            .map(existingCooperative -> {
                cooperativeMapper.partialUpdate(existingCooperative, cooperativeDTO);

                return existingCooperative;
            })
            .map(cooperativeRepository::save)
            .map(cooperativeMapper::toDto);
    }

    /**
     * Get all the cooperatives.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CooperativeDTO> findAll() {
        log.debug("Request to get all Cooperatives");
        return cooperativeRepository.findAll().stream().map(cooperativeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the cooperatives with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CooperativeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return cooperativeRepository.findAllWithEagerRelationships(pageable).map(cooperativeMapper::toDto);
    }

    /**
     * Get one cooperative by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CooperativeDTO> findOne(Long id) {
        log.debug("Request to get Cooperative : {}", id);
        return cooperativeRepository.findOneWithEagerRelationships(id).map(cooperativeMapper::toDto);
    }

    /**
     * Delete the cooperative by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cooperative : {}", id);
        cooperativeRepository.deleteById(id);
    }
}

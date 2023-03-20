package com.mycoopcycle.service;

import com.mycoopcycle.domain.Commercant;
import com.mycoopcycle.repository.CommercantRepository;
import com.mycoopcycle.service.dto.CommercantDTO;
import com.mycoopcycle.service.mapper.CommercantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commercant}.
 */
@Service
@Transactional
public class CommercantService {

    private final Logger log = LoggerFactory.getLogger(CommercantService.class);

    private final CommercantRepository commercantRepository;

    private final CommercantMapper commercantMapper;

    public CommercantService(CommercantRepository commercantRepository, CommercantMapper commercantMapper) {
        this.commercantRepository = commercantRepository;
        this.commercantMapper = commercantMapper;
    }

    /**
     * Save a commercant.
     *
     * @param commercantDTO the entity to save.
     * @return the persisted entity.
     */
    public CommercantDTO save(CommercantDTO commercantDTO) {
        log.debug("Request to save Commercant : {}", commercantDTO);
        Commercant commercant = commercantMapper.toEntity(commercantDTO);
        commercant = commercantRepository.save(commercant);
        return commercantMapper.toDto(commercant);
    }

    /**
     * Update a commercant.
     *
     * @param commercantDTO the entity to save.
     * @return the persisted entity.
     */
    public CommercantDTO update(CommercantDTO commercantDTO) {
        log.debug("Request to update Commercant : {}", commercantDTO);
        Commercant commercant = commercantMapper.toEntity(commercantDTO);
        commercant = commercantRepository.save(commercant);
        return commercantMapper.toDto(commercant);
    }

    /**
     * Partially update a commercant.
     *
     * @param commercantDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommercantDTO> partialUpdate(CommercantDTO commercantDTO) {
        log.debug("Request to partially update Commercant : {}", commercantDTO);

        return commercantRepository
            .findById(commercantDTO.getId())
            .map(existingCommercant -> {
                commercantMapper.partialUpdate(existingCommercant, commercantDTO);

                return existingCommercant;
            })
            .map(commercantRepository::save)
            .map(commercantMapper::toDto);
    }

    /**
     * Get all the commercants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CommercantDTO> findAll() {
        log.debug("Request to get all Commercants");
        return commercantRepository.findAll().stream().map(commercantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one commercant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommercantDTO> findOne(Long id) {
        log.debug("Request to get Commercant : {}", id);
        return commercantRepository.findById(id).map(commercantMapper::toDto);
    }

    /**
     * Delete the commercant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Commercant : {}", id);
        commercantRepository.deleteById(id);
    }
}

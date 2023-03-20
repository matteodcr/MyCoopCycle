package com.mycoopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycoopcycle.IntegrationTest;
import com.mycoopcycle.domain.Commercant;
import com.mycoopcycle.repository.CommercantRepository;
import com.mycoopcycle.service.dto.CommercantDTO;
import com.mycoopcycle.service.mapper.CommercantMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CommercantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommercantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/commercants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommercantRepository commercantRepository;

    @Autowired
    private CommercantMapper commercantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommercantMockMvc;

    private Commercant commercant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercant createEntity(EntityManager em) {
        Commercant commercant = new Commercant().name(DEFAULT_NAME).location(DEFAULT_LOCATION);
        return commercant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercant createUpdatedEntity(EntityManager em) {
        Commercant commercant = new Commercant().name(UPDATED_NAME).location(UPDATED_LOCATION);
        return commercant;
    }

    @BeforeEach
    public void initTest() {
        commercant = createEntity(em);
    }

    @Test
    @Transactional
    void createCommercant() throws Exception {
        int databaseSizeBeforeCreate = commercantRepository.findAll().size();
        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);
        restCommercantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isCreated());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeCreate + 1);
        Commercant testCommercant = commercantList.get(commercantList.size() - 1);
        assertThat(testCommercant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommercant.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    void createCommercantWithExistingId() throws Exception {
        // Create the Commercant with an existing ID
        commercant.setId(1L);
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        int databaseSizeBeforeCreate = commercantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercantRepository.findAll().size();
        // set the field null
        commercant.setName(null);

        // Create the Commercant, which fails.
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        restCommercantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCommercants() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        // Get all the commercantList
        restCommercantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    void getCommercant() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        // Get the commercant
        restCommercantMockMvc
            .perform(get(ENTITY_API_URL_ID, commercant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commercant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION));
    }

    @Test
    @Transactional
    void getNonExistingCommercant() throws Exception {
        // Get the commercant
        restCommercantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommercant() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();

        // Update the commercant
        Commercant updatedCommercant = commercantRepository.findById(commercant.getId()).get();
        // Disconnect from session so that the updates on updatedCommercant are not directly saved in db
        em.detach(updatedCommercant);
        updatedCommercant.name(UPDATED_NAME).location(UPDATED_LOCATION);
        CommercantDTO commercantDTO = commercantMapper.toDto(updatedCommercant);

        restCommercantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commercantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
        Commercant testCommercant = commercantList.get(commercantList.size() - 1);
        assertThat(testCommercant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommercant.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void putNonExistingCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();
        commercant.setId(count.incrementAndGet());

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commercantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();
        commercant.setId(count.incrementAndGet());

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commercantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();
        commercant.setId(count.incrementAndGet());

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommercantWithPatch() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();

        // Update the commercant using partial update
        Commercant partialUpdatedCommercant = new Commercant();
        partialUpdatedCommercant.setId(commercant.getId());

        partialUpdatedCommercant.name(UPDATED_NAME).location(UPDATED_LOCATION);

        restCommercantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommercant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommercant))
            )
            .andExpect(status().isOk());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
        Commercant testCommercant = commercantList.get(commercantList.size() - 1);
        assertThat(testCommercant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommercant.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void fullUpdateCommercantWithPatch() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();

        // Update the commercant using partial update
        Commercant partialUpdatedCommercant = new Commercant();
        partialUpdatedCommercant.setId(commercant.getId());

        partialUpdatedCommercant.name(UPDATED_NAME).location(UPDATED_LOCATION);

        restCommercantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommercant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommercant))
            )
            .andExpect(status().isOk());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
        Commercant testCommercant = commercantList.get(commercantList.size() - 1);
        assertThat(testCommercant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommercant.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void patchNonExistingCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();
        commercant.setId(count.incrementAndGet());

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commercantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commercantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();
        commercant.setId(count.incrementAndGet());

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commercantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();
        commercant.setId(count.incrementAndGet());

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommercantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commercantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommercant() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        int databaseSizeBeforeDelete = commercantRepository.findAll().size();

        // Delete the commercant
        restCommercantMockMvc
            .perform(delete(ENTITY_API_URL_ID, commercant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

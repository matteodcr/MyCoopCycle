package com.mycoopcycle.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycoopcycle.IntegrationTest;
import com.mycoopcycle.domain.CategorieProduit;
import com.mycoopcycle.repository.CategorieProduitRepository;
import com.mycoopcycle.service.dto.CategorieProduitDTO;
import com.mycoopcycle.service.mapper.CategorieProduitMapper;
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
 * Integration tests for the {@link CategorieProduitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieProduitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorie-produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieProduitRepository categorieProduitRepository;

    @Autowired
    private CategorieProduitMapper categorieProduitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieProduitMockMvc;

    private CategorieProduit categorieProduit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieProduit createEntity(EntityManager em) {
        CategorieProduit categorieProduit = new CategorieProduit().name(DEFAULT_NAME);
        return categorieProduit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieProduit createUpdatedEntity(EntityManager em) {
        CategorieProduit categorieProduit = new CategorieProduit().name(UPDATED_NAME);
        return categorieProduit;
    }

    @BeforeEach
    public void initTest() {
        categorieProduit = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieProduit() throws Exception {
        int databaseSizeBeforeCreate = categorieProduitRepository.findAll().size();
        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);
        restCategorieProduitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieProduit testCategorieProduit = categorieProduitList.get(categorieProduitList.size() - 1);
        assertThat(testCategorieProduit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCategorieProduitWithExistingId() throws Exception {
        // Create the CategorieProduit with an existing ID
        categorieProduit.setId(1L);
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        int databaseSizeBeforeCreate = categorieProduitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieProduitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieProduitRepository.findAll().size();
        // set the field null
        categorieProduit.setName(null);

        // Create the CategorieProduit, which fails.
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        restCategorieProduitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isBadRequest());

        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategorieProduits() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        // Get all the categorieProduitList
        restCategorieProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieProduit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCategorieProduit() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        // Get the categorieProduit
        restCategorieProduitMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieProduit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieProduit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCategorieProduit() throws Exception {
        // Get the categorieProduit
        restCategorieProduitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorieProduit() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();

        // Update the categorieProduit
        CategorieProduit updatedCategorieProduit = categorieProduitRepository.findById(categorieProduit.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieProduit are not directly saved in db
        em.detach(updatedCategorieProduit);
        updatedCategorieProduit.name(UPDATED_NAME);
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(updatedCategorieProduit);

        restCategorieProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieProduitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
        CategorieProduit testCategorieProduit = categorieProduitList.get(categorieProduitList.size() - 1);
        assertThat(testCategorieProduit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();
        categorieProduit.setId(count.incrementAndGet());

        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieProduitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();
        categorieProduit.setId(count.incrementAndGet());

        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();
        categorieProduit.setId(count.incrementAndGet());

        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieProduitWithPatch() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();

        // Update the categorieProduit using partial update
        CategorieProduit partialUpdatedCategorieProduit = new CategorieProduit();
        partialUpdatedCategorieProduit.setId(categorieProduit.getId());

        partialUpdatedCategorieProduit.name(UPDATED_NAME);

        restCategorieProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieProduit))
            )
            .andExpect(status().isOk());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
        CategorieProduit testCategorieProduit = categorieProduitList.get(categorieProduitList.size() - 1);
        assertThat(testCategorieProduit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCategorieProduitWithPatch() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();

        // Update the categorieProduit using partial update
        CategorieProduit partialUpdatedCategorieProduit = new CategorieProduit();
        partialUpdatedCategorieProduit.setId(categorieProduit.getId());

        partialUpdatedCategorieProduit.name(UPDATED_NAME);

        restCategorieProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieProduit))
            )
            .andExpect(status().isOk());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
        CategorieProduit testCategorieProduit = categorieProduitList.get(categorieProduitList.size() - 1);
        assertThat(testCategorieProduit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();
        categorieProduit.setId(count.incrementAndGet());

        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieProduitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();
        categorieProduit.setId(count.incrementAndGet());

        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieProduit() throws Exception {
        int databaseSizeBeforeUpdate = categorieProduitRepository.findAll().size();
        categorieProduit.setId(count.incrementAndGet());

        // Create the CategorieProduit
        CategorieProduitDTO categorieProduitDTO = categorieProduitMapper.toDto(categorieProduit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieProduitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieProduitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieProduit in the database
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieProduit() throws Exception {
        // Initialize the database
        categorieProduitRepository.saveAndFlush(categorieProduit);

        int databaseSizeBeforeDelete = categorieProduitRepository.findAll().size();

        // Delete the categorieProduit
        restCategorieProduitMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieProduit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieProduit> categorieProduitList = categorieProduitRepository.findAll();
        assertThat(categorieProduitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

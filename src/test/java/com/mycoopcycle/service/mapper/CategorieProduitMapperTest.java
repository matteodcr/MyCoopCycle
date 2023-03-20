package com.mycoopcycle.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieProduitMapperTest {

    private CategorieProduitMapper categorieProduitMapper;

    @BeforeEach
    public void setUp() {
        categorieProduitMapper = new CategorieProduitMapperImpl();
    }
}

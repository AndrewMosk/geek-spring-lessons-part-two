package ru.geekbrains.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.model.Brand;
import ru.geekbrains.repo.BrandRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class BrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BrandRepository brandRepository;

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testNewBrand() throws Exception {
        String name = "New brand";
        addNewBrandToDB(name);

        Optional<Brand> actualBrand = getBrandFromDB(name);

        assertTrue(actualBrand.isPresent());
        assertEquals(name, actualBrand.get().getName());
    }
    
    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testDeleteBrand() throws Exception {
        String name = "New brand";
        addNewBrandToDB(name);

        Optional<Brand> actualBrand = getBrandFromDB(name);

        assertTrue(actualBrand.isPresent());
        Long id = actualBrand.get().getId();

        mvc.perform(delete("/brand/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", String.valueOf(id))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brand"));

        Optional<Brand> actualBrandAfterDelete = getBrandFromDB(name);
        assertFalse(actualBrandAfterDelete.isPresent());
    }

    private void addNewBrandToDB(String name) throws Exception {
        mvc.perform(post("/brand")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "-1")
                .param("name", name)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/brand"));
    }

    private Optional<Brand> getBrandFromDB(String name) {
        Brand brand = new Brand();
        brand.setName(name);
        return brandRepository.findOne(Example.of(brand));
    }
}

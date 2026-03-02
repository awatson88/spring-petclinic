/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.system.PetClinicExceptionHandler;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

/**
 * Test class for {@link VisitController}
 *
 * @author Colin But
 * @author Wick Dynex
 * @author Devin AI
 */
@WebMvcTest(VisitController.class)
@Import(PetClinicExceptionHandler.class)
@DisabledInNativeImage
@DisabledInAotMode
class VisitControllerTests {

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_PET_ID = 1;

	private static final int NONEXISTENT_OWNER_ID = 999;

	private static final int NONEXISTENT_PET_ID = 888;

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	@BeforeEach
	void init() {
		Owner owner = new Owner();
		Pet pet = new Pet();
		owner.addPet(pet);
		pet.setId(TEST_PET_ID);
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(Optional.of(owner));
		given(this.owners.findById(NONEXISTENT_OWNER_ID)).willReturn(Optional.empty());
	}

	// --- Happy-path tests ---

	@Test
	void testInitNewVisitForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdateVisitForm"))
			.andExpect(model().attributeExists("visit"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(model().attributeExists("owner"));
	}

	@Test
	void testProcessNewVisitFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
				.param("name", "George")
				.param("description", "Visit Description"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
		verify(this.owners).save(ArgumentMatchers.any(Owner.class));
	}

	// --- Resource-not-found tests ---

	@Nested
	class ResourceNotFound {

		@Test
		void testGetVisitFormForNonexistentOwner() throws Exception {
			mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", NONEXISTENT_OWNER_ID, TEST_PET_ID))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"))
				.andExpect(model().attribute("status", 404));
		}

		@Test
		void testPostVisitFormForNonexistentOwner() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", NONEXISTENT_OWNER_ID, TEST_PET_ID)
					.param("description", "Visit Description"))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"))
				.andExpect(model().attribute("status", 404));
			verify(owners, never()).save(ArgumentMatchers.any(Owner.class));
		}

		@Test
		void testGetVisitFormForNonexistentPet() throws Exception {
			mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, NONEXISTENT_PET_ID))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"))
				.andExpect(model().attribute("status", 404));
		}

		@Test
		void testPostVisitFormForNonexistentPet() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, NONEXISTENT_PET_ID)
					.param("description", "Visit Description"))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"))
				.andExpect(model().attribute("status", 404));
			verify(owners, never()).save(ArgumentMatchers.any(Owner.class));
		}

	}

	// --- Validation failure tests ---

	@Nested
	class FormValidationErrors {

		@Test
		void testProcessNewVisitFormMissingDescription() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID).param("name",
						"George"))
				.andExpect(model().attributeHasErrors("visit"))
				.andExpect(model().attributeHasFieldErrors("visit", "description"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
			verify(owners, never()).save(ArgumentMatchers.any(Owner.class));
		}

		@Test
		void testProcessNewVisitFormEmptyDescription() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", ""))
				.andExpect(model().attributeHasErrors("visit"))
				.andExpect(model().attributeHasFieldErrors("visit", "description"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
			verify(owners, never()).save(ArgumentMatchers.any(Owner.class));
		}

		@Test
		void testProcessNewVisitFormBlankDescription() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", "   \t\n"))
				.andExpect(model().attributeHasErrors("visit"))
				.andExpect(model().attributeHasFieldErrors("visit", "description"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
			verify(owners, never()).save(ArgumentMatchers.any(Owner.class));
		}

	}

	// --- Boundary / edge-input tests ---

	@Nested
	class BoundaryInputs {

		@Test
		void testProcessNewVisitFormWithExtremelyLongDescription() throws Exception {
			String longDescription = "A".repeat(10_000);
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", longDescription))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
		}

		@Test
		void testProcessNewVisitFormWithMinimalDescription() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", "x"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
		}

		@Test
		void testProcessNewVisitFormWithSpecialCharacters() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", "<script>alert('xss')</script> & \"quotes\" 'apos'"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
		}

		@Test
		void testProcessNewVisitFormWithCustomDate() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", "Routine checkup")
					.param("date", "2024-06-15"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/{ownerId}"));
		}

		@Test
		void testProcessNewVisitFormWithInvalidDateFormat() throws Exception {
			mockMvc
				.perform(post("/owners/{ownerId}/pets/{petId}/visits/new", TEST_OWNER_ID, TEST_PET_ID)
					.param("description", "Routine checkup")
					.param("date", "not-a-date"))
				.andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateVisitForm"));
		}

	}

}

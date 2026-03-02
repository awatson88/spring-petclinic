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
package org.springframework.samples.petclinic.system;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global exception handler that converts specific runtime exceptions into appropriate
 * HTTP error responses.
 *
 * @author Devin AI
 */
@ControllerAdvice
public class PetClinicExceptionHandler {

	/**
	 * Handles {@link ResourceNotFoundException} by returning a 404 error view.
	 * @param ex the exception
	 * @return a ModelAndView pointing to the error view with a 404 status
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
		ModelAndView mav = new ModelAndView("error");
		mav.setStatus(HttpStatus.NOT_FOUND);
		mav.addObject("status", 404);
		mav.addObject("message", ex.getMessage());
		return mav;
	}

}

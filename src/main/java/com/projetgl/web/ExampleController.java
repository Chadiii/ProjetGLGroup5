package com.projetgl.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExampleController {

	protected final Log logger = LogFactory.getLog(getClass());

	private String message = "example";

	/**
	 * Une simple page de test (GET)
	 */
	@RequestMapping(value = "/example")
	private ModelAndView hello() {
		return new ModelAndView("hello", "message", message);
	}

}

package edu.trafficsim.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opengis.referencing.operation.TransformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.vividsolutions.jts.io.ParseException;

import edu.trafficsim.engine.NetworkFactory;
import edu.trafficsim.engine.OdFactory;
import edu.trafficsim.engine.SimulationScenario;
import edu.trafficsim.engine.TypesFactory;
import edu.trafficsim.engine.factory.Sequence;
import edu.trafficsim.engine.library.TypesLibrary;
import edu.trafficsim.model.Network;
import edu.trafficsim.model.OdMatrix;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.SimulationProject;
import edu.trafficsim.web.service.ImportScenarioService;
import edu.trafficsim.web.service.SimulationService;

@Controller
@RequestMapping(value = "/scenario")
@SessionAttributes(value = { "sequence", "typesLibrary", "typesFactory",
		"networkFactory", "odFactory", "network", "odMatrix" })
public class ScenarioController extends AbstractController {

	@Autowired
	SimulationProject project;
	@Autowired
	SimulationService simulationService;
	@Autowired
	ImportScenarioService importScenarioService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newScenarioView() {
		return "components/scenario";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportScenario(@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("network") Network network,
			@ModelAttribute("odMatrix") OdMatrix odMatrix,
			HttpServletResponse response) {
		response.setContentType("application/json");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"scenario.json\"");
		try {
			simulationService.exportScenario(project, seq, network, odMatrix,
					response.getOutputStream());
		} catch (IOException e) {
			// TODO
		}
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String importScenarip(@ModelAttribute("sequence") Sequence seq,
			@ModelAttribute("typesLibrary") TypesLibrary library,
			@ModelAttribute("typesFactory") TypesFactory typesFactory,
			@ModelAttribute("networkFactory") NetworkFactory networkFactory,
			@ModelAttribute("odFactory") OdFactory odFactory, Model model,
			HttpServletRequest request, HttpServletResponse response) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		if (!multipartFile.isEmpty()) {
			try (InputStream in = multipartFile.getInputStream()) {
				SimulationScenario scenario = simulationService.importScenario(
						in, typesFactory, networkFactory, odFactory);
				model.addAttribute("sequence", scenario.getSequence());
				model.addAttribute("network", scenario.getNetwork());
				model.addAttribute("odMatrix", scenario.getOdMatrix());

				importScenarioService.updateProject(project, library, scenario);
			} catch (IOException e) {
			} catch (ParseException | ModelInputException e) {
			} catch (TransformException e) {
			}
		}
		return "redirect:/";
	}
}

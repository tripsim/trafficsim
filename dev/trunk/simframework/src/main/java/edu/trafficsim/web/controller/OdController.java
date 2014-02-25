package edu.trafficsim.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.trafficsim.model.Node;
import edu.trafficsim.model.Od;
import edu.trafficsim.model.core.ModelInputException;
import edu.trafficsim.web.UserInterfaceException;
import edu.trafficsim.web.service.entity.OdService;

@Controller
@RequestMapping(value = "/od")
public class OdController extends AbstractController {

	@Autowired
	OdService odService;

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String odView(@PathVariable long id, Model model) {
		Od od = project.getOdMatrix().getOd(id);

		model.addAttribute("od", od);
		return "components/od-info";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String odEdit(
			@PathVariable long id,
			@MatrixVariable(required = false, defaultValue = "false") boolean isNew,
			Model model) {
		Od od = project.getOdMatrix().getOd(id);

		// TODO make it reachable sinks
		model.addAttribute("destinations", project.getNetwork().getSinks());
		model.addAttribute("od", od);
		model.addAttribute("isNew", isNew);
		return "components/od-form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveOd(@RequestParam("id") long id,
			@RequestParam("destinatioid") long did,
			@RequestParam("times[]") double[] times,
			@RequestParam("vphs[]") Integer[] vphs) {
		try {
			odService.updateOd(id, did, times, vphs);
		} catch (ModelInputException e) {
			return failureResponse(e.getMessage());
		}
		return messageOnlySuccessResponse("Od saved.");
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> newOd(@RequestParam("originId") long oid) {
		Node origin = project.getNetwork().getNode(oid);

		try {
			long id = odService.createOd(origin, null).getId();
			return successResponse("New od created.", null, id);
		} catch (ModelInputException | UserInterfaceException e) {
			return failureResponse(e.getMessage());
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> removeOd(@RequestParam("id") long id) {
		odService.removeOd(id);
		return messageOnlySuccessResponse("Od removed.");
	}

}

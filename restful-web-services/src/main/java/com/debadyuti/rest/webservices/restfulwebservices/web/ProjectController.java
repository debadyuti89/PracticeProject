package com.debadyuti.rest.webservices.restfulwebservices.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.debadyuti.rest.webservices.restfulwebservices.domain.Project;
import com.debadyuti.rest.webservices.restfulwebservices.exceptions.ProjectIdException;
import com.debadyuti.rest.webservices.restfulwebservices.services.IProjectService;
import com.debadyuti.rest.webservices.restfulwebservices.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private IProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		Project project1 = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public EntityModel<Project> getProjectById(@PathVariable String projectId) {

		Project project = projectService.findProjectByIdentifier(projectId);

		if(project == null)
			throw new ProjectIdException("id :"+projectId);
		
		EntityModel<Project> model =  EntityModel.of(project);
		WebMvcLinkBuilder linkToProjects =  linkTo(methodOn(this.getClass()).getAllProjects());
		
		model.add(linkToProjects.withRel("all-projects"));
		
		return model;//new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects() {
		return projectService.findAllProjects();
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
		projectService.deleteProjectByIdentifier(projectId);

		return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);
	}
}

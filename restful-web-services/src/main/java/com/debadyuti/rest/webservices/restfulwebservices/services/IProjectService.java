package com.debadyuti.rest.webservices.restfulwebservices.services;

import com.debadyuti.rest.webservices.restfulwebservices.domain.Project;

public interface IProjectService {

	public Project saveOrUpdateProject(Project project);
	public Project findProjectByIdentifier(String projectId);
	public Iterable<Project> findAllProjects();
	public void deleteProjectByIdentifier(String projectid);
}

package it.drwolf.impaqtsbe.controllers;

import it.drwolf.impaqtsbe.dao.QueryDAO;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;

public class QueryController extends Controller {

	private final QueryDAO queryDAO;

	@Inject
	public QueryController(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public Result testSingleWord(String word) throws InterruptedException {
		return Results.ok(Json.toJson(this.queryDAO.testSingleWord(word)));
	}
}

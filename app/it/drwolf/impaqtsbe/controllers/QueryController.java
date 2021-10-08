package it.drwolf.impaqtsbe.controllers;

import java.io.IOException;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import it.drwolf.impaqtsbe.actors.ExternalProcessActor;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.dto.QueryResponse;
import it.drwolf.impaqtsbe.startup.Startup;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.WebSocket;

public class QueryController extends Controller {

	private final ActorSystem actorSystem;
	private final Materializer materializer;
	private final Startup startup;
	private final WrapperCaller wrapperCaller;

	@Inject
	public QueryController(ActorSystem actorSystem, Materializer materializer, Startup startup) {
		this.actorSystem = actorSystem;
		this.materializer = materializer;
		this.startup = startup;
		this.wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
				this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
				this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
				this.startup.getDockerManateePath());
	}

	public Result getMetadatumValues(String corpus, String metadatum) {
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setCorpus(corpus);
		queryRequest.setCorpusMetadatum(metadatum);
		QueryResponse response = null;
		try {
			return Results.ok(this.wrapperCaller.retrieveMetadatumValues(queryRequest));
		} catch (IOException e) {
			return Results.internalServerError(String
					.format("Error retrieving metadatum values for metadatum %s and corpus %s", metadatum, corpus));
		}
	}

	public WebSocket testSingleWordExt() throws InterruptedException {
		return WebSocket.Json.accept(request -> ActorFlow.actorRef(
				out -> ExternalProcessActor.props(out, this.startup.getManateeRegistryPath(),
						this.startup.getManateeLibPath(), this.startup.getJavaExecutable(),
						this.startup.getWrapperPath(), this.startup.getDockerSwitch(),
						this.startup.getDockerManateeRegistry(), this.startup.getDockerManateePath()),
				this.actorSystem, this.materializer));
	}

}

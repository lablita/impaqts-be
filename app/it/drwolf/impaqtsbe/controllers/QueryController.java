package it.drwolf.impaqtsbe.controllers;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import it.drwolf.impaqtsbe.actors.ExternalProcessActor;
import it.drwolf.impaqtsbe.startup.Startup;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.WebSocket;

public class QueryController extends Controller {

	private final ActorSystem actorSystem;
	private final Materializer materializer;
	private final Startup startup;

	@Inject
	public QueryController(ActorSystem actorSystem, Materializer materializer, Startup startup) {
		this.actorSystem = actorSystem;
		this.materializer = materializer;
		this.startup = startup;
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

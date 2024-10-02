/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.drwolf.impaqtsbe.controllers;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.databind.JsonNode;
import it.drwolf.impaqtsbe.actors.ExternalProcessActor;
import it.drwolf.impaqtsbe.dto.QueryRequest;
import it.drwolf.impaqtsbe.security.JWKSSecured;
import it.drwolf.impaqtsbe.startup.Startup;
import it.drwolf.impaqtsbe.utils.WrapperCaller;
import play.libs.F;
import play.libs.streams.ActorFlow;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.WebSocket;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class QueryController extends Controller {

	private final ActorSystem actorSystem;
	private final Materializer materializer;
	private final Startup startup;
	private final WrapperCaller wrapperCaller;
	private final JWKSSecured jwkSecured;

	@Inject
	public QueryController(ActorSystem actorSystem, Materializer materializer, Startup startup,
			JWKSSecured jwksSecured) {
		this.actorSystem = actorSystem;
		this.materializer = materializer;
		this.startup = startup;
		this.jwkSecured = jwksSecured;
		this.wrapperCaller = new WrapperCaller(null, this.startup.getManateeRegistryPath(),
				this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
				this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
				this.startup.getDockerManateePath(), this.startup.getCacheDir());
	}

	private Flow<JsonNode, JsonNode, ?> getActor(final String accessToken) {
		return ActorFlow.actorRef(out -> ExternalProcessActor.props(out, this.startup.getManateeRegistryPath(),
						this.startup.getManateeLibPath(), this.startup.getJavaExecutable(), this.startup.getWrapperPath(),
						this.startup.getDockerSwitch(), this.startup.getDockerManateeRegistry(),
						this.startup.getDockerManateePath(), this.startup.getCacheDir(), this.jwkSecured, accessToken),
				this.actorSystem, this.materializer);
	}

	public Result getMetadatumValues(String corpus, String metadatum) {
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setCorpus(corpus);
		queryRequest.setCorpusMetadatum(metadatum);
		try {
			return Results.ok(this.wrapperCaller.retrieveMetadatumValues(queryRequest));
		} catch (IOException e) {
			return Results.internalServerError(
					String.format("Error retrieving metadatum values for metadatum %s and corpus %s", metadatum,
							corpus));
		}
	}

	public WebSocket getWebSocket(String accessToken) {
		// set websocket if idToken is null or if idToken is valid
		boolean authenticated = false;
		if (accessToken == null) {
			authenticated = true;
		} else {
			String username = this.jwkSecured.getUsername(accessToken).orElse(null);
			authenticated = username != null && !username.isEmpty();
		}
		final Optional<Boolean> authenticatedOpt = Optional.of(authenticated);
		return WebSocket.Json.acceptOrResult(request -> CompletableFuture.completedFuture(authenticatedOpt.map(user -> {
			if (Boolean.TRUE.equals(user)) {
				return F.Either.<Result, Flow<JsonNode, JsonNode, ?>>Right(this.getActor(accessToken));
			}
			return null;
		}).orElseGet(() -> F.Either.Left(Results.forbidden()))));
	}

}

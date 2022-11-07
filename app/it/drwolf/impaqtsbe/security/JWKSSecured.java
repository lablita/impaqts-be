package it.drwolf.impaqtsbe.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.typesafe.config.Config;
import it.drwolf.impaqtsbe.security.exceptions.EMMACorpusSecurityException;
import it.drwolf.impaqtsbe.security.roles.RoleNames;
import play.Logger;
import play.mvc.Security;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

public class JWKSSecured extends Security.Authenticator {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	private static final Logger.ALogger logger = Logger.of(JWKSSecured.class);
	private static String rolesField;
	@SuppressWarnings("rawtypes")
	private final ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
	private final DefaultJWTClaimsVerifier<SecurityContext> jwtClaimsSetVerifier;
	private String issuer;

	@Inject
	public JWKSSecured(Config config) {
		this.setConfig(config);
		this.jwtClaimsSetVerifier = new DefaultJWTClaimsVerifier(null,
				new HashSet<>(Arrays.asList("sub", "iat", "exp", "sid")));
	}

	public static void setRolesField(String rolesField) {
		JWKSSecured.rolesField = rolesField;

	}

	@SuppressWarnings("unchecked")
	private JWTClaimsSet checkToken(String token) throws EMMACorpusSecurityException {
		if (token == null) {
			return null;
		}
		JWTClaimsSet claimsSet = null;
		try {
			claimsSet = this.jwtProcessor.process(token, null);
			this.jwtClaimsSetVerifier.verify(claimsSet, null);
		} catch (BadJWTException badJWTException) {
			JWKSSecured.logger.error("Bad JWT token.");
			return null;
		} catch (ParseException | BadJOSEException | JOSEException e) {
			JWKSSecured.logger.error("JWT Processing error.");
			return null;
		}
		if (claimsSet == null || !this.issuer.equals(claimsSet.getIssuer())) {
			throw new EMMACorpusSecurityException("JWT Token wrong issuer.");
		}
		return claimsSet;
	}

	public String currentUser(String token) {
		try {
			return this.doGetCurrentUser(token);
		} catch (EMMACorpusSecurityException e) {
			JWKSSecured.logger.error("Error checking user authentication/authorization");
		}
		return null;
	}

	private String doGetCurrentUser(String token) throws EMMACorpusSecurityException {
		final JWTClaimsSet claimSet = this.checkToken(token);
		if (claimSet != null) {
			final Object claim = claimSet.getClaim("email");
			if (claim != null) {
				return claim.toString();
			}
		}
		return null;
	}

	private String getTokenFromAuthorizationHeader(String header) {
		if (header.startsWith("Bearer ")) {
			return header.replace("Bearer ", "");
		}
		return null;
	}

	public Optional<String> getUsername(String token) {
		try {
			return Optional.of(this.doGetCurrentUser(token));
		} catch (EMMACorpusSecurityException e) {
			JWKSSecured.logger.error("Error checking user authentication/authorization");
		}
		return Optional.of(null);
	}

	public boolean hasRole(String token, String roleString) {
		JWTClaimsSet claims;
		try {
			claims = this.checkToken(token);
		} catch (EMMACorpusSecurityException e) {
			JWKSSecured.logger.error("Error checking user authorization");
			return false;
		}
		if (claims != null) {
			final Object claim = claims.getClaim(rolesField + roleString);
			if (claim != null) {
				String admin = claim.toString();
				return "true".equals(admin);
			}
		}
		return false;
	}

	public boolean isAdmin(String token) {
		return this.hasRole(token, RoleNames.ADMIN);
	}

	public boolean isAdvancedUser(String token) {
		return this.hasRole(token, RoleNames.ADVANCEDUSER);
	}

	public boolean isUser(String token) {
		return this.hasRole(token, RoleNames.USER);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setConfig(Config config) {
		try {
			this.jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector(JWSAlgorithm.RS256,
					new RemoteJWKSet(new URL(config.getString("auth0.jwks.url")),
							new DefaultResourceRetriever(2000, 2000))));
		} catch (MalformedURLException e) {
			JWKSSecured.logger.error("Malformed auth0.jwks.url");
		}
		this.issuer = config.getString("auth0.issuer");

	}

}
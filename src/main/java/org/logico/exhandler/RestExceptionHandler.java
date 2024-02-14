package org.logico.exhandler;

import io.quarkus.arc.DefaultBean;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.jbosslog.JBossLog;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.logico.dto.error.ApiError;

@ApplicationScoped
@DefaultBean
@JBossLog
public class RestExceptionHandler extends GenericExceptionHandler {

    @ServerExceptionMapper
    public RestResponse<ApiError> mapException(Exception ex, UriInfo uriInfo) {
        log.error("Unhandled exception occurred", ex);
        return this.buildRestResponse(Status.INTERNAL_SERVER_ERROR, this.errorBuilder(Status.INTERNAL_SERVER_ERROR,
                "Sorry, we are unable to process your request right now. Please try again later", uriInfo).build());
    }


    @ServerExceptionMapper
    public RestResponse<ApiError> mapAuthenticationFailedException(AuthenticationFailedException ex, UriInfo uriInfo) {
        log.error("Required privileges are missing.", ex);
        return buildRestResponse(Status.UNAUTHORIZED, ex, uriInfo);
    }

    @ServerExceptionMapper
    public RestResponse<ApiError> mapPrivilegeNotFoundException(PrivilegeNotFoundException ex, UriInfo uriInfo) {
        log.error("Privilege was not found in database", ex);
        return buildRestResponse(Status.BAD_REQUEST, ex, uriInfo);
    }

}

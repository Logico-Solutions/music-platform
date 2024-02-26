package org.logico.exhandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;
import org.logico.dto.error.ApiError;

import java.time.Instant;

public abstract class GenericExceptionHandler {

    public GenericExceptionHandler() {
    }

    protected RestResponse<ApiError> buildRestResponse(Response.Status status, ApiError apiError) {
        return RestResponse.status(status, apiError);
    }

    protected RestResponse<ApiError> buildRestResponse(Response.Status status, Exception ex, UriInfo uriInfo) {
        return this.buildRestResponse(status, this.errorBuilder(status, ex, uriInfo).build());
    }

    protected ApiError.ApiErrorBuilder errorBuilder(Response.Status status, Exception ex, UriInfo uriInfo) {
        return this.errorBuilder(status, ex.getMessage(), uriInfo);
    }

    protected ApiError.ApiErrorBuilder errorBuilder(Response.Status status, String message, UriInfo uriInfo) {
        return ApiError.builder().error(status.getReasonPhrase()).message(message)
                .path(uriInfo.getRequestUri().getPath()).timestamp(
                        Instant.now()).status(status.getStatusCode());
    }
}

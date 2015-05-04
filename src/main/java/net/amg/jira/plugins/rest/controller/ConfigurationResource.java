package net.amg.jira.plugins.rest.controller;

import com.atlassian.jira.rest.api.messages.TextMessage;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.google.gson.Gson;
import net.amg.jira.plugins.model.FormField;
import net.amg.jira.plugins.rest.model.ErrorCollection;
import net.amg.jira.plugins.services.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Resource providing validation service for the gadget.
 * Created by Ivo on 18/04/15.
 */
@Path("/configuration")
public class ConfigurationResource {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationResource.class);

    private Validator validator;

    /**
     * Accepts field values and checks the configuration for errors.
     *
     * @param project value of Project field
     * @param issues  values of Issues field
     * @param period  value of Period field
     * @param date
     * @return JAXB object encapsulating errors detected.
     */
    @Path("/validate")
    @GET
    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrefsValidation(@QueryParam("Project") String project, @QueryParam("Issues") String issues
            , @QueryParam("Period") String period, @QueryParam("Date") String date) {
        Map<FormField, String> paramMap = new HashMap<>();
        paramMap.put(FormField.PROJECT, project);
        paramMap.put(FormField.ISSUES, issues);
        paramMap.put(FormField.PERIOD, period);
        paramMap.put(FormField.DATE, date);
        ErrorCollection errorCollection = validator.validate(paramMap);
        Gson gson = new Gson();
        if (errorCollection.isEmpty()) {
            return Response.ok(gson.toJson(new TextMessage("No input configuration errors found."))).build();
        } else {
            String timestamp = "TIMESTAMP: " +
                    new java.text.SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());
            log.error(timestamp, "Invalid request parameters", errorCollection);
            return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(timestamp))
                    .entity(gson.toJson(errorCollection)).build();
        }
    }

    public Validator getValidator() {
        return validator;
    }

    /**
     * Used by Spring to autowire a component.
     *
     * @param validator
     */
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
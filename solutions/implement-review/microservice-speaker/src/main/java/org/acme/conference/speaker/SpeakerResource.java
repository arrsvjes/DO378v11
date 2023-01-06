package org.acme.conference.speaker;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/speaker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {
    @Inject
    SpeakerService service;

    @GET
    public List<Speaker> list() {
        return Speaker.listAll();
    }

    @GET
    @Path("/search")
    public List<Speaker> search(@QueryParam("q") String q) { 
        return service.search(q);
    }
    @GET
    @Path("/{uuid}")
    public Speaker findByUuid(@PathParam("uuid") UUID uuid) {
                        return service.findByUuid(uuid)
                            .orElseThrow(NotFoundException::new);
    }
    @POST
    @Transactional
    public Speaker create(final Speaker speaker) {
        //service.create(newSpeaker);
        Speaker newSpeaker = Speaker.of(speaker.nameFirst, speaker.nameLast, speaker.organization, speaker.biography, speaker.picture, speaker.twitterHandle, speaker.uuid);
        newSpeaker.persist();
        return newSpeaker;
    }
    
    @DELETE
    @Transactional
    @Path("/{uuid}")
    public void delete(@PathParam("uuid") UUID uuid) {
        service.deleteByUuid(uuid);
    }
}
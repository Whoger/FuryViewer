package com.furyviewer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.furyviewer.domain.Episode;

import com.furyviewer.repository.EpisodeRepository;
import com.furyviewer.service.OpenMovieDatabase.Service.EpisodeOmdbDTOService;
import com.furyviewer.service.dto.OpenMovieDatabase.EpisodeOmdbDTO;
import com.furyviewer.web.rest.errors.BadRequestAlertException;
import com.furyviewer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Episode.
 */
@RestController
@RequestMapping("/api")
public class EpisodeResource {

    private final Logger log = LoggerFactory.getLogger(EpisodeResource.class);

    private static final String ENTITY_NAME = "episode";

    private final EpisodeRepository episodeRepository;

    @Autowired
    EpisodeOmdbDTOService episodeOmdbDTOService;

    public EpisodeResource(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    /**
     * POST  /episodes : Create a new episode.
     *
     * @param episode the episode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new episode, or with status 400 (Bad Request) if the episode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/episodes")
    @Timed
    public ResponseEntity<Episode> createEpisode(@RequestBody Episode episode) throws URISyntaxException {
        log.debug("REST request to save Episode : {}", episode);
        if (episode.getId() != null) {
            throw new BadRequestAlertException("A new episode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Episode result = episodeRepository.save(episode);
        return ResponseEntity.created(new URI("/api/episodes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /episodes : Updates an existing episode.
     *
     * @param episode the episode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated episode,
     * or with status 400 (Bad Request) if the episode is not valid,
     * or with status 500 (Internal Server Error) if the episode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/episodes")
    @Timed
    public ResponseEntity<Episode> updateEpisode(@RequestBody Episode episode) throws URISyntaxException {
        log.debug("REST request to update Episode : {}", episode);
        if (episode.getId() == null) {
            return createEpisode(episode);
        }
        Episode result = episodeRepository.save(episode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, episode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /episodes : get all the episodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of episodes in body
     */
    @GetMapping("/episodes")
    @Timed
    public List<Episode> getAllEpisodes() {
        log.debug("REST request to get all Episodes");
        return episodeRepository.findAll();
        }

    /**
     * GET  /episodes/:id : get the "id" episode.
     *
     * @param id the id of the episode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the episode, or with status 404 (Not Found)
     */
    @GetMapping("/episodes/{id}")
    @Timed
    public ResponseEntity<Episode> getEpisode(@PathVariable Long id) {
        log.debug("REST request to get Episode : {}", id);
        Episode episode = episodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(episode));
    }


    /**
     *
     * Find episodes by name (commit obligado.... Pau e Ibra son malos....)
     *
     * @param name
     * @return
     */
    @GetMapping("/episodesByName/{name}")
    @Timed
    public ResponseEntity<List<Episode>> findEpisodeByName(@PathVariable String name) {
        log.debug("REST request to get Episodes by name", name);
        List<Episode> episode =episodeRepository.findEpisodeByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(episode));
    }

    /**
     * DELETE  /episodes/:id : delete the "id" episode.
     *
     * @param id the id of the episode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/episodes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        log.debug("REST request to delete Episode : {}", id);
        episodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/episode-api/test")
    @Timed
    public EpisodeOmdbDTO getTestInicial() throws Exception {


        return episodeOmdbDTOService.getEpisode("American Horror Story", 1, 2);
    }
}

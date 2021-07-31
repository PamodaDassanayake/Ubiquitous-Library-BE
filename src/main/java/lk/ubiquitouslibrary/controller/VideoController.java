package lk.ubiquitouslibrary.controller;

import io.swagger.annotations.ApiParam;
import lk.ubiquitouslibrary.entity.Video;
import lk.ubiquitouslibrary.repository.VideoRepository;
import lk.ubiquitouslibrary.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link Video}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);

    private static final String ENTITY_NAME = "video";

    @Autowired
    private VideoRepository videoRepository;

    /**
     * {@code POST  /videos} : Create a new video.
     *
     * @param video the video to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new video, or with status {@code 400 (Bad Request)} if the video has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/videos")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Video> createVideo(@RequestBody Video video) throws URISyntaxException {
        log.debug("REST request to save Video : {}", video);
        if (video.getId() != null) {
            throw new RuntimeException("idexists");
        }
        Video result = videoRepository.save(video);
        return ResponseEntity
            .created(new URI("/api/videos/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /videos/:id} : Updates an existing video.
     *
     * @param id the id of the video to save.
     * @param video the video to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated video,
     * or with status {@code 400 (Bad Request)} if the video is not valid,
     * or with status {@code 500 (Internal Server Error)} if the video couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/videos/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Video> updateVideo(@PathVariable(value = "id", required = false) final Long id, @RequestBody Video video)
        throws URISyntaxException {
        log.debug("REST request to update Video : {}, {}", id, video);
        if (video.getId() == null) {
            throw new RuntimeException("idnull");
        }
        if (!Objects.equals(id, video.getId())) {
            throw new RuntimeException("idinvalid");
        }

        if (!videoRepository.existsById(id)) {
            throw new RuntimeException("idnotfound");
        }

        Video result = videoRepository.save(video);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code GET  /videos} : get all the videos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videos in body.
     */
    @GetMapping("/videos")
    public List<Video> getAllVideos() {
        log.debug("REST request to get all Videos");
        return videoRepository.findAll();
    }

    /**
     * {@code GET  /search} : Search all the videos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of books in body.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Video>> getAllBooks(
            @ApiParam("Title of the video") @RequestParam(required = true) String title
    ) {
        log.debug("REST request to search all Videos");

        if (!(title == null || title.isBlank())){
            //serch by both
            return ResponseEntity.ok(videoRepository.findAllByTitleContaining(title));
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * {@code GET  /videos/:id} : get the "id" video.
     *
     * @param id the id of the video to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the video, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        log.debug("REST request to get Video : {}", id);
        Optional<Video> video = videoRepository.findById(id);
        return video.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /videos/:id} : delete the "id" video.
     *
     * @param id the id of the video to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/videos/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        log.debug("REST request to delete Video : {}", id);
        videoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}

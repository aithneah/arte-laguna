package pl.artelaguna.backend.contest.controller;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.artelaguna.backend.contest.model.Artwork;
import pl.artelaguna.backend.contest.service.ArtworkService;
import pl.artelaguna.backend.util.Optionals;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("artworks")
public class ArtworkController {

    @Autowired
    private ArtworkService artworks;

    @GetMapping("{id}/img")
    public void entryImage(@PathVariable int id, HttpServletResponse response) {
        Optionals.ifPresentOrElse(artworks.get(id).map(Artwork::getImage),
                image -> {
                    response.setContentLength(image.length);
                    response.setContentType("image/jpeg");
                    try {
                        Streams.copy(new ByteArrayInputStream(image), response.getOutputStream(), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, () -> response.setStatus(HttpStatus.NOT_FOUND.value()));
    }
}

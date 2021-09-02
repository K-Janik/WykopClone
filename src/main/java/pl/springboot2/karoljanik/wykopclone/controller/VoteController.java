package pl.springboot2.karoljanik.wykopclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.springboot2.karoljanik.wykopclone.dto.VoteDto;
import pl.springboot2.karoljanik.wykopclone.service.VoteService;

@RestController
@RequestMapping("/api/votes/")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        voteService.save(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

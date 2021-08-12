//package pl.springboot2.karoljanik.wykopclone.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import pl.springboot2.karoljanik.wykopclone.dto.TagDto;
//import pl.springboot2.karoljanik.wykopclone.security.TagService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tag")
//public class TagController {
//
//    private final TagService tagService;
//
//    @Autowired
//    public TagController(TagService tagService) {
//        this.tagService = tagService;
//    }
//
//    @PostMapping
//    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
//       return ResponseEntity.status(HttpStatus.CREATED)
//               .body(tagService.save(tagDto));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TagDto>> getAllTags() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(tagService.getAll());
//    }
//}

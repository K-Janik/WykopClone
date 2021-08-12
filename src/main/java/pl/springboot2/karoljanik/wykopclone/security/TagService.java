//package pl.springboot2.karoljanik.wykopclone.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import pl.springboot2.karoljanik.wykopclone.dto.TagDto;
//import pl.springboot2.karoljanik.wykopclone.model.Tag;
//import pl.springboot2.karoljanik.wykopclone.repository.TagRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TagService {
//
//    private final TagRepository tagRepository;
//
//    @Autowired
//    public TagService(TagRepository tagRepository) {
//        this.tagRepository = tagRepository;
//    }
//
//    @Transactional
//    public TagDto save(TagDto tagDto) {
//        Tag save= tagRepository.save(mapToTag(tagDto));
//        tagDto.setId(save.getId());
//        return tagDto;
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<TagDto> getAll() {
//        return tagRepository.findAll()
//                .stream()
//                .map(this::mapToTagDto)
//                .collect(Collectors.toList());
//    }
//
//    private TagDto mapToTagDto(Tag tag) {
//        return TagDto.builder().name(tag.getTagName())
//                .description(tag.getDescription())
//                .numberOfPosts(tag.getPosts().size())
//                .build();
//    }
//
//    private Tag mapToTag(TagDto tagDto) {
//        return Tag.builder().tagName(tagDto.getName())
//                .description(tagDto.getDescription())
//                .build();
//    }
//
//}

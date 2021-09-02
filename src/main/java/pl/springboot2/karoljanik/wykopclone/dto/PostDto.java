package pl.springboot2.karoljanik.wykopclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String tagName;
    private String postName;
    private String url;
    private String description;
    //new fields
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}

package pl.springboot2.karoljanik.wykopclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.springboot2.karoljanik.wykopclone.model.VoteType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}

package com.example.newsfeed.dto.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RequestComment {

    private final Long postId;

    private final Long commentId;

    @NotNull
    private final String contents;

    @JsonCreator
    public RequestComment(
            @JsonProperty("post_id") Long postId,
            @JsonProperty("comment_id") Long commentId,
            @JsonProperty("contents") String contents) {
        this.postId = postId;
        this.commentId = commentId;
        this.contents = contents;
    }
}

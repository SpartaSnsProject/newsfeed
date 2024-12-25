package com.example.newsfeed.dto.comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RequestPatchComment {

    @NotNull
    @NotBlank
    Long commentId;

    @NotNull
    String contents;

    @JsonCreator
    public RequestPatchComment(
            @JsonProperty("comment_id") Long commentId,
            @JsonProperty("contents") String contents) {
        this.commentId = commentId;
        this.contents = contents;
    }
}

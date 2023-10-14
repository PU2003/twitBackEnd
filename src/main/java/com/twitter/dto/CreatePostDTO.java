package com.twitter.dto;

import com.twitter.models.ApplicationUser;
import com.twitter.models.Audience;
import com.twitter.models.Post;
import com.twitter.models.ReplyRestriction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDTO {
    private String content;
    private ApplicationUser author;
    Set <Post> replies;
    private boolean scheduled;
    private Date scheduledDate;
    private Audience audience;
    private ReplyRestriction replyRestriction;

}

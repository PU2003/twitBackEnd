package com.twitter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Integer postId;

    @Column(length = 255,nullable = false)
    private String content;

    @Column(name = "posted_date")
    private Date postedDate;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    private ApplicationUser author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "post_likes_junction",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<ApplicationUser> likes;

    @OneToMany
    private List<Image> images;

    /*TODO : Figure out video upload*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_reply_junction",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "reply_id")}
    )
    @JsonIgnore
    private Set<Post> replies;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_repost_junction",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<ApplicationUser> reposts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_bookmark_junction",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<ApplicationUser> bookmarks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_view_junction",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<ApplicationUser> views;

    private Boolean scheduled;

    @Column(name = "scheduled_date",nullable = true)
    private Date scheduledDate;

    @Enumerated(EnumType.ORDINAL)
    private Audience audience;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reply_restriction")
    private ReplyRestriction replyRestriction;

    public Post() {
        super();
        this.likes = likes;
        this.images = images;
        this.replies = replies;
        this.reposts = reposts;
        this.bookmarks = bookmarks;
        this.views = views;
    }



}

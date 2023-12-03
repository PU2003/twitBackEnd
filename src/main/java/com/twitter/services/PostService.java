package com.twitter.services;

import com.twitter.dto.CreatePostDTO;
import com.twitter.exceptions.PostDoesNotExistException;
import com.twitter.exceptions.UnableToCreatePostException;
import com.twitter.models.ApplicationUser;
import com.twitter.models.Image;
import com.twitter.models.Post;
import com.twitter.repositories.PostRepository;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service
@Transactional
public class PostService {

    private final PostRepository postRepo;
    private final ImageService imageService;
    @Autowired
    public PostService(PostRepository postRepo,ImageService imageService){
        this.postRepo = postRepo;
        this.imageService = imageService;
    }

    public Post createPost(CreatePostDTO dto){

        Post p = new Post();
        p.setContent(dto.getContent());
        if(dto.isScheduled()){
            p.setPostedDate(dto.getScheduledDate());
        } else {
            p.setPostedDate(new Date());
        }
        p.setAuthor(dto.getAuthor());
        p.setReplies(dto.getReplies());
        p.setScheduled(dto.isScheduled());
        p.setScheduledDate(dto.getScheduledDate());
        p.setAudience(dto.getAudience());
        p.setReplyRestriction(dto.getReplyRestriction());

        try{
            Post posted = postRepo.save(p);
            return posted;
        } catch (Exception e){
            //TODO: Setup custom Exception
            throw new UnableToCreatePostException();
        }
    }
    public Post createMediaPost(String post, List<MultipartFile> files){
        CreatePostDTO dto = new CreatePostDTO();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            dto = objectMapper.readValue(post,CreatePostDTO.class);

            Post p = new Post();
            p.setContent(dto.getContent());
            if(dto.isScheduled()){
                p.setPostedDate(dto.getScheduledDate());
            } else {
                p.setPostedDate(new Date());
            }
            p.setAuthor(dto.getAuthor());
            p.setReplies(dto.getReplies());
            p.setScheduled(dto.isScheduled());
            p.setScheduledDate(dto.getScheduledDate());
            p.setAudience(dto.getAudience());
            p.setReplyRestriction(dto.getReplyRestriction());

            // Upload the images that got passed
            List<Image> postImages = new ArrayList<>();

            for(int i=0;i< files.size();i++){
                Image postImage = imageService.uploadImage(files.get(i),"post");
                postImages.add(postImage);
            }

            p.setImages(postImages);

            return postRepo.save(p);
        } catch(Exception e){
            throw new UnableToCreatePostException();
        }
    }

    public List <Post> getAllPosts(){
        return postRepo.findAll();
    }
    public Post getPostById(Integer id){
        //TODO: Setup custom exception for posts that doesn't exist
        return postRepo.findById(id).orElseThrow(PostDoesNotExistException::new);
    }

    public Set<Post> getAllPostByAuthor(ApplicationUser author){
        Set<Post> userPosts = postRepo.findByAuthor(author).orElse(new HashSet<>());

        return userPosts;
    }
    public void deletePost(Post p){
        postRepo.delete(p);
    }
}

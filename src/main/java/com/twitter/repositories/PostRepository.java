package com.twitter.repositories;

import com.twitter.models.ApplicationUser;
import com.twitter.models.Post;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Integer> {

    Optional<Set<Post>> findByAuthor(ApplicationUser author);

}

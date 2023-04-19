package com.amcef.repository;

import com.amcef.entity.Post;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Integer>,
        QuerydslPredicateExecutor<Post> {

    List<Post> findAll(Predicate predicate);
}

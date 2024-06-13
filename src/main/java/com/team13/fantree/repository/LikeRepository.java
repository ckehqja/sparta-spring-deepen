package com.team13.fantree.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team13.fantree.entity.ContentEnumType;
import com.team13.fantree.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Optional<Like> findByUserIdAndContentIdAndContentType(long userId, long contentId, ContentEnumType byType);
}

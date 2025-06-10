package me.sideproject.urlshortener.repository;

import me.sideproject.urlshortener.models.URLMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface URLMapRepository extends JpaRepository<URLMap, Integer> {
  @Query("SELECT u FROM URLMap u WHERE u.shortCode = :shortCode")
  Optional<URLMap> findByShortCode(@Param("shortCode") String shortCode);

  @Transactional
  @Modifying
  @Query("UPDATE URLMap u SET u.clickCount = u.clickCount + 1, u.updatedAt = CURRENT_TIMESTAMP WHERE u.shortCode = :shortCode")
  void incrementClickCount(@Param("shortCode") String shortCode);
}

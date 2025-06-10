package me.sideproject.urlshortener.repository;

import io.lettuce.core.dynamic.annotation.Param;
import me.sideproject.urlshortener.models.URLMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLMapRepository extends JpaRepository<URLMap, Integer> {
  @Query("SELECT u FROM URLMap u WHERE u.shortCode = :shortCode")
  Optional<URLMap> findByShortCode(@Param("shortCode") String shortCode);
}

package me.sideproject.urlshortener.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "url_map", schema = "url_shortener")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class URLMap {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "original_url")
  private String originalURL;

  @Column(name = "short_code")
  private String shortCode;

  @Column(name = "click_count")
  private Integer clickCount;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Date updatedAt;

  @Column(name = "expired_at")
  private Date expiredAt;
}

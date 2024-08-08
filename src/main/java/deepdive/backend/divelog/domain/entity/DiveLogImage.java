package deepdive.backend.divelog.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DIVE_LOG_PICTURE")
public class DiveLogImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "THUMB_NAIL")
	private String thumb_nail;
	@Column(name = "FILE_NAME")
	private String file_name;
	@Column(name = "CREATED_AT")
	private LocalDateTime created_at;
	@Column(name = "DELETED_AT")
	private LocalDateTime deleted_at;
	@Column(name = "URL")
	private String url;
	@Column(name = "DIVE_LOG_ID")
	private Long diveLogId;

	protected DiveLogImage(String url, Long diveLogId) {
		this.url = url;
		this.created_at = LocalDateTime.now();
		this.deleted_at = null;
		this.diveLogId = diveLogId;
	}

	public static DiveLogImage of(String url, Long diveLogId) {
		return new DiveLogImage(url, diveLogId);
	}
}

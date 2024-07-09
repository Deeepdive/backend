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
public class DiveLogPicture {

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

	protected DiveLogPicture(String url) {
		this.url = url;
		this.created_at = LocalDateTime.now();
		this.deleted_at = null;
	}

	public static DiveLogPicture of(String url) {
		return new DiveLogPicture(url);
	}
}

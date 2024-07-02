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
@Table(name = "DIVE_LOG_PICTURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiveLogPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "URL")
	private String url;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	protected DiveLogPicture(String url, String fileName) {
		this.url = url;
		this.fileName = fileName;
		this.createdAt = LocalDateTime.now();
		this.deletedAt = null;
	}

	public static DiveLogPicture of(String url, String fileName) {
		return new DiveLogPicture(url, fileName);
	}
}

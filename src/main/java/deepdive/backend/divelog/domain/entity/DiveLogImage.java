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
@Table(name = "DIVE_LOG_IMAGE")
public class DiveLogImage {

	private static final char URL_SEPARATOR = '/';

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FILE_NAME")
	private String fileName;
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	@Column(name = "DELETED_AT")
	private LocalDateTime deletedAt;
	@Column(name = "URL")
	private String url;
	@Column(name = "DIVE_LOG_ID")
	private Long diveLogId;

	protected DiveLogImage(String url, Long diveLogId) {
		this.url = url;
		this.createdAt = LocalDateTime.now();
		this.deletedAt = null;
		this.diveLogId = diveLogId;
		this.fileName = getOriginName(url);
	}

	public static DiveLogImage of(String url, Long diveLogId) {
		return new DiveLogImage(url, diveLogId);
	}

	private String getOriginName(String url) {
		return url.substring(url.lastIndexOf(URL_SEPARATOR) + 1);
	}
}

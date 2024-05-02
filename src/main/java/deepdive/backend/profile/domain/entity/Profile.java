package deepdive.backend.profile.domain.entity;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.member.domain.entity.Member;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PROFILE")
public class Profile {

	private static final Integer MAX_LEN = 20;
	private static final Integer MIN_LEN = 4;
	private static final String REGEX = "^[a-z0-9]+$";
	private static final List<String> INVALID_WORDS = List.of("admin", "deepdive", "master",
		"error");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NICK_NAME")
	private String nickName;

	// picture 부분 멀티파트로 수정하기.
	@Column(name = "PICTURE")
	private String picture;
	@Column(name = "IS_TEACHER")
	private Boolean isTeacher;
	@Column(name = "ETC")
	private String etc;

	@Enumerated(EnumType.STRING)
	@Column(name = "CERT_ORGANIZATION")
	private CertOrganization certOrganization;
	@Enumerated(EnumType.STRING)
	@Column(name = "CERT_TYPE")
	private CertType certType;

	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	protected Profile(String nickName) {
		this.nickName = nickName;
	}

	public static Profile of(String nickName) {
		return new Profile(nickName);
	}

	public void updateCertProfile(CertOrganization certOrganization, CertType certType,
		Boolean isTeacher) {
		this.certOrganization = certOrganization;
		this.certType = certType;
		this.isTeacher = isTeacher;
		this.etc = null;
	}

	public void updateEtcCertProfile(CertOrganization etcOrganization, Boolean isTeacher,
		String etc) {
		this.certType = null;
		this.certOrganization = etcOrganization;
		this.isTeacher = isTeacher;
		this.etc = etc;
	}

	public void updateDefaultImage(String url) {
		this.picture = url;
	}

	public void updateNickName(String newNickName) {
		validateNickName(newNickName);
		this.nickName = newNickName;
	}

	private void validateNickName(String nickName) {
		Pattern pattern = Pattern.compile(REGEX);
		if (nickName.length() > MAX_LEN || nickName.length() < MIN_LEN
			|| !pattern.matcher(nickName).matches()) {
			throw ExceptionStatus.INVALID_NICKNAME_TYPE.asDomainException();
		}
		if (isContainInvalidWords(nickName)) {
			throw ExceptionStatus.INVALID_WORD_CONTAIN.asDomainException();
		}
	}

	private boolean isContainInvalidWords(String nickName) {
		String result = nickName.toLowerCase();
		return INVALID_WORDS.stream().anyMatch(result::contains);
	}

	public void updateDefaultProfile(String url, String nickName) {
		this.picture = url;
		this.validateNickName(nickName);
		this.nickName = nickName;
	}
}

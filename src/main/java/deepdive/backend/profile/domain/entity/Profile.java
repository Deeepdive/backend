package deepdive.backend.profile.domain.entity;

import deepdive.backend.exception.ExceptionStatus;
import deepdive.backend.profile.domain.CertOrganization;
import deepdive.backend.profile.domain.CertType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Profile {

	private static final Integer MAX_LEN = 20;
	private static final Integer MIN_LEN = 4;
	private static final String REGEX = "^[a-z0-9]+$";
	private static final List<String> INVALID_WORDS = List.of("admin", "deepdive", "master",
		"error");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long id;

	private String nickName;

	// picture 부분 멀티파트로 수정하기.
	private String picture;
	private Boolean isTeacher;
	private String etc;

	@Enumerated(EnumType.STRING)
	private CertOrganization certOrganization;
	@Enumerated(EnumType.STRING)
	private CertType certType;

	protected Profile(String nickName) {
		this.nickName = nickName;
	}

	public static Profile defaultProfile(String nickName, String picture) {

		return new Profile(nickName);
	}

	public static Profile of(String nickName) {
		return new Profile(nickName);
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
		return INVALID_WORDS.contains(result);
	}

	public void updateDefaultProfile(String nickName, String picture) {
		validateNickName(nickName);
		this.nickName = nickName;
		this.picture = picture;
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

	public void saveCertProfile(String nickName, String url,
		CertOrganization organization, Boolean isTeacher, String etc) {
		this.nickName = nickName;
		this.picture = url;
		updateEtcCertProfile(organization, isTeacher, etc);
	}

	public void saveCommonProfile(String nickName, String picture,
		CertOrganization certOrganization, CertType certType, Boolean isTeacher) {
		updateDefaultProfile(nickName, picture);
		updateCertProfile(certOrganization, certType, isTeacher);
	}
}

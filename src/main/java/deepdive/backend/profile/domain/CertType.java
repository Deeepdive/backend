package deepdive.backend.profile.domain;

public enum CertType {
	NONE, OPEN_WATER, ADVANCED_OPEN_WATER, RESCUE, DIVE_MASTER, ETC;

	public static boolean common(CertType type) {
		return type.equals(OPEN_WATER)
			|| type.equals(ADVANCED_OPEN_WATER) || type.equals(RESCUE)
			|| type.equals(DIVE_MASTER);
	}
}

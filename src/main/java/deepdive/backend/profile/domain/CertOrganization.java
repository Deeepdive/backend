package deepdive.backend.profile.domain;

public enum CertOrganization {
	NONE, PADI, SDI, SSI, BSAC, KUDA, TDI, NAUI, CMAS, ETC;

	public static boolean common(CertOrganization organization) {
		return organization.equals(PADI)
			|| organization.equals(SDI) || organization.equals(SSI)
			|| organization.equals(BSAC) || organization.equals(KUDA) || organization.equals(TDI)
			|| organization.equals(NAUI)
			|| organization.equals(CMAS);
	}
}

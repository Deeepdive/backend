package deepdive.backend.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
	ADMIN("ROLE_ADMIN"),
	CLIENT("ROLE_USER");

	private String value;
}

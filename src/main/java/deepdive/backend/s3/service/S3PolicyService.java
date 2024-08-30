package deepdive.backend.s3.service;

import deepdive.backend.exception.ExceptionStatus;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3PolicyService {

	private static final List<String> ALLOWED_EXTENSIONS =
			Arrays.asList("jpg", "jpeg", "png", "gif");
	private static final String FILE_EXTENSION_SEPARATOR = ".";


	public void validateImageFile(MultipartFile image) {
		validateEmpty(image);
		validateFileExtension(image);
		validateImageContent(image);
	}

	private void validateEmpty(MultipartFile image) {
		if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
			throw ExceptionStatus.EMPTY_IMAGE.asServiceException();
		}
	}

	private void validateFileExtension(MultipartFile image) {
		String fileName = image.getOriginalFilename();
		int lastDotIndex = fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);

		if (lastDotIndex == -1) {
			throw ExceptionStatus.INVALID_IMAGE_EXTENSION.asServiceException();
		}

		String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
		if (!ALLOWED_EXTENSIONS.contains(extension)) {
			throw ExceptionStatus.INVALID_IMAGE_EXTENSION.asServiceException();
		}
	}

	private void validateImageContent(MultipartFile image) {
		try {
			BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
			if (bufferedImage == null) {
				throw ExceptionStatus.INVALID_IMAGE_CONTENT.asServiceException();
			}
		} catch (IOException e) {
			throw ExceptionStatus.INVALID_IMAGE_CONTENT.asServiceException();
		}
	}
}

package deepdive.backend.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import deepdive.backend.divelog.service.DiveLogCommandService;
import deepdive.backend.dto.s3.S3ResponseDto;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class S3Service {

	private static final char URL_SEPARATOR = '/';
	private static final char EXTENSION_SEPARATOR = '.';
	private final AmazonS3 s3Client;
	private final S3PolicyService s3PolicyService;
	private final DiveLogCommandService diveLogCommandService;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Transactional
	public S3ResponseDto uploadImage(MultipartFile imageFile) throws IOException {
		s3PolicyService.validateImageFile(imageFile);
		String originalFileName = imageFile.getOriginalFilename();
		int extensionIndex = originalFileName.lastIndexOf(EXTENSION_SEPARATOR);
		String extension = originalFileName.substring(extensionIndex + 1);

		String s3FileName = generateS3FileName(originalFileName);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(imageFile.getContentType());
		objectMetadata.setContentLength(imageFile.getSize());
		s3Client.putObject(bucketName, s3FileName, imageFile.getInputStream(),
				objectMetadata);

		String url = s3Client.getUrl(bucketName, s3FileName).toString();
		diveLogCommandService.saveImage(url);
		return new S3ResponseDto(url, extension);
	}

	private String generateS3FileName(String fileName) {
		String generatedName = UUID.randomUUID() + fileName;

		return generatedName.substring(0, 10);
	}

	// 이미지 올린 후 취소 혹은 지우기 요청
	public void deleteImage(String fileName) {
		s3Client.deleteObject(bucketName, fileName);
	}

}

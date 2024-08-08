package deepdive.backend.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import deepdive.backend.exception.ExceptionStatus;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3FileUploadService {

	private final AmazonS3 s3Client;
	private final S3PolicyService s3PolicyService;
	@Value("{cloud.aws.s3.bucket}")
	private String bucketName;

	public String upload(MultipartFile imageFile) {
		s3PolicyService.validateImageFile(imageFile);
		String originalFileName = imageFile.getOriginalFilename();

		String s3FileName = generateS3FileName(originalFileName);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(imageFile.getContentType());
		try {
			InputStream inputStream = imageFile.getInputStream();
			s3Client.putObject(
					new PutObjectRequest(bucketName, originalFileName, inputStream,
							objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw ExceptionStatus.INVALID_IMAGE_CONTENT.asServiceException();
		}

		return s3Client.getUrl(bucketName, s3FileName).toString();
	}

	private String generateS3FileName(String fileName) {
		return UUID.randomUUID() + fileName;
	}
}

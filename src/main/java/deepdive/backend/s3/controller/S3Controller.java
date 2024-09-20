package deepdive.backend.s3.controller;

import deepdive.backend.s3.service.S3Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v2/s3")
@RequiredArgsConstructor
@Slf4j
public class S3Controller {

	private final S3Service s3Service;

	@PostMapping("/image")
	public String uploadFile(@RequestPart("file") MultipartFile multipartFile)
			throws IOException {
		return s3Service.uploadImage(multipartFile);
	}

	@DeleteMapping("/image/{fileName}")
	public void deleteFile(@PathVariable(value = "fileName") String fileName) {
		s3Service.deleteImage(fileName);
	}
}

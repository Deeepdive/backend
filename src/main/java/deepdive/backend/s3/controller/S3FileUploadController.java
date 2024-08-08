package deepdive.backend.s3.controller;

import deepdive.backend.s3.service.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v2/s3")
@RequiredArgsConstructor
public class S3FileUploadController {

	private final S3FileUploadService s3FileUploadService;

	@PostMapping("/image")
	public String uploadFile(@RequestParam MultipartFile multipartFile) {
		return s3FileUploadService.upload(multipartFile);
	}
}

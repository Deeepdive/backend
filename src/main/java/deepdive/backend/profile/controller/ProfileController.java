package deepdive.backend.profile.controller;

import deepdive.backend.profile.domain.dto.ProfileRequestDto;
import deepdive.backend.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/update")
    public void updateProfile(@RequestBody ProfileRequestDto dto) {

        profileService.update(dto);
    }
}

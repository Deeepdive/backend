package deepdive.backend.profile.controller;

import deepdive.backend.profile.domain.dto.ProfileRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @PostMapping("/update")
    public void updateProfile(@RequestBody ProfileRequestDto dto) {

    }
}

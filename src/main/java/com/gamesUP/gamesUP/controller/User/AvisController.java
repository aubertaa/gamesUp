package com.gamesUP.gamesUP.controller.User;

import com.gamesUP.gamesUP.DTO.User.AvisDTO;
import com.gamesUP.gamesUP.DTO.User.AvisResponseDTO;
import com.gamesUP.gamesUP.service.User.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
public class AvisController {
    @Autowired
    private AvisService avisService;

    @GetMapping
    public List<AvisResponseDTO> getAllAvis() {
        return avisService.getAllAvis();
    }

    @GetMapping("/{id}")
    public AvisResponseDTO getAvisById(@PathVariable Long id) {
        return avisService.getAvisById(id);
    }

    @PostMapping
    public void addAvis(@RequestBody AvisDTO avisDTO) {
        avisService.addAvis(avisDTO);
    }

    @PutMapping("/{id}")
    public void updateAvis(@PathVariable Long id, @RequestBody AvisDTO avisDTO) {
        avisService.updateAvis(id, avisDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAvis(@PathVariable Long id) {
        avisService.deleteAvis(id);
    }
}

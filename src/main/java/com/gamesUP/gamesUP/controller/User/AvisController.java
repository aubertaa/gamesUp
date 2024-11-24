package com.gamesUP.gamesUP.controller.User;

import com.gamesUP.gamesUP.DTO.User.AvisDTO;
import com.gamesUP.gamesUP.DTO.User.AvisResponseDTO;
import com.gamesUP.gamesUP.service.User.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
public class AvisController {
    @Autowired
    private AvisService avisService;

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping
    public List<AvisResponseDTO> getAllAvis() {
        return avisService.getAllAvis();
    }

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping("/{id}")
    public AvisResponseDTO getAvisById(@PathVariable Long id) {
        return avisService.getAvisById(id);
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @PostMapping
    public void addAvis(@RequestBody AvisDTO avisDTO) {
        avisService.addAvis(avisDTO);
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @PutMapping("/{id}")
    public void updateAvis(@PathVariable Long id, @RequestBody AvisDTO avisDTO) {
        avisService.updateAvis(id, avisDTO);
    }

    @PreAuthorize("hasRole('administrateur')")
    @DeleteMapping("/{id}")
    public void deleteAvis(@PathVariable Long id) {
        avisService.deleteAvis(id);
    }
}

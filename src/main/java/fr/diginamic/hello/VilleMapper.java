package fr.diginamic.hello;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.dto.VilleDto;

public class VilleMapper {
    public static VilleDto toDto(Ville ville) {
        return new VilleDto(
                String.valueOf(ville.getId()),
                ville.getPopulation(),
                ville.getDepartement().getCode(),
                ville.getDepartement().getNom()
        );
    }
}

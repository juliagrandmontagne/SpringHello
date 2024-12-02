package fr.diginamic.hello;
import fr.diginamic.hello.Departement;
import fr.diginamic.hello.dto.DepartementDto;

public class DepartementMapper {
    public static DepartementDto toDto(Departement departement) {
        int totalPopulation = departement.getVilles()
                .stream()
                .mapToInt(Ville::getPopulation)
                .sum();
        return new DepartementDto(
                departement.getCode(),
                departement.getNom(),
                totalPopulation
        );
    }
}
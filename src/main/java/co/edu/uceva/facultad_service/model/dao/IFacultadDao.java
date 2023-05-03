package co.edu.uceva.facultad_service.model.dao;

import co.edu.uceva.facultad_service.model.entities.Facultad;
import org.springframework.data.repository.CrudRepository;

public interface IFacultadDao extends CrudRepository<Facultad, Long> {
}
package co.edu.uceva.facultad_service.model.service;

import co.edu.uceva.facultad_service.model.entities.Facultad;

import java.util.List;


public interface IFacultadService {
    Facultad save (Facultad facultad);

    void delete (Facultad facultad);

    Facultad update (Facultad facultad);

    Facultad findById(Long id);

    List<Facultad> findAll();
}

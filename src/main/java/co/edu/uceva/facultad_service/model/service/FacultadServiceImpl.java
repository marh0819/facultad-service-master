package co.edu.uceva.facultad_service.model.service;


import co.edu.uceva.facultad_service.model.dao.IFacultadDao;
import co.edu.uceva.facultad_service.model.entities.Facultad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultadServiceImpl implements IFacultadService{


    @Autowired
    IFacultadDao facultadDao;

    @Override
    public Facultad save(Facultad facultad) {
        return facultadDao.save(facultad);
    }

    @Override
    public void delete(Facultad facultad) {
        facultadDao.delete(facultad);
    }

    @Override
    public Facultad update(Facultad facultad) {
        return facultadDao.save(facultad);
    }

    @Override
    public Facultad findById(Long id) {
        return facultadDao.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)  //Para ejecutar en modo de solo lectura
    public List<Facultad> findAll() {
        List<Facultad> facultad = (List<Facultad>) facultadDao.findAll();
        return facultad;
    }
}
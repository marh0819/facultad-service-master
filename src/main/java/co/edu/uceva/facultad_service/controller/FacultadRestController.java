package co.edu.uceva.facultad_service.controller;


import co.edu.uceva.facultad_service.model.entities.Facultad;
import co.edu.uceva.facultad_service.model.service.IFacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facultad-service")
@CrossOrigin( origins = "http://localhost:4200", allowedHeaders = "*")
public class FacultadRestController {
    @Autowired
    IFacultadService facultadService;


    /**
     * Metodo que lista las facultades
     * @return Lista de facultades
     */

    @GetMapping("/listar")
    public List<Facultad> listar(){
        return facultadService.findAll();
    }

    /**
     * Metodo que lista las facultades por id
     * @return Lista de facultades
     */
    @GetMapping("/listar/{id}")
    public Facultad buscarFacultad(@PathVariable("id") Long id){
        return facultadService.findById(id);
    }

    /**
     * Metodo que crea facultades
     * @return Facultad
     */
    @PostMapping("listar")
    public Facultad crearFacultad(@RequestBody Facultad facultad){
        return facultadService.save(facultad);
    }

    /**
     * Metodo que que borra facultades
     * @return
     */
    @PostMapping("listar/{id}")
    public void borrarFacultad(@PathVariable("id") Long id){
        Facultad facultad;
        facultad = facultadService.findById(id);
        facultadService.delete(facultad);
    }

    /**
     * Metodo que modifica facultades
     * return Facultad modificada
     */
    @PutMapping("/listar")
    public Facultad actualizarFacultad(@RequestBody Facultad facultad){
        return facultadService.update(facultad);
    }

}


package co.edu.uceva.facultad_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Pruebas unitarias (unit tests) para la  API RESTful que se encarga de realizar operaciones CRUD sobre una entidad
 * llamada "Pais".
 * Se importan las clases necesarias para realizar las pruebas (MockMvc, ObjectMapper, etc.), se inyecta el servicio
 * que se encarga de realizar las operaciones sobre la entidad "Pais" (IPaisService), y se definen varios métodos de
 * prueba para la API RESTful, que comprueban el correcto funcionamiento de los métodos:
 * GET, POST, PUT y DELETE de la API.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PaisRestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private IPaisService paisService;

    /**
     * Inicializa los objetos necesarios para la prueba. En el ejemplo de código dado, este método se utiliza para \
     * inicializar el objeto MockMvc, que se utiliza para simular el envío de solicitudes HTTP en la prueba de la  \
     * clase PaisRestController.
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Prueba del método GET "/pais-service/hola/{nombre}", que comprueba que se recibe el nombre correcto
     * en la respuesta.
     * @throws Exception
     */
    @Test
    public void testHolaMundo() throws Exception {
        String nombre = "Juan";
        this.mockMvc.perform(get("/pais-service/hola/{nombre}", nombre))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola " + nombre));
    }

    /**
     * Prueba del método GET "/pais-service/paises", que comprueba que se recibe una lista de países en la respuesta.
     * @throws Exception
     */
    @Test
    public void testListar() throws Exception {
        Pais pais1 = new Pais();
        pais1.setNombre("croacia");
        Pais pais2 = new Pais();
        pais2.setNombre("España");
        paisService.save(pais1);
        paisService.save(pais2);
        List<Pais> listaPaises = new ArrayList<>();
        listaPaises.add(pais1);
        listaPaises.add(pais2);

        this.mockMvc.perform(get("/pais-service/paises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is(pais1.getNombre())))
                .andExpect(jsonPath("$[1].nombre", is(pais2.getNombre())));

        paisService.delete(pais1);
        paisService.delete(pais2);
    }

    /**
     * Prueba del método GET "/pais-service/paises/{id}", que comprueba que se recibe el país correcto en la respuesta.
     * @throws Exception
     */
    @Test
    public void testBuscarPais() throws Exception {
        Pais pais = new Pais();
        pais.setNombre("España");
        paisService.save(pais);

        this.mockMvc.perform(get("/pais-service/paises/{id}", pais.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(pais.getNombre())));

        paisService.delete(pais);
    }

    /**
     * Prueba del método POST "/pais-service/paises", que comprueba que se crea un nuevo país correctamente.
     * @throws Exception
     */
    @Test
    public void testCrearPais() throws Exception {
        Pais pais = new Pais();
        pais.setNombre("España");

        this.mockMvc.perform(post("/pais-service/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pais)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(pais.getNombre())));

        paisService.delete(pais);
    }

    /**
     * Prueba del método PUT "/pais-service/paises", que comprueba que se actualiza un país correctamente.
     * @throws Exception
     */
    @Test
    public void testActualizarPais() throws Exception {
        Pais pais = new Pais();
        pais.setNombre("España");
        paisService.save(pais);
        pais.setNombre("Portugal");

        this.mockMvc.perform(put("/pais-service/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pais)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(pais.getNombre())));
//                .andExpect(jsonPath("$.capital", is(pais.getNombre())));

        paisService.delete(pais);
    }

    /**
     * Prueba del método DELETE "/pais-service/paises/{id}", que comprueba que se elimina un país correctamente.
     * @throws Exception
     */
    @Test
    public void testBorrarPais() throws Exception {
        Pais pais = new Pais();
        pais.setNombre("Canada");
        paisService.save(pais);

        this.mockMvc.perform(delete("/pais-service/paises/{id}", pais.getId()))
                .andExpect(status().isOk());

        assertNull(paisService.findById(pais.getId()));
    }

    /**
     * Método para convertir un objeto a una cadena JSON
     *
     * @param obj Objeto a convertir
     * @return Cadena JSON
     */
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;


import javax.transaction.Transactional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
public class EspecialidadServiceTest {

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

	private List<EspecialidadEntity> especialidadList = new ArrayList<>();



    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
            especialidadEntity.setDescripcion(String.valueOf(i).repeat(11));
			entityManager.persist(especialidadEntity);
			especialidadList.add(especialidadEntity);
		}
	}

    //Prueba de crear un medico

    @Test
	void testCreateEspecialidad() throws IllegalOperationException {
		EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);
		
		EspecialidadEntity result = especialidadService.createEspecialidad(newEntity);
		assertNotNull(result);

		EspecialidadEntity entity = entityManager.find(EspecialidadEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
	}

    @Test
	void testCreateEspecialidadInvalidDescripcion() {
		assertThrows(IllegalOperationException.class, ()->{
			EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);

			newEntity.setDescripcion("doctor");
			especialidadService.createEspecialidad(newEntity);
		});
	}

	@Test
	void testCreateEspecialidadInvalidDescripcion2() {
		assertThrows(IllegalOperationException.class, ()->{
			EspecialidadEntity newEntity = factory.manufacturePojo(EspecialidadEntity.class);

			newEntity.setDescripcion(null);
			especialidadService.createEspecialidad(newEntity);
		});
	}

    
    
}

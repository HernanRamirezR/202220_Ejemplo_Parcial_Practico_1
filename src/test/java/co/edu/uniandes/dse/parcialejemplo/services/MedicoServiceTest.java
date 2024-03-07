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

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;

import org.junit.jupiter.api.extension.ExtendWith;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest 
{
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

	private List<MedicoEntity> medicoList = new ArrayList<>();



    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			medicoEntity.setRegistroMedico("RM" + String.valueOf(i));
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}

    //Prueba de crear un medico

    @Test
	void testCreateMedico() throws IllegalOperationException {
		MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
		newEntity.setRegistroMedico("RM1234");
		
		MedicoEntity result = medicoService.createMedico(newEntity);
		assertNotNull(result);

		MedicoEntity entity = entityManager.find(MedicoEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getApellido(), entity.getApellido());
		assertEquals(newEntity.getRegistroMedico(), entity.getRegistroMedico());
	}


    @Test
	void testCreateMedicoInvalidRM() {
		assertThrows(IllegalOperationException.class, ()->{
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);

			newEntity.setRegistroMedico("1234");
			medicoService.createMedico(newEntity);
		});
	}

	@Test
	void testCreateMedicoInvalidRM1() {
		assertThrows(IllegalOperationException.class, ()->{
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);

			newEntity.setRegistroMedico("");
			medicoService.createMedico(newEntity);
		});
	}

	@Test
	void testCreateMedicoInvalidRM2() {
		assertThrows(IllegalOperationException.class, ()->{
			MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);

			newEntity.setRegistroMedico(null);
			medicoService.createMedico(newEntity);
		});
	}
    
}
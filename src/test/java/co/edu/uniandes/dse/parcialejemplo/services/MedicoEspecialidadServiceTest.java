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
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest 
{

	@Autowired
	private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

	private List<MedicoEntity> medicoList = new ArrayList<>();
	private List<EspecialidadEntity> especialidadList = new ArrayList<>();



    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) 
		{
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
			especialidadEntity.setDescripcion(String.valueOf(i).repeat(11));
			entityManager.persist(especialidadEntity);
			especialidadList.add(especialidadEntity);
		}

		for (int i = 0; i < 3; i++) 
		{
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			medicoEntity.setRegistroMedico("RM" + String.valueOf(i));
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}

    //Prueba de crear un medico

    @Test
	void testAddEspecialidad() throws EntityNotFoundException 
	{

		EspecialidadEntity especialidad = especialidadList.get(0);
		MedicoEntity medico = medicoList.get(0);
		MedicoEntity result = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());
		assertNotNull(result);

		EspecialidadEntity epecialidadResult = result.getEspecialidades().get(0);
		assertNotNull(epecialidadResult);

		assertEquals(especialidad.getId(), epecialidadResult.getId());
		assertEquals(especialidad.getNombre(), epecialidadResult.getNombre());
		assertEquals(especialidad.getDescripcion(), epecialidadResult.getDescripcion());

	}

	@Test
	void testAddInvalidEspecialidad() 
	{
		assertThrows(EntityNotFoundException.class, ()->{
			MedicoEntity entity = medicoList.get(0);
			medicoEspecialidadService.addEspecialidad(entity.getId(),0L);
		});
	}

	@Test
	void testAddInvalidEspecialidad2() 
	{
		assertThrows(EntityNotFoundException.class, ()->{
			EspecialidadEntity entity = especialidadList.get(0);
			medicoEspecialidadService.addEspecialidad(0L,entity.getId());
		});
	}

    
    


    
}

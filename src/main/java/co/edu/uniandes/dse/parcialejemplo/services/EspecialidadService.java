package co.edu.uniandes.dse.parcialejemplo.services;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository; 

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidad) throws IllegalOperationException
    {
        log.info("Inicia el proceso de crear una especialidad");

        if (especialidad.getDescripcion() == null || especialidad.getDescripcion().length() < 10)
        {
            throw new IllegalOperationException("Invalid descripcion");
        }
        return especialidadRepository.save(especialidad);
    }

    
}

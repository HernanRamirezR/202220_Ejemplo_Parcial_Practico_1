package co.edu.uniandes.dse.parcialejemplo.services;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadMedicoService {

    
    
	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException
    {
        log.info("Inicia el proceso de añadir un medico a una especialidad");
        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("El medico con el Id ingresado no fue encontrado");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("La especialidad con el Id ingresado no fue encontrado");

        especialidadEntity.get().getMedicos().add(medicoEntity.get());
        log.info("Termina proceso de asociarle un medico a la especialidad con id = {0}", especialidadId);
        return especialidadEntity.get();
    }
}

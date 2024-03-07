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
public class MedicoEspecialidadService {

    
    
	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private EspecialidadRepository especialidadRepository;

    @Transactional
    public MedicoEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException
    {
        log.info("Inicia el proceso de añadir una especialidad a un medico");
        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException("El medico con el Id ingresado no fue encontrado");

		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException("La especialidad con el Id ingresado no fue encontrado");

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        log.info("Termina proceso de asociarle una especialidad al medico con id = {0}", medicoId);
        return medicoEntity.get();
    }
}

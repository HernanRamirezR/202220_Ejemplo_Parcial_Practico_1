package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {


    @Autowired
	private MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedico(MedicoEntity medico) throws IllegalOperationException
    {
        log.info("Inicia el proceso de crear un medico");

        if (medico.getRegistroMedico() == null || !medico.getRegistroMedico().startsWith("RM") || medico.getRegistroMedico().equals("") )
        {
            throw new IllegalOperationException("Invalid registro medico");
        }
        return medicoRepository.save(medico);
    }
    
}

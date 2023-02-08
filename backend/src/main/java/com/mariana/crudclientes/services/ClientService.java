package com.mariana.crudclientes.services;

import com.mariana.crudclientes.dto.ClientDTO;
import com.mariana.crudclientes.entitie.Client;
import com.mariana.crudclientes.repositories.ClientRepository;
import com.mariana.crudclientes.services.exceptions.ResouceNotFounException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
            Optional<Client> result = repository.findById(id);
            Client client = result.orElseThrow(
                    ()-> new ResouceNotFounException("Não existe o cliente"));
            ClientDTO dto = new ClientDTO(client);
            return dto;

        }


    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<Client> result = repository.findAll(pageable);
        return result.map(x -> new ClientDTO(x));
    }
    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        copyDtoToEntity(dto,entity);
        entity = repository.save(entity);
        return new ClientDTO(entity);

    }
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto,entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResouceNotFounException("Id não encontrado");
        }
    }
    @Transactional
    public void delete(Long id){
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResouceNotFounException("impossivel deletar");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }

}

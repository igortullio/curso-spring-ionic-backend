package com.igortullio.cursomc.services;

import com.igortullio.cursomc.domain.Cliente;
import com.igortullio.cursomc.domain.Cliente;
import com.igortullio.cursomc.dto.ClienteDTO;
import com.igortullio.cursomc.repositories.ClienteRepository;
import com.igortullio.cursomc.services.exceptions.DataIntegrityException;
import com.igortullio.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente find(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update(Cliente cliente){
        Cliente clienteNovo = find(cliente.getId());
        updateData(clienteNovo, cliente);
        return clienteRepository.save(clienteNovo);
    }

    public void delete(Integer id){
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
        }
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    private void updateData(Cliente clienteNovo, Cliente cliente){
        clienteNovo.setNome(cliente.getNome());
        clienteNovo.setEmail(cliente.getEmail());
    }

}

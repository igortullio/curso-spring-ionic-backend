package com.igortullio.cursomc.services;

import com.igortullio.cursomc.domain.Cidade;
import com.igortullio.cursomc.domain.Cliente;
import com.igortullio.cursomc.domain.Endereco;
import com.igortullio.cursomc.domain.Enums.Perfil;
import com.igortullio.cursomc.domain.Enums.TipoCliente;
import com.igortullio.cursomc.dto.ClienteDTO;
import com.igortullio.cursomc.dto.ClienteNewDTO;
import com.igortullio.cursomc.repositories.ClienteRepository;
import com.igortullio.cursomc.repositories.EnderecoRepository;
import com.igortullio.cursomc.security.UserSS;
import com.igortullio.cursomc.services.exceptions.AuthorizationException;
import com.igortullio.cursomc.services.exceptions.DataIntegrityException;
import com.igortullio.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private S3Service s3Service;

    public Cliente find(Integer id){

        UserSS userSS = UserService.authenticated();
        if (userSS == null || !userSS.hasRole(Perfil.ADMIN) && !id.equals(userSS.getId())){
            throw new AuthorizationException("Acesso Negado");
        }

        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente cliente){
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
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
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO clienteNewDTO){
        Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()), bCryptPasswordEncoder.encode(clienteNewDTO.getSenha()));
        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDTO.getTelefone1());
        if (clienteNewDTO.getTelefone2() != null){
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        }
        if (clienteNewDTO.getTelefone3() != null){
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        }
        return cliente;
    }

    private void updateData(Cliente clienteNovo, Cliente cliente){
        clienteNovo.setNome(cliente.getNome());
        clienteNovo.setEmail(cliente.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        return s3Service.uploadFile(multipartFile);
    }

}

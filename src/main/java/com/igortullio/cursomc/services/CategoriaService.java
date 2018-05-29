package com.igortullio.cursomc.services;

import com.igortullio.cursomc.domain.Categoria;
import com.igortullio.cursomc.dto.CategoriaDTO;
import com.igortullio.cursomc.services.exceptions.DataIntegrityException;
import com.igortullio.cursomc.services.exceptions.ObjectNotFoundException;
import com.igortullio.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria){
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria){
        Categoria categoriaNova = find(categoria.getId());
        updateData(categoriaNova, categoria);
        return categoriaRepository.save(categoriaNova);
    }

    public void delete(Integer id){
        find(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateData(Categoria categoriaNova, Categoria categoria){
        categoriaNova.setNome(categoria.getNome());
    }

}

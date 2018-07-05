package com.igortullio.cursomc.resources;

import com.igortullio.cursomc.domain.Cidade;
import com.igortullio.cursomc.domain.Estado;
import com.igortullio.cursomc.dto.CidadeDTO;
import com.igortullio.cursomc.dto.EstadoDTO;
import com.igortullio.cursomc.services.CidadeService;
import com.igortullio.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> estados = estadoService.findAll();
        List<EstadoDTO> estadosDTO = estados.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadosDTO);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
        List<Cidade> cidades = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> cidadeDTO = cidades.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadeDTO);
    }

}

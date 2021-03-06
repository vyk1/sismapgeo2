package com.pbi.map.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pbi.map.entity.MarkersEntity;
import com.pbi.map.service.MarkersService;

@RestController
@RequestMapping(value = "/markers")
public class MarkersResource {

	@Autowired
	MarkersService service;

	@RequestMapping(method = RequestMethod.GET)
	public List<MarkersEntity> listardto() {
		List<MarkersEntity> listaMarkerss = service.buscar();
//		List<MarkersDTO> listaDTO = listaMarkerss.stream().map(obj -> new MarkersDTO(obj)).collect(Collectors.toList());
		return listaMarkerss;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<MarkersEntity> buscar(@PathVariable Integer id) {
		MarkersEntity Markers = service.buscar(id);
		return ResponseEntity.ok(Markers);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@Valid @RequestBody MarkersEntity obj) {

		MarkersEntity obj2 = new MarkersEntity(null, obj.getLatitude(), obj.getLongitude(), obj.getDescricao(),
				obj.getTitulo());

		obj2 = service.salvar(obj2);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj2.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody MarkersEntity obj, @PathVariable Integer id) {

		MarkersEntity obj2 = new MarkersEntity(id, obj.getLatitude(), obj.getLongitude(), obj.getDescricao(),
				obj.getTitulo());
		obj2 = service.atualizar(obj2);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.apagar(id);
		return ResponseEntity.noContent().build();
	}
}
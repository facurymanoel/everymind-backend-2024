package com.everymind.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.everymind.dto.ProdutoDTO;
import com.everymind.model.Produto;
import com.everymind.repository.ProdutoRepository;
import com.everymind.service.ProdutoService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private ProdutoRepository produtoRepository;
	
	private ProdutoService produtoService;

	@GetMapping
	public List<ProdutoDTO> findAll() {

		return produtoService.findAll();

	}

	@GetMapping("/{id}")
	public ProdutoDTO findByID(@PathVariable Long id) {

		return produtoService.findById(id);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO created(@Valid @RequestBody ProdutoDTO produtoDTO) {

		return produtoService.create(produtoDTO);
	}

	@PutMapping("/{id}")
	public ProdutoDTO update(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {

		return produtoService.update(id, produtoDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {

		produtoService.delete(id);
	}
	
	@GetMapping("/produtoPorNome/{nome}")
	@CachePut("cacheusuarios")
	public ResponseEntity<List<Produto>> produtoPorNome(@PathVariable("nome") String nome)
			throws InterruptedException {

		List<Produto> list = (List<Produto>) produtoRepository.findUserByNome(nome);

		return new ResponseEntity<List<Produto>>(list, HttpStatus.OK);

	}

}

package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Tag;
import com.algaworks.brewer.repository.Tags;
import com.algaworks.brewer.repository.filter.TagFilter;
import com.algaworks.brewer.service.CadastroTagService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeTagJaCadastradoException;

@Controller
@RequestMapping("/mecanicas/tags")
public class TagsController {

	@Autowired
	private CadastroTagService cadastroTagService;
	
	@Autowired
	private Tags tags;
	
	@RequestMapping("/nova")
	public ModelAndView nova(Tag tag) {
		return new ModelAndView("tag/CadastroTag");
	}
	
	@PostMapping(value = { "/nova", "{\\d+}" })
	public ModelAndView cadastrar(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(tag);
		}
		
		try {
			cadastroTagService.salvar(tag);
		} catch (NomeTagJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(tag);
		}
		
		attributes.addFlashAttribute("mensagem", "Tag salvo com sucesso");
		return new ModelAndView("redirect:/tags/nova");
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Tag tag, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		tag = cadastroTagService.salvar(tag);
		return ResponseEntity.ok(tag);
	}
	
	@GetMapping
	public ModelAndView pesquisar(TagFilter tagFilter, BindingResult result
			, @PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) 
	{
		ModelAndView mv = new ModelAndView("tag/PesquisaTags");
		
		PageWrapper<Tag> paginaWrapper = new PageWrapper<>(tags.filtrar(tagFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		mv.addObject("tags", tags.findAll());
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Tag tag) {
		try {
			cadastroTagService.excluir(tag);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Tag tag) {
		ModelAndView mv = nova(tag);
		mv.addObject(tag);
		return mv;
	}
}

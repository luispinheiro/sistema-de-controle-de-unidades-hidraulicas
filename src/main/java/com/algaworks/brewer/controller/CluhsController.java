package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cluh;
import com.algaworks.brewer.model.Condicao;
import com.algaworks.brewer.repository.Cluhs;
import com.algaworks.brewer.repository.Tags;
import com.algaworks.brewer.repository.filter.CluhFilter;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroCluhService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/mecanicas/cluhs")
public class CluhsController {

	@Autowired
	private Tags tags;
	
	@Autowired
	private CadastroCluhService cadastroCluhService;
	
	@Autowired
	private Cluhs cluhs;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cluh cluh) {
		ModelAndView mv = new ModelAndView("mecanica/CadastroCluh");
		mv.addObject("condicoes", Condicao.values());
		mv.addObject("tags", tags.findAll());
		return mv;
	}
	
	@PostMapping(value = "/novo")
	public ModelAndView cadastrar(@Valid Cluh cluh, BindingResult result, Model model, RedirectAttributes attributes
			, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		if (result.hasErrors()) {
			return novo(cluh);
		}
		
		cluh.setUsuario(usuarioSistema.getUsuario());
		
		cadastroCluhService.salvar(cluh);
		attributes.addFlashAttribute("mensagem", "Checklist salvo com sucesso!");
		return new ModelAndView("redirect:/mecanicas/cluhs/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(CluhFilter cluhFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("mecanica/PesquisaCluhs");
		mv.addObject("condicoes", Condicao.values());
		mv.addObject("tags", tags.findAll());

		PageWrapper<Cluh> paginaWrapper = new PageWrapper<>(cluhs.filtrar(cluhFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Cluh cluh) {
		ModelAndView mv = novo(cluh);
		mv.addObject(cluh);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Cluh cluh) {
		try {
			cadastroCluhService.excluir(cluh);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}

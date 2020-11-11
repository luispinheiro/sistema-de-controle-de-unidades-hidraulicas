var Brewer = Brewer || {};

Brewer.TagCadastroRapido = (function() {
	
	function TagCadastroRapido() {
		this.modal = $('#modalCadastroRapidoTag');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-tag-salvar-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeTag = $('#nomeTag');
		this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-tag');
	}
	
	TagCadastroRapido.prototype.iniciar = function() {
		this.form.on('submit', function(event) { event.preventDefault() });
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this))
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
	}
	
	function onModalShow() {
		this.inputNomeTag.focus();
	}
	
	function onModalClose() {
		this.inputNomeTag.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeTag = this.inputNomeTag.val().trim();
		$.ajax({
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeTag }),
			error: onErroSalvandoTag.bind(this),
			success: onTagSalvo.bind(this)
		});
	}
	
	function onErroSalvandoTag(obj) {
		var mensagemErro = obj.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	function onTagSalvo(tag) {
		var comboTag = $('#tag');
		comboTag.append('<option value=' + tag.codigo + '>' + tag.nome + '</option>');
		comboTag.val(tag.codigo);
		this.modal.modal('hide');
	}
	
	return TagCadastroRapido;
	
}());

$(function() {
	var tagCadastroRapido = new Brewer.TagCadastroRapido();
	tagCadastroRapido.iniciar();
});

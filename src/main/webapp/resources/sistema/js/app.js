$(function() {
	$('.js-toggle').bind('click', function(event) {
		$('.js-sidebar, .js-content').toggleClass('is-toggled');
		event.preventDefault();
	});
});

(function($) {
	$(document).ready(function() {
		$('.aw-menu > ul > li > a').click(function() {
			
			/*(this) nessa function é o primeiro href*/
			
			/*Remove a classe active de todas li*/
			$('.aw-menu li').removeClass('active');
			
			/* Adiciona a classe active no primeiro li anterior ao (this)*/
			$(this).closest('li').addClass('active');
			
			/*Captura o próximo elemento após (this) que se tiver sub-menu será um ul*/
			var checkElement = $(this).next();
			
			/*Verifica se o elemento encontrado é uma ul e verifica se está visivel*/
			if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
				/* Renome a classe active no primeiro li anterior ao (this)*/
				$(this).closest('li').removeClass('active');
				/*Aplica o slideUp (ou seja FECHAR) na velocidade normal a ul*/
				checkElement.slideUp('normal');
			}
			
			/*Verifica se o elemento encontrado é uma ul e verifica se NÃO está visivel*/
			if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
				/*Aplica o slideUp (ou seja FECHAR) na velocidade normal a todos elementos .aw-menu ul ul:visible*/
				$('.aw-menu ul ul:visible').slideUp('normal');
				/*Aplica o slideDown (ou seja ABRIR) na velocidade normal a ul*/
				checkElement.slideDown('normal');
			}
			/*Ao primeiro li anterior ao (this) procura o proxima ul e vefifica quantos elementos filhos tem.*/
			if ($(this).closest('li').find('ul').children().length == 0) {
				return true;
			} else {
				return false;
			}
		});
	});
})(jQuery);

/*Function que mantem o estado ativo do menu ao carregar uma url do menu ou sub menu*/
$(document).ready(function() {
	/*Captura o path da url*/
	var pgurl = window.location.pathname;
	/*Vai até o primeiro href e compara com a o path e aplica a classe active a li*/
	$('.aw-menu > ul > li > a[href="'+pgurl+'"]').parent().addClass('active');
	/*Vai até o segundo (sub-menu) href e compara com a o path e aplica a classe active primeira a li*/
	$('.aw-menu > ul > li > ul > li > a[href="'+pgurl+'"]').parent().parent().parent().addClass('active').find("ul").show(0);
})


/*Mascara CPF ou CNPJ no mesmo input*/
function mascaraCpfCnpj(o,f){
    v_obj=o
    v_fun=f
    setTimeout('execmascara()',1)
}
 
function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}
 
function cpfCnpj(v){
    //Remove tudo o que não é dígito
    v=v.replace(/\D/g,"")
    if (v.length <= 11) { //CPF
        //Coloca um ponto entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d)/,"$1.$2")
 
        //Coloca um ponto entre o terceiro e o quarto dígitos
        //de novo (para o segundo bloco de números)
        v=v.replace(/(\d{3})(\d)/,"$1.$2")
 
        //Coloca um hífen entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2")
 
    } else if((v.length > 11) && (v.length <= 14)){ //CNPJ
 
        //Coloca ponto entre o segundo e o terceiro dígitos
        v=v.replace(/^(\d{2})(\d)/,"$1.$2")
 
        //Coloca ponto entre o quinto e o sexto dígitos
        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")
 
        //Coloca uma barra entre o oitavo e o nono dígitos
        v=v.replace(/\.(\d{3})(\d)/,".$1/$2")
 
        //Coloca um hífen depois do bloco de quatro dígitos
        v=v.replace(/(\d{4})(\d)/,"$1-$2")
    }else if(v.length > 14){
    	//Remove todos caracteres depois do 18º
        v=v.replace(v,v.substring(0,14))
        v=v.replace(/^(\d{2})(\d)/,"$1.$2")
        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")
        v=v.replace(/\.(\d{3})(\d)/,".$1/$2")
        v=v.replace(/(\d{4})(\d)/,"$1-$2")
    }
    return v
}

function reloadPagina() {
    window.parent.location = window.parent.location.href;
}
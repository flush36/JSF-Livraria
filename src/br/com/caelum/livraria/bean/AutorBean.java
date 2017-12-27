package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.manager.Transaction;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	@Inject
	private AutorDao dao;
	
	private Integer autorId;
	
	@Inject
	private FacesContext context;
	
	@Inject
	private EntityManager manager;
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
		this.autor = this.dao.buscaPorId(autorId);
	}

	@Transaction
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}

		this.autor = new Autor();

		return "livro?faces-redirect=true";
	}
	
	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
		try {
			
			manager.getTransaction().begin();
			this.dao.remove(autor);
			manager.getTransaction().commit();
			
		}catch (Exception e) {
			context.addMessage(null, new FacesMessage("Autor não pode ser apagado, favor excluir os livros do autor primeiro."));
		}
	}
	
	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}

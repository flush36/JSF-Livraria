package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.VendaLivros;

@Named
@ViewScoped
public class VendaLivrosBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LivroDao livroDao;

	public BarChartModel getVendasModel(){
		
	        BarChartModel model = new BarChartModel();
	        model.setAnimate(true);
	        ChartSeries vendaSerie2017 = new ChartSeries();
	        vendaSerie2017.setLabel("Vendas 2017");
	        
	        List<VendaLivros> vendas = getVendas(1234);
	        for(VendaLivros venda : vendas) {
	        	vendaSerie2017.set(venda.getLivro().getTitulo(), venda.getQuantidade());
	        }
	        
	        ChartSeries vendaSerie2018 = new ChartSeries();
	        vendaSerie2018.setLabel("Vendas 2017");
	        
	         vendas = getVendas(4321);
	        for(VendaLivros venda : vendas) {
	        	vendaSerie2018.set(venda.getLivro().getTitulo(), venda.getQuantidade());
	        }
	        
	        model.addSeries(vendaSerie2017);
	        model.addSeries(vendaSerie2018);
	        
	        return model;
	    }
	
	public List<VendaLivros> getVendas(long seed){
		
		List<Livro> livros = livroDao.listaTodos();
		List<VendaLivros> vendas = new ArrayList<VendaLivros>();
		
		Random random = new Random(seed);
		
		for (Livro livro : livros) {
			Integer quantidade = random.nextInt(500);
			vendas.add(new VendaLivros(livro, quantidade));
		}
		return vendas;
	}
}

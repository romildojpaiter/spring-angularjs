package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class DummyCarroRepository implements CarroRepository {

	private Map<Integer, Carro> carros = new ConcurrentHashMap<Integer, Carro>();
	
	@Override
	public Carro findById(Integer id) {
		return this.carros.get(id);
	}

	@Override
	public List<Carro> findAll() {
		List<Carro> carrosLista = new ArrayList<Carro>(carros.values());
		Collections.sort(carrosLista, new Comparator<Carro>() {
			@Override
			public int compare(Carro carro1, Carro carro2) {
				return carro1.getId() - carro2.getId();
			}
		});
		return carrosLista;
	}

	@Override
	public Carro save(Carro carro) {
		if(carro.getId() == null){
			carro.setId(nextId());
		}
		this.carros.put(carro.getId(), carro);
		return carro;
	}

	private Integer nextId() {
		if(this.carros.isEmpty()){
			return 1;
		}
		// return this.carros.size()+1;
		return Collections.max(this.carros.keySet()) + 1; 
	}

	@Override
	public void delete(Integer id) {
		this.carros.remove(id);
	}

}

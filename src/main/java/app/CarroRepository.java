package app;

import java.util.List;

public interface CarroRepository {
	
	
	  Carro findById(Integer id);

	  List<Carro> findAll();

	  Carro save(Carro carro);

	  void delete(Integer id);	

}

package app;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

@Controller
@RequestMapping("/carros")
public class CarroController {
	
	@Autowired
	CarroRepository carroRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	  public @ResponseBody List<Carro> list() {
	    return this.carroRepository.findAll();
	  }

	  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	  public @ResponseBody Carro find(@PathVariable("id") Integer id) {
	    Carro carro = this.carroRepository.findById(id);
	    if (carro == null) {
	      throw new CarroNotFoundException(id);
	    }
	    return carro;
	  }

	  @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
	  @ResponseStatus(HttpStatus.CREATED)
	  public HttpEntity<?> create(@RequestBody Carro carro, @Value("#{request.requestURL}") StringBuffer parentUri) {
	    carro = this.carroRepository.save(carro);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(childLocation(parentUri, carro.getId()));
	    return new HttpEntity<Object>(headers);
	  }

	  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	  @ResponseStatus(HttpStatus.NO_CONTENT)
	  public void delete(@PathVariable("id") Integer id) {
	    this.carroRepository.delete(id);
	  }

	  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	  @ResponseStatus(HttpStatus.NO_CONTENT)
	  public void update(@PathVariable("id") Integer id, @RequestBody Carro carro) {
	    carro.setId(id);
	    this.carroRepository.save(carro);
	  }


	  private URI childLocation(StringBuffer parentUri, Object childId) {
	    UriTemplate uri = new UriTemplate(parentUri.append("/{childId}").toString());
	    return uri.expand(childId);
	  }

	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public class CarroNotFoundException extends RuntimeException {
	    public CarroNotFoundException(Integer id) {
	      super("Carro '" + id + "' not found.");
	    }
	  }	

}

package rest;


import domain.Proba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.JdbcRepositoryProba;

//@CrossOrigin
@RestController
@RequestMapping("/probe")
public class ProbaControllerRest {

    ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-server.xml");

    private JdbcRepositoryProba probeRepo=(JdbcRepositoryProba) factory.getBean("repoProbe");

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<Proba> getAll(){
        System.out.println("Get all probe..");
        return probeRepo.findAll();
    }


    @RequestMapping(value = "/{id}",method=RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        int id1 = Integer.parseInt(id);
        System.out.println("Get by id"+id);
        Proba proba = probeRepo.findOne(id1);
        if(proba==null)
            return new ResponseEntity<String>("Proba not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(proba, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba proba){
        return probeRepo.update(proba.getId(),proba);


    }


    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("Deleting proba ... "+id);
        int id1 = Integer.parseInt(id);
        try {
            probeRepo.delete(id1);
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Delete proba exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/{id}/arbitru")
    public String name(@PathVariable String id){
        int id1 = Integer.parseInt(id);
        Proba result= probeRepo.findOne(id1);
        System.out.println("Result ..."+result);

        return result.getNumeArbitru();
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }

}

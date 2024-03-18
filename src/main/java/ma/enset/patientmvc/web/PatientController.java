package ma.enset.patientmvc.web;

import lombok.AllArgsConstructor;
import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    PatientRepository patientRepository;

  /*  @GetMapping(path = "/index")
    public String patients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patients";
    }*/

    @GetMapping(path = "/index")
    public String patients(Model model,
                          @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword){
        //Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page, size));
        Page<Patient> pagePatients = patientRepository.findByNameContains(keyword,PageRequest.of(page, size));
        model.addAttribute("patients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping(path = "/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping(path = "/delete")
    public String delete(Long id, String keyword, int page, int size){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&size="+size+"&keyword="+keyword;
    }

    @GetMapping(path = "/patients")
    @ResponseBody
    public List<Patient> patients() {
        return patientRepository.findAll();
    }
}

package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    PetRepository petRepository;

    @RequestMapping("/homepage")
    public String homepage()
    {
        return "homepage";
    }

    @RequestMapping("/")
    public String listPets(Model model)
    {
        model.addAttribute("pets", petRepository.findAll());
        return "list";
    }


    @GetMapping("/add")
    public String petForm(Model model)
    {
        model.addAttribute("aPet", new Pet());
        return "petForm";
    }

    @PostMapping("/savepet")
    public String savePet(@Valid Pet pet, BindingResult result)
    {

        if (result.hasErrors())
        {
            return "petForm";
        }
        petRepository.save(pet);
        return "redirect:/";
    }

    @RequestMapping("/changestatus/{id}")
    public String notAvailable(@PathVariable("id")long id)
    {
        Pet thisPet = petRepository.findPetById(id);
        thisPet.setFound(!thisPet.isFound());
        petRepository.save(thisPet);
        return "redirect:/";
    }


    @RequestMapping("/detail/{id}")
    public String showPet (@PathVariable("id") long id, Model model)
    {
        /* bookRepository.findById(id).get()*/
        model.addAttribute("aPet", petRepository.findPetById(id));
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updatePet (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("aPet", petRepository.findPetById(id));
        return "petForm";
    }


    @RequestMapping("/delete/{id}")
    public String delPet(@PathVariable("id") long id, Model model)
    {
        petRepository.deleteById(id);
        return "redirect:/";
    }
}

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
    CarRepository carRepository;

    @RequestMapping("/")
    public String listCars(Model model)
    {
        model.addAttribute("cars", carRepository.findAll());
        return "list";
    }


    @GetMapping("/add")
    public String carForm(Model model)
    {
        model.addAttribute("aCar", new Car());
        return "carForm";
    }

    @PostMapping("/savecar")
    public String saveCar(@Valid Car car, BindingResult result)
    {

        if (result.hasErrors())
        {
            return "carForm";
        }
        carRepository.save(car);
        return "redirect:/";
    }

    @RequestMapping("/changestatus/{id}")
    public String notAvailable(@PathVariable("id")long id)
    {
        Car thisCar = carRepository.findCarById(id);
        thisCar.setAvailable(!thisCar.isAvailable());
        carRepository.save(thisCar);
        return "redirect:/";
    }


    @RequestMapping("/detail/{id}")
    public String showCar (@PathVariable("id") long id, Model model)
    {
        /* bookRepository.findById(id).get()*/
        model.addAttribute("aCar", carRepository.findCarById(id));
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateCar (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("aCar", carRepository.findCarById(id));
        return "carForm";
    }


    @RequestMapping("/delete/{id}")
    public String delCar(@PathVariable("id") long id, Model model)
    {
        carRepository.deleteById(id);
        return "redirect:/";
    }
}

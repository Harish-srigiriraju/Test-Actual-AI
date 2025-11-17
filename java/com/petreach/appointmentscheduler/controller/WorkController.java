package com.petreach.appointmentscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.petreach.appointmentscheduler.entity.Work;
import com.petreach.appointmentscheduler.service.WorkService;

@Controller
@RequestMapping("/works")
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping("/all")
    public String showAllWorks(Model model) {
        model.addAttribute("works", workService.getAllWorks());
        return "works/list";
    }

    @GetMapping("/{workId}")
    public String showFormForUpdateWork(@PathVariable("workId") int workId, Model model) {
        model.addAttribute("work", workService.getWorkById(workId));
        return "works/createOrUpdateWorkForm";
    }

    @GetMapping("/new")
    public String showFormForAddWork(Model model) {
        model.addAttribute("work", new Work());
        return "works/createOrUpdateWorkForm";
    }

    @PostMapping("/new")
    public String saveWork(@ModelAttribute("work") Work work) {
        if (work.getId() != null) {
            workService.updateWork(work);
        } else {
            workService.createNewWork(work);
        }
        return "redirect:/works/all";
    }

    @PostMapping("/delete")
    public String deleteWork(@RequestParam("workId") int workId) {
        workService.deleteWorkById(workId);
        return "redirect:/works/all";
    }
}

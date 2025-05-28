package com.example.resumebuilder.controller;

import com.example.resumebuilder.model.Resume;
import com.example.resumebuilder.repository.ResumeRepository;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ResumeController {

    @Autowired
    private ResumeRepository resumeRepository;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("resume", new Resume());
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute Resume resume) {
        resumeRepository.save(resume);
        return "redirect:/pdf/" + resume.getId();
    }

    @GetMapping("/pdf/{id}")
    public void generatePDF(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Resume resume = resumeRepository.findById(id).orElse(null);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=resume.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Name: " + resume.getFullName()));
        document.add(new Paragraph("Email: " + resume.getEmail()));
        document.add(new Paragraph("Phone: " + resume.getPhone()));
        document.add(new Paragraph("Education: " + resume.getEducation()));
        document.add(new Paragraph("Experience: " + resume.getExperience()));
        document.add(new Paragraph("Skills: " + resume.getSkills()));

        document.close();
    }
}

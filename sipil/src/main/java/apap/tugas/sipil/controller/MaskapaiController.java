package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.security.PrivateKey;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
@Controller
public class MaskapaiController {
    @Qualifier("maskapaiServiceImpl")
    @Autowired
    private MaskapaiService maskapaiService;


    @GetMapping("/maskapai/viewall")
    public String viewAll(Model model){
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        model.addAttribute("listMaskapai", listMaskapai);
        return "viewall-maskapai";
    }

    @GetMapping("/maskapai/add")
    public String addMaskapai(Model model){
        model.addAttribute("maskapai", new MaskapaiModel());
        return "form-add-maskapai";
    }

    @PostMapping("/maskapai/add")
    public String addMaskapaiSubmit(
            @ModelAttribute MaskapaiModel maskapai,
            Model model
    ){
        maskapaiService.addMaskapai(maskapai);
        String teks = "Maskapai Berhasil Ditambahkan";
        model.addAttribute("nama", maskapai.getNama());
        model.addAttribute("teks", teks);
        return "form-add-maskapai";
    }

    @GetMapping("/maskapai/change/{idMaskapai}")
    public String changeMaskapaiFormPage(
            @PathVariable Long idMaskapai,
            Model model){
        MaskapaiModel maskapai = maskapaiService.getMaskapaiById(idMaskapai);
        model.addAttribute("maskapai",maskapai);
        return "form-update-maskapai";
    }

    @PostMapping("/maskapai/change")
    public String changeMaskapaiFormSubmit(
            @ModelAttribute MaskapaiModel maskapai,
            Model model){
        MaskapaiModel target = maskapaiService.updateMaskapai(maskapai);
        model.addAttribute("maskapai",target);
        String teks = "Maskapai Berhasil Di Update";
        model.addAttribute("teks", teks);
        return "form-update-Maskapai";
    }
}

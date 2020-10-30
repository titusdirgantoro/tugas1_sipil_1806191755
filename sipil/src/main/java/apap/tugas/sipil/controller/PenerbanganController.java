package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class PenerbanganController {
    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

    @Autowired
    private PilotService pilotService;

    @Autowired
    private Pilot_PenerbanganService pilot_penerbanganService;

    @GetMapping("/")
    private String home( Model model){
//        model.addAttribute("standardDate", new Date());
        return "home";
    }

    @GetMapping("/penerbangan")
    public String viewAll(Model model){
        List<PenerbanganModel> listPenerbangan =penerbanganService.getListPenerbangan();
        model.addAttribute("listPenerbangan",listPenerbangan);
        return "viewall-penerbangan";
    }

    @GetMapping("/penerbangan/detail/{id}")
    public String viewDetailPenerbangan(
            @PathVariable(value = "id") Long id,
            Model model){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        List<PilotModel> listPilot = pilot_penerbanganService.getDaftarPilot(penerbangan);
        List<Pilot_PenerbanganModel> listPilotPenerbangan = pilot_penerbanganService.getPilotPenerbanganByPenerbangan(penerbangan);
        List<PilotModel> listPilot1 = pilotService.getListPilot();
        model.addAttribute("listPilot",listPilot);
        model.addAttribute("listPilot1",listPilot1);
        model.addAttribute("listPilotPenerbangan",listPilotPenerbangan);
        model.addAttribute("penerbangan",penerbangan);
        model.addAttribute("standardDate", new Date());
        return "view-penerbangan";
    }

    @GetMapping("/penerbangan/tambah")
    public String addPenerbangan(Model model){
        model.addAttribute("penerbangan", new PenerbanganModel());
        return "form-add-penerbangan";
    }

    @PostMapping("penerbangan/tambah")
    public String addPenerbanganSubmit(
        @ModelAttribute PenerbanganModel penerbangan,
        Model model
    ){
        penerbanganService.addPenerbangan(penerbangan);
        String teks = "Penerbangan dengan kode penerbangan "+ penerbangan.getKode() +" Berhasil Di Tambahkan";;
        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("teks", teks);
        return "notification";
    }

    @GetMapping("/penerbangan/ubah/{idPenerbangan}")
    public String changePenerbanganFormPage(
            @PathVariable Long idPenerbangan,
            Model model){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        model.addAttribute("penerbangan",penerbangan);
        return "form-update-penerbangan";
    }

    @PostMapping("/penerbangan/ubah/{idPenerbangan}")
    public String changePenerbanganFormSubmit(
            @ModelAttribute PenerbanganModel penerbangan,
            Model model){
        PenerbanganModel penerbanganUpdated = penerbanganService.updatePenerbangan(penerbangan);
        model.addAttribute("penerbangan",penerbanganUpdated);
        String teks = "Penerbangan dengan kode penerbangan "+ penerbangan.getKode() +" Berhasil Di Update";
        model.addAttribute("teks", teks);
        return "notification";
    }

    @RequestMapping("/penerbangan/hapus/{idPenerbangan}")
    public String deletePernarbangan(
            @PathVariable(value = "idPenerbangan") Long idPenerbangan,
            Model model){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        List<PilotModel> listPilot = pilot_penerbanganService.getDaftarPilot(penerbangan);
        String teks = "";
        if (listPilot.size() == 0) {
            model.addAttribute("idPenerbangan",idPenerbangan);
            penerbanganService.deletePenerbangan(penerbangan);
            teks = "Penerbangan dengan kode penerbangan "+ penerbangan.getKode() +" Berhasil Di Delete";
        }else{
            teks = "Penghapusan penerbangan dengan kode penerbangan "+ penerbangan.getKode() +" Dibatalkan karena masih terdapat pilot yang bertugas";
        }
        model.addAttribute("teks",teks);
        return "notification";
    }
}

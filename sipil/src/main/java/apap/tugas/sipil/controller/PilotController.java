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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PilotController {
    @Qualifier("pilotServiceImpl")
    @Autowired
    private PilotService pilotService;

    @Autowired
    private AkademiService akademiService;

    @Autowired
    private MaskapaiService maskapaiService;

    @Autowired
    private PenerbanganService penerbanganService;

    @Autowired
    private Pilot_PenerbanganService pilot_penerbanganService;


    @GetMapping("/pilot")
    public String viewAll(Model model){
        List<PilotModel> listPilot =pilotService.getListPilot();
        model.addAttribute("listPilot",listPilot);
        return "viewall-pilot";
    }

    @GetMapping("/pilot/{nipPilot}")
    public String viewDetailPilot(
            @PathVariable(value = "nipPilot") String nip,
            Model model){
        PilotModel pilot = pilotService.getPilotByNip(nip);
        model.addAttribute("pilot",pilot);
        List<PenerbanganModel> listPenerbangan = pilot_penerbanganService.getDaftarPenerbangan(pilot);
        MaskapaiModel maskapai = maskapaiService.getMaskapaiById(pilot.getMaskapai().getId());
        AkademiModel akademi = akademiService.getAkademiById(pilot.getMaskapai().getId());
        model.addAttribute("listPenerbangan",listPenerbangan);
        model.addAttribute("maskapai",maskapai);
        model.addAttribute("akademi",akademi);
        return "view-pilot";

    }
    @GetMapping("/pilot/tambah")
    public String addPilot(Model model){
        List<AkademiModel> listAkademi = akademiService.getListAkademi();
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        model.addAttribute("listAkademi", listAkademi);
        model.addAttribute("listMaskapai", listMaskapai);
        model.addAttribute("pilot", new PilotModel());
        return "form-add-pilot";
    }

    @GetMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormPage(
            @PathVariable String nipPilot,
            Model model){
        List<AkademiModel> listAkademi = akademiService.getListAkademi();
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        model.addAttribute("listAkademi", listAkademi);
        model.addAttribute("listMaskapai", listMaskapai);
        PilotModel pilot = pilotService.getPilotByNip(nipPilot);
        model.addAttribute("pilot",pilot);
        return "form-update-pilot";
    }

    @PostMapping("/pilot/ubah/{nipPilot}")
    public String changePilotFormSubmit(
            @ModelAttribute PilotModel pilot,
            Model model){
        PilotModel pilotUpdated = pilotService.updatePilot(pilot);
        pilotUpdated.setNip(pilotService.nipPilot(pilot));
        List<PilotModel> listPilot = pilotService.getListPilot();
        List<String> listNip = new ArrayList<String>();
        for(PilotModel temp : listPilot){
            listNip.add(temp.getNip());
        }
        if (listNip.size() != 0)
            if (listNip.contains(pilot.getNip())) pilotUpdated.setNip(pilotService.nipPilot(pilot));
        model.addAttribute("pilot",pilotUpdated);
        String teks = "Pilot dengan nip "+ pilotUpdated.getNip() +" Berhasil Di Update";
        model.addAttribute("teks", teks);
        return "notification";
    }

    @PostMapping("/pilot/tambah")
    public String addPilotSubmit(
            @ModelAttribute PilotModel pilot,
            Model model
    ){
        pilot.setNip(pilotService.nipPilot(pilot));
        List<PilotModel> listPilot = pilotService.getListPilot();
        List<String> listNip = new ArrayList<String>();
        for(PilotModel temp : listPilot){
            listNip.add(temp.getNip());
        }
        if (listNip.size() != 0)
        if (listNip.contains(pilot.getNip())) pilot.setNip(pilotService.nipPilot(pilot));
        pilotService.addPilot(pilot);
        String teks = "Pilot dengan NIP " +pilot.getNip() +" Berhasil Ditambahkan";
        model.addAttribute("pilot", pilot);
        model.addAttribute("teks", teks);
        return "notification";
    }

    @RequestMapping("/pilot/delete/{nip}")
    public String deletePilot(
            @PathVariable(value = "nip") String nip,
            Model model
    ){
        PilotModel pilot = pilotService.getPilotByNip(nip);
        model.addAttribute("pilot", pilot);
        pilotService.deletePilot(pilot);
        String teks = "Pilot dengan nip "+ pilot.getNip() +" Berhasil Di Delete";
        model.addAttribute("teks",teks);
        return "notification";

    }
    @GetMapping("/cari/pilot")
    public String cariPilot(
            Model model){
        long id = 0;
        model.addAttribute("id",id);
        int temp = 0;
        model.addAttribute("temp",temp);
        List<AkademiModel> listAkademi = akademiService.getListAkademi();
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        model.addAttribute("listAkademi", listAkademi);
        model.addAttribute("listMaskapai", listMaskapai);
        return "cari-pilot-maskapai-akademi";
    }

    @GetMapping("/cari/pilott")
    public String cariPilot1(
            @RequestParam(value = "kodeMaskapai") String kodeMaskapai,
            @RequestParam(value = "idSekolah") Long idSekolah,
            Model model){
        int temp = 1;
        model.addAttribute("temp",temp);
        MaskapaiModel maskapai = maskapaiService.getMaskapaiByKode(kodeMaskapai);
        if (idSekolah == null)
            idSekolah = Long.valueOf(0);
        AkademiModel akademi = akademiService.getAkademiById(idSekolah);
        List<PilotModel> listPilot = pilotService.getListPilotByMaskapaiAkademi(maskapai,akademi);
        List<Integer> jumlahPenerbangan = new ArrayList<>();
        if (listPilot != null)
        for (PilotModel pilot : listPilot){
            jumlahPenerbangan.add(pilot_penerbanganService.getDaftarPenerbangan(pilot).size());
        }
        List<AkademiModel> listAkademi = akademiService.getListAkademi();
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        long id = 0;
        model.addAttribute("id",id);
        model.addAttribute("listAkademi", listAkademi);
        model.addAttribute("listMaskapai", listMaskapai);
        model.addAttribute("listPilot", listPilot);
        model.addAttribute("jumlahPenerbangan",jumlahPenerbangan);

        return "cari-pilot-maskapai-akademi";
    }
    @GetMapping("/cari/pilot/penerbangan-terbanyakk")
    public String cariPilotPenerbanganTerbanyak(
            Model model){
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        model.addAttribute("listMaskapai", listMaskapai);
        return "cari-pilot-penerbangan-terbanyak";
    }

    @GetMapping("/cari/pilot/penerbangan-terbanyak")
    public String cariPilotPenerbanganTerbanyak1(
            @RequestParam(value = "kodeMaskapai") String kodeMaskapai,
            Model model){
        int temp = 1;
        model.addAttribute("temp",temp);
        MaskapaiModel maskapai = maskapaiService.getMaskapaiByKode(kodeMaskapai);
        List<Integer> jumlah = new ArrayList<>();
        List<PilotModel> listPilotUrut = pilotService.getListPilotByMaskapaiJmlPenerbangan(maskapai,jumlah);
        if (listPilotUrut.size() > 3) {
            jumlah.subList(3, jumlah.size()).clear();
            listPilotUrut.subList(3, listPilotUrut.size()).clear();
        }
        List<MaskapaiModel> listMaskapai = maskapaiService.getListMaskapai();
        long id = 0;
        model.addAttribute("id",id);
        model.addAttribute("listMaskapai", listMaskapai);
        model.addAttribute("listPilotUrut", listPilotUrut);
        model.addAttribute("jumlah",jumlah);
        return "cari-pilot-penerbangan-terbanyak";
    }

    @GetMapping("/cari/pilot/bulan-ini")
    public String cariPilotBulanIni(
            Model model
    ){
        List<PilotModel> listPilot = pilotService.getListPilot();
        List<PilotModel> listPilotBulanIni = pilotService.getListPilotBulanIni(listPilot);
        model.addAttribute("listPilotBulanIni",listPilotBulanIni);

        return "cari-pilot-bulan-ini";
    }

}

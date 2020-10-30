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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
@Controller
public class Pilot_PenerbanganController {
    @Qualifier("pilot_PenerbanganServiceImpl")
    @Autowired
    private Pilot_PenerbanganService pilot_penerbanganService;

    @Autowired
    private PilotService pilotService;

    @Autowired
    private PenerbanganService penerbanganService;

    @PostMapping("/penerbangan/{idPenerbangan}/pilot/tambah")
    public String addPilotPenerbangan(
            @ModelAttribute Pilot_PenerbanganModel pilot_penerbangan,
            Model model
    ){
        Pilot_PenerbanganModel pilotpenerbangan = pilot_penerbanganService.getPilotPenerbangan(pilot_penerbangan.getPenerbangan(),pilot_penerbangan.getPilot());
        String teks ="";
        PilotModel pilot = pilot_penerbangan.getPilot();
        PenerbanganModel penerbangan = pilot_penerbangan.getPenerbangan();
        if (pilotpenerbangan == null) {
            pilot_penerbanganService.addPilotPenerbangan(pilot_penerbangan);
            teks = "Pilot bernama " + pilot.getNama() + " Berhasil Di Tambahkan Pada Penerbangan " + penerbangan.getKode();
            model.addAttribute("teks", teks);
        }else{
            teks = "Pilot bernama " + pilot.getNama() + " Sudah Pernah Di Tambahkan Pada Penerbangan " + penerbangan.getKode();
            model.addAttribute("teks", teks);
        }
        return "notification";
    }
}

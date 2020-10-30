package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class Pilot_PenerbanganServiceImpl implements Pilot_PenerbanganService {
    @Autowired
    Pilot_PenerbanganDb pilot_penerbanganDb;

    @Autowired
    PilotDb pilotDb;

    @Autowired
    PenerbanganDb penerbanganDb;

    @Override
    public void addPilotPenerbangan(Pilot_PenerbanganModel pilot_penerbangan) {
        pilot_penerbanganDb.save(pilot_penerbangan);
    }

    @Override
    public List<Pilot_PenerbanganModel> getListPilotPenerbangan() {
        return pilot_penerbanganDb.findAll();
    }

    @Override
    public List<Pilot_PenerbanganModel> getPilotPenerbanganByPenerbangan(PenerbanganModel penerbangan) {
        return pilot_penerbanganDb.findAllByPenerbangan(penerbangan);
    }


    @Override
    public Pilot_PenerbanganModel getPilotPenerbangan(PenerbanganModel penerbangan,PilotModel pilot) {
        try {
            return pilot_penerbanganDb.findByPenerbanganAndPilot(penerbangan,pilot).get();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    public List<PilotModel> getDaftarPilot(PenerbanganModel penerbangan) {
        List<Pilot_PenerbanganModel> listpilot_penerbangan = pilot_penerbanganDb.findAllByPenerbangan(penerbangan);
        List<PilotModel> daftarPilot = new ArrayList<>();
        for (Pilot_PenerbanganModel pilot : listpilot_penerbangan){
            PilotModel pilot1 = pilotDb.findById(pilot.getPilot().getId()).get();
            daftarPilot.add(pilot1);
        }
        return daftarPilot;
    }

    @Override
    public List<PenerbanganModel> getDaftarPenerbangan(PilotModel pilot) {
        List<Pilot_PenerbanganModel> listpilot_penerbangan = pilot_penerbanganDb.findAllByPilot(pilot);
        List<PenerbanganModel> daftarPenerbangan = new ArrayList<>();
        for (Pilot_PenerbanganModel penerbangan : listpilot_penerbangan){
            PenerbanganModel penerbangan1 = penerbanganDb.findByKode(penerbangan.getPenerbangan().getKode()).get();
            daftarPenerbangan.add(penerbangan1);
        }
        return daftarPenerbangan;
    }

}

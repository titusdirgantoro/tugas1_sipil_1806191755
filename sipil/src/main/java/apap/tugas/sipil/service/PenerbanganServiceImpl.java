package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PenerbanganServiceImpl implements PenerbanganService{
    @Autowired
    PenerbanganDb penerbanganDb;

    @Override
    public void addPenerbangan(PenerbanganModel penerbangan) {
        penerbanganDb.save(penerbangan);
    }

    @Override
    public List<PenerbanganModel> getListPenerbangan() {
        return penerbanganDb.findAll();
    }

    @Override
    public PenerbanganModel getPenerbanganById(Long id) {
        return penerbanganDb.findById(id).get();
    }

    @Override
    public PenerbanganModel getPenerbanganByKode(String kode) {
        return penerbanganDb.findByKode(kode).get();
    }

    @Override
    public PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan) {
        PenerbanganModel target =penerbanganDb.findById(penerbangan.getId()).get();
        try {
            target.setKode(penerbangan.getKode());
            target.setKota_asal(penerbangan.getKota_asal());
            target.setKota_tujuan(penerbangan.getKota_tujuan());
            target.setWaktu(penerbangan.getWaktu());
            penerbanganDb.save(target);
            return target;
        }
        catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public void deletePenerbangan(PenerbanganModel penerbangan) {
        penerbanganDb.delete(penerbangan);
    }
}

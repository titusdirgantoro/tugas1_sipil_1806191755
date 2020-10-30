package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;

import java.util.List;

public interface PenerbanganService {
    void addPenerbangan(PenerbanganModel penerbangan);
    List<PenerbanganModel> getListPenerbangan();
    PenerbanganModel getPenerbanganById(Long id);
    PenerbanganModel getPenerbanganByKode (String kode);
    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);
    void deletePenerbangan(PenerbanganModel penerbangan);

}

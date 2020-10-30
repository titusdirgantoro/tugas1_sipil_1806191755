package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;

import java.util.List;

public interface Pilot_PenerbanganService {
    void addPilotPenerbangan(Pilot_PenerbanganModel pilot_penerbangan);
    List<Pilot_PenerbanganModel> getListPilotPenerbangan();
    List<Pilot_PenerbanganModel> getPilotPenerbanganByPenerbangan(PenerbanganModel penerbangan);
    Pilot_PenerbanganModel getPilotPenerbangan(PenerbanganModel penerbangan,PilotModel pilot);
    List<PilotModel> getDaftarPilot(PenerbanganModel penerbangan);
    List<PenerbanganModel> getDaftarPenerbangan (PilotModel pilot);
}

package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;
import java.util.List;
public interface PilotService {
    void addPilot(PilotModel pilot);
    List<PilotModel> getListPilot();
    List<PilotModel> getListPilotByMaskapaiAkademi(MaskapaiModel maskapai,AkademiModel akademi);
    List<PilotModel> getListPilotByMaskapaiJmlPenerbangan(MaskapaiModel maskapai, List<Integer> jumlah);
    List<PilotModel> getListPilotBulanIni(List<PilotModel> listPilot);
    PilotModel getPilotById(Long id);
    PilotModel getPilotByNip(String nip);
    PilotModel updatePilot(PilotModel pilot);
    void deletePilot(PilotModel pilot);
    String nipPilot(PilotModel pilot);


}

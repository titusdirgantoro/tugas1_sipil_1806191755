package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;
import java.util.List;
public interface MaskapaiService {
    void addMaskapai(MaskapaiModel maskapai);
    List<MaskapaiModel> getListMaskapai();
    MaskapaiModel getMaskapaiById(Long id);
    MaskapaiModel getMaskapaiByKode(String kode);
    MaskapaiModel getMaskapaiByNama(String nama);
    MaskapaiModel updateMaskapai(MaskapaiModel maskapai);
}

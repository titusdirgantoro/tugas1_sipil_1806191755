package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class MaskapaiServiceImpl implements MaskapaiService{
    @Autowired
    MaskapaiDb maskapaiDb;

    @Override
    public void addMaskapai(MaskapaiModel maskapai) {
        maskapaiDb.save(maskapai);
    }

    @Override
    public List<MaskapaiModel> getListMaskapai() {
        return maskapaiDb.findAll();
    }

    @Override
    public MaskapaiModel getMaskapaiById(Long id) {
        return maskapaiDb.findById(id).get();
    }

    @Override
    public MaskapaiModel getMaskapaiByKode(String kode) {

        try {
            return maskapaiDb.findByKode(kode).get();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    public MaskapaiModel getMaskapaiByNama(String nama) {
        return maskapaiDb.findByNama(nama).get();
    }

    @Override
    public MaskapaiModel updateMaskapai(MaskapaiModel maskapai) {
        return null;
    }

}

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
public class AkademiServiceImpl implements AkademiService {
    @Autowired
    AkademiDb akademiDb;

    @Override
    public List<AkademiModel> getListAkademi() {
        return akademiDb.findAll();
    }

    @Override
    public AkademiModel getAkademiById(Long id) {
        try {
            return akademiDb.findById(id).get();
        }catch (NoSuchElementException e){
            return null;
        }
    }


}

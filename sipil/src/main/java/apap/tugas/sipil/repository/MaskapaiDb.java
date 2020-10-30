package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaskapaiDb extends JpaRepository<MaskapaiModel,Long>{
    Optional<MaskapaiModel> findById(Long id);
    Optional<MaskapaiModel> findByNama(String nama);
    Optional<MaskapaiModel> findByKode(String kode);
}

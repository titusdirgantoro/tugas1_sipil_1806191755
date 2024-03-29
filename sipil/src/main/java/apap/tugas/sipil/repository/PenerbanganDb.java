package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenerbanganDb extends JpaRepository<PenerbanganModel,Long> {
    Optional<PenerbanganModel> findById(Long id);
    Optional<PenerbanganModel> findByKode(String kode);
}

package apap.tugas.sipil.repository;

import apap.tugas.sipil.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Pilot_PenerbanganDb extends JpaRepository<Pilot_PenerbanganModel,Long>{
    Optional<Pilot_PenerbanganModel> findById(Long id);
    Optional<Pilot_PenerbanganModel> findByPenerbanganAndPilot(PenerbanganModel penerbangan, PilotModel piilot);
    List<Pilot_PenerbanganModel> findAllByPenerbangan(PenerbanganModel penerbangan);
    List<Pilot_PenerbanganModel> findAllByPilot(PilotModel pilot);
}

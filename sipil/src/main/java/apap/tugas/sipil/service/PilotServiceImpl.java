package apap.tugas.sipil.service;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PilotServiceImpl implements PilotService {
    @Autowired
    PilotDb pilotDb;

    @Autowired
    private Pilot_PenerbanganService pilot_penerbanganService;

    @Autowired
    Pilot_PenerbanganDb pilot_penerbanganDb;

    @Override
    public void addPilot(PilotModel pilot) {
        pilotDb.save(pilot);
    }

    @Override
    public List<PilotModel> getListPilot() {
        return pilotDb.findAll();
    }

    @Override
    public List<PilotModel> getListPilotByMaskapaiAkademi(MaskapaiModel maskapai, AkademiModel akademi) {
        if (maskapai == null){
            if (akademi != null)
                return pilotDb.findAllByAkademi(akademi);
            else return null;
        }else if (akademi == null){
            return pilotDb.findAllByMaskapai(maskapai);
        }
        return pilotDb.findAllByMaskapaiAndAkademi(maskapai,akademi);
    }

    @Override
    public List<PilotModel> getListPilotByMaskapaiJmlPenerbangan(MaskapaiModel maskapai, List<Integer> jmlPenerbangan) {
        List<PilotModel> listPilot = pilotDb.findAllByMaskapai(maskapai);
        List<PilotModel> listPilotUrut = new ArrayList<PilotModel>();
        for (int i = 0; i <listPilot.size(); i++){
            PilotModel pilot = listPilot.get(i);
            int jml = pilot_penerbanganDb.findAllByPilot(pilot).size();
            if (jmlPenerbangan.size()==0){
                listPilotUrut.add(pilot);
                jmlPenerbangan.add(jml);
            }else {
                innerloop:
                for (int j = 0 ; j < listPilotUrut.size() ; j++){
                    if (jml < jmlPenerbangan.get(j)){
                        if (j == listPilotUrut.size()-1) {
                            jmlPenerbangan.add(jml);
                            listPilotUrut.add(pilot);
                            break innerloop;
                        }
                        continue;
                    }else if (jml > jmlPenerbangan.get(j)){
                        jmlPenerbangan.add(j,jml);
                        listPilotUrut.add(j,pilot);
                        break innerloop;
                    }else if (jml == jmlPenerbangan.get(j)){
                        jmlPenerbangan.add(jml);
                        listPilotUrut.add(pilot);
                        break innerloop;
                    }
                }
            }
        }
        return listPilotUrut;

    }

    @Override
    public List<PilotModel> getListPilotBulanIni(List<PilotModel> listPilot) {
        List<PilotModel> listPilotBulanIni = new ArrayList<>();
        for (PilotModel pilot : listPilot){
            List<PenerbanganModel> jadwalPilot = pilot_penerbanganService.getDaftarPenerbangan(pilot);
            innerloop:
            for (PenerbanganModel penerbangan : jadwalPilot){
                Date datePenerbangan = penerbangan.getWaktu();
                LocalDate datePenerbangan1 = datePenerbangan.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate now = LocalDate.now(); // 2015-11-24
                LocalDate earlier = now.minusMonths(1);
                if (datePenerbangan1.compareTo(earlier) > 0){
                    if (datePenerbangan1.compareTo(now) < 0){
                        listPilotBulanIni.add(pilot);
                        break innerloop;
                    }
                }
            }
        }
        return listPilotBulanIni;
    }


    @Override
    public PilotModel getPilotById(Long id) {
        return pilotDb.findById(id).get();
    }

    @Override
    public PilotModel getPilotByNip(String nip) {
        return pilotDb.findByNip(nip).get();
    }

    @Override
    public PilotModel updatePilot(PilotModel pilot) {
        PilotModel target = pilotDb.findById(pilot.getId()).get();
        try{
            target.setNama(pilot.getNama());
            target.setNik(pilot.getNik());
            target.setTempat_lahir(pilot.getTempat_lahir());
            target.setTanggal_lahir(pilot.getTanggal_lahir());
            target.setJenis_kelamin(pilot.getJenis_kelamin());
            target.setAkademi(pilot.getAkademi());
            target.setMaskapai(pilot.getMaskapai());
            pilotDb.save(target);
            return target;
        }
        catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public void deletePilot(PilotModel pilot) {
        pilotDb.delete(pilot);
    }

    @Override
    public String nipPilot(PilotModel pilot) {
        String nip = "";
        if (pilot.getJenis_kelamin() == 1) {
            nip +="1";
        }else{nip += "2";}
        nip += pilot.getTempat_lahir().substring(0,2).toUpperCase();
        nip += Character.toUpperCase(pilot.getNama().charAt(pilot.getNama().length()-1));
        Date date = pilot.getTanggal_lahir();
        DateFormat dateFormat = new SimpleDateFormat("ddMM");
        nip += dateFormat.format(date);
        dateFormat = new SimpleDateFormat("yyyy");
        int tahunLahir = Integer.parseInt(dateFormat.format(date).substring(0,4));
        tahunLahir =(int) Math.floor(tahunLahir/10);
        nip += String.valueOf(tahunLahir);
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random randomNumber = new Random();
        for (int i = 0; i < 2; i++) {
            //4
            int randomNo = randomNumber.nextInt(alphabet.length());

            //5
            Character character = alphabet.charAt(randomNo);

            //6
            nip += Character.toUpperCase(character);
        }
        return nip;

    }

}

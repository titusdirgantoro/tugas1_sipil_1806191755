package apap.tugas.sipil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pilot_penerbangan")
public class Pilot_PenerbanganModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pilot",referencedColumnName = "id",nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PilotModel pilot;

    @ManyToOne
    @JoinColumn(name = "id_penerbangan",referencedColumnName = "id",nullable = false)
    @JsonIgnore
    private PenerbanganModel penerbangan;

    @NotNull
    @Column(name="tanggal_penugasan", nullable = false)
    @DateTimeFormat(pattern = "dd-MMMM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date tanggal_penugasan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PilotModel getPilot() {
        return pilot;
    }

    public void setPilot(PilotModel pilot) {
        this.pilot = pilot;
    }

    public PenerbanganModel getPenerbangan() {
        return penerbangan;
    }

    public void setPenerbangan(PenerbanganModel penerbangan) {
        this.penerbangan = penerbangan;
    }

    public Date getTanggal_penugasan() {
        return tanggal_penugasan;
    }

    public void setTanggal_penugasan(Date tanggal_penugasan) {
        this.tanggal_penugasan = tanggal_penugasan;
    }
}

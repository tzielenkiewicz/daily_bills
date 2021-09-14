import java.time.LocalDate;

public class Bill {
    private  float dania;
    private LocalDate date;
    private float pieczywo;
    private float nabial;
    private float produktyZbo;
    private float mieso;
    private float wedlina;
    private float warzywaOwoce;
    private float slodycze;
    private float ryby;
    private float chemia;
    private float ubrania;
    private float zabawki;
    private float AGD;
    private float medycyna;
    private float lekarstwa;

    public Bill(LocalDate date, float pieczywo, float nabial, float produktyZbo, float mieso, float wedlina,
                float warzywaOwoce, float slodycze, float ryby, float dania, float chemia, float ubrania,
                float zabawki, float AGD, float medycyna, float lekarstwa) {
        this.date = date;
        this.pieczywo = pieczywo;
        this.nabial = nabial;
        this.produktyZbo = produktyZbo;
        this.mieso = mieso;
        this.wedlina = wedlina;
        this.warzywaOwoce = warzywaOwoce;
        this.slodycze = slodycze;
        this.ryby = ryby;
        this.dania = dania;
        this.chemia = chemia;
        this.ubrania = ubrania;
        this.zabawki = zabawki;
        this.AGD = AGD;
        this.medycyna = medycyna;
        this.lekarstwa = lekarstwa;
    }
    public Bill() {

    }

    public LocalDate getDate() {
        return date;
    }

    public float getPieczywo() {
        return pieczywo;
    }

    public float getNabial() {
        return nabial;
    }

    public float getProduktyZbo() {
        return produktyZbo;
    }

    public float getMieso() {
        return mieso;
    }

    public float getWedlina() {
        return wedlina;
    }

    public float getWarzywaOwoce() {
        return warzywaOwoce;
    }

    public float getSlodycze() {
        return slodycze;
    }

    public float getRyby() {
        return ryby;
    }

    public float getDania() {
        return dania;
    }

    public float getChemia() {
        return chemia;
    }

    public float getUbrania() {
        return ubrania;
    }

    public float getZabawki() {
        return zabawki;
    }

    public float getAGD() {
        return AGD;
    }
    public float getMedycyna() {
        return medycyna;
    }
    public float getLekarstwa() {
        return lekarstwa;
    }


}

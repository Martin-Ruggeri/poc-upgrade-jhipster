package com.pae.well.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Well.
 */
@Entity
@Table(name = "well")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Well implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * UWI is the Well identifier. It's the primary key in database
     */
    @NotNull
    @Column(name = "id_uwi", nullable = false)
    private Integer idUwi;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 100)
    @Column(name = "well_type", length = 100)
    private String wellType;

    /**
     * Modelo de bomba
     */
    @Size(max = 100)
    @Column(name = "pump_model", length = 100)
    private String pumpModel;

    @Size(max = 100)
    @Column(name = "manifold", length = 100)
    private String manifold;

    @Size(max = 100)
    @Column(name = "province", length = 100)
    private String province;

    /**
     * Yacimiento
     */
    @Size(max = 100)
    @Column(name = "deposit", length = 100)
    private String deposit;

    @Column(name = "campaign_year")
    private Integer campaignYear;

    /**
     * Fecha de puesta en marcha
     */
    @Column(name = "start_up_date")
    private LocalDate startUpDate;

    @Column(name = "is_injector")
    private Boolean isInjector;

    /**
     * Torque Vastago
     */
    @Column(name = "stem_torque")
    private Double stemTorque;

    /**
     * Bomba Constante
     */
    @Column(name = "pump_constant")
    private Double pumpConstant;

    /**
     * RPM Vastago
     */
    @Column(name = "stem_rpm")
    private Double stemRPM;

    @Column(name = "variator_frequency")
    private Double variatorFrequency;

    @Size(max = 500)
    @Column(name = "extraction_type", length = 500)
    private String extractionType;

    @Size(max = 500)
    @Column(name = "extraction_subtype", length = 500)
    private String extractionSubtype;

    @ManyToOne
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private SaltWaterInjectionPlant saltWaterInjectionPlant;

    @ManyToOne
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private PetroleumPlant petroleumPlant;

    @ManyToOne
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private GasPlant gasPlant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "managementUnit" }, allowSetters = true)
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "managementUnit" }, allowSetters = true)
    private Rig rig;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private Battery battery;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "managementUnit" }, allowSetters = true)
    private District district;

    @ManyToOne
    private WellStatus wellStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Well id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUwi() {
        return this.idUwi;
    }

    public Well idUwi(Integer idUwi) {
        this.setIdUwi(idUwi);
        return this;
    }

    public void setIdUwi(Integer idUwi) {
        this.idUwi = idUwi;
    }

    public String getName() {
        return this.name;
    }

    public Well name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWellType() {
        return this.wellType;
    }

    public Well wellType(String wellType) {
        this.setWellType(wellType);
        return this;
    }

    public void setWellType(String wellType) {
        this.wellType = wellType;
    }

    public String getPumpModel() {
        return this.pumpModel;
    }

    public Well pumpModel(String pumpModel) {
        this.setPumpModel(pumpModel);
        return this;
    }

    public void setPumpModel(String pumpModel) {
        this.pumpModel = pumpModel;
    }

    public String getManifold() {
        return this.manifold;
    }

    public Well manifold(String manifold) {
        this.setManifold(manifold);
        return this;
    }

    public void setManifold(String manifold) {
        this.manifold = manifold;
    }

    public String getProvince() {
        return this.province;
    }

    public Well province(String province) {
        this.setProvince(province);
        return this;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDeposit() {
        return this.deposit;
    }

    public Well deposit(String deposit) {
        this.setDeposit(deposit);
        return this;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public Integer getCampaignYear() {
        return this.campaignYear;
    }

    public Well campaignYear(Integer campaignYear) {
        this.setCampaignYear(campaignYear);
        return this;
    }

    public void setCampaignYear(Integer campaignYear) {
        this.campaignYear = campaignYear;
    }

    public LocalDate getStartUpDate() {
        return this.startUpDate;
    }

    public Well startUpDate(LocalDate startUpDate) {
        this.setStartUpDate(startUpDate);
        return this;
    }

    public void setStartUpDate(LocalDate startUpDate) {
        this.startUpDate = startUpDate;
    }

    public Boolean getIsInjector() {
        return this.isInjector;
    }

    public Well isInjector(Boolean isInjector) {
        this.setIsInjector(isInjector);
        return this;
    }

    public void setIsInjector(Boolean isInjector) {
        this.isInjector = isInjector;
    }

    public Double getStemTorque() {
        return this.stemTorque;
    }

    public Well stemTorque(Double stemTorque) {
        this.setStemTorque(stemTorque);
        return this;
    }

    public void setStemTorque(Double stemTorque) {
        this.stemTorque = stemTorque;
    }

    public Double getPumpConstant() {
        return this.pumpConstant;
    }

    public Well pumpConstant(Double pumpConstant) {
        this.setPumpConstant(pumpConstant);
        return this;
    }

    public void setPumpConstant(Double pumpConstant) {
        this.pumpConstant = pumpConstant;
    }

    public Double getStemRPM() {
        return this.stemRPM;
    }

    public Well stemRPM(Double stemRPM) {
        this.setStemRPM(stemRPM);
        return this;
    }

    public void setStemRPM(Double stemRPM) {
        this.stemRPM = stemRPM;
    }

    public Double getVariatorFrequency() {
        return this.variatorFrequency;
    }

    public Well variatorFrequency(Double variatorFrequency) {
        this.setVariatorFrequency(variatorFrequency);
        return this;
    }

    public void setVariatorFrequency(Double variatorFrequency) {
        this.variatorFrequency = variatorFrequency;
    }

    public String getExtractionType() {
        return this.extractionType;
    }

    public Well extractionType(String extractionType) {
        this.setExtractionType(extractionType);
        return this;
    }

    public void setExtractionType(String extractionType) {
        this.extractionType = extractionType;
    }

    public String getExtractionSubtype() {
        return this.extractionSubtype;
    }

    public Well extractionSubtype(String extractionSubtype) {
        this.setExtractionSubtype(extractionSubtype);
        return this;
    }

    public void setExtractionSubtype(String extractionSubtype) {
        this.extractionSubtype = extractionSubtype;
    }

    public SaltWaterInjectionPlant getSaltWaterInjectionPlant() {
        return this.saltWaterInjectionPlant;
    }

    public void setSaltWaterInjectionPlant(SaltWaterInjectionPlant saltWaterInjectionPlant) {
        this.saltWaterInjectionPlant = saltWaterInjectionPlant;
    }

    public Well saltWaterInjectionPlant(SaltWaterInjectionPlant saltWaterInjectionPlant) {
        this.setSaltWaterInjectionPlant(saltWaterInjectionPlant);
        return this;
    }

    public PetroleumPlant getPetroleumPlant() {
        return this.petroleumPlant;
    }

    public void setPetroleumPlant(PetroleumPlant petroleumPlant) {
        this.petroleumPlant = petroleumPlant;
    }

    public Well petroleumPlant(PetroleumPlant petroleumPlant) {
        this.setPetroleumPlant(petroleumPlant);
        return this;
    }

    public GasPlant getGasPlant() {
        return this.gasPlant;
    }

    public void setGasPlant(GasPlant gasPlant) {
        this.gasPlant = gasPlant;
    }

    public Well gasPlant(GasPlant gasPlant) {
        this.setGasPlant(gasPlant);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Well project(Project project) {
        this.setProject(project);
        return this;
    }

    public Rig getRig() {
        return this.rig;
    }

    public void setRig(Rig rig) {
        this.rig = rig;
    }

    public Well rig(Rig rig) {
        this.setRig(rig);
        return this;
    }

    public Battery getBattery() {
        return this.battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Well battery(Battery battery) {
        this.setBattery(battery);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Well district(District district) {
        this.setDistrict(district);
        return this;
    }

    public WellStatus getWellStatus() {
        return this.wellStatus;
    }

    public void setWellStatus(WellStatus wellStatus) {
        this.wellStatus = wellStatus;
    }

    public Well wellStatus(WellStatus wellStatus) {
        this.setWellStatus(wellStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Well)) {
            return false;
        }
        return id != null && id.equals(((Well) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Well{" +
            "id=" + getId() +
            ", idUwi=" + getIdUwi() +
            ", name='" + getName() + "'" +
            ", wellType='" + getWellType() + "'" +
            ", pumpModel='" + getPumpModel() + "'" +
            ", manifold='" + getManifold() + "'" +
            ", province='" + getProvince() + "'" +
            ", deposit='" + getDeposit() + "'" +
            ", campaignYear=" + getCampaignYear() +
            ", startUpDate='" + getStartUpDate() + "'" +
            ", isInjector='" + getIsInjector() + "'" +
            ", stemTorque=" + getStemTorque() +
            ", pumpConstant=" + getPumpConstant() +
            ", stemRPM=" + getStemRPM() +
            ", variatorFrequency=" + getVariatorFrequency() +
            ", extractionType='" + getExtractionType() + "'" +
            ", extractionSubtype='" + getExtractionSubtype() + "'" +
            "}";
    }
}

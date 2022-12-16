package com.pae.well.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.pae.well.domain.Well} entity.
 */
public class WellDTO implements Serializable {

    private Long id;

    /**
     * UWI is the Well identifier. It's the primary key in database
     */
    @NotNull
    @Schema(description = "UWI is the Well identifier. It's the primary key in database", required = true)
    private Integer idUwi;

    @Size(max = 50)
    private String name;

    @Size(max = 100)
    private String wellType;

    /**
     * Modelo de bomba
     */
    @Size(max = 100)
    @Schema(description = "Modelo de bomba")
    private String pumpModel;

    @Size(max = 100)
    private String manifold;

    @Size(max = 100)
    private String province;

    /**
     * Yacimiento
     */
    @Size(max = 100)
    @Schema(description = "Yacimiento")
    private String deposit;

    private Integer campaignYear;

    /**
     * Fecha de puesta en marcha
     */
    @Schema(description = "Fecha de puesta en marcha")
    private LocalDate startUpDate;

    private Boolean isInjector;

    /**
     * Torque Vastago
     */
    @Schema(description = "Torque Vastago")
    private Double stemTorque;

    /**
     * Bomba Constante
     */
    @Schema(description = "Bomba Constante")
    private Double pumpConstant;

    /**
     * RPM Vastago
     */
    @Schema(description = "RPM Vastago")
    private Double stemRPM;

    private Double variatorFrequency;

    @Size(max = 500)
    private String extractionType;

    @Size(max = 500)
    private String extractionSubtype;

    private SaltWaterInjectionPlantDTO saltWaterInjectionPlant;

    private PetroleumPlantDTO petroleumPlant;

    private GasPlantDTO gasPlant;

    private ProjectDTO project;

    private RigDTO rig;

    private BatteryDTO battery;

    private DistrictDTO district;

    private WellStatusDTO wellStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUwi() {
        return idUwi;
    }

    public void setIdUwi(Integer idUwi) {
        this.idUwi = idUwi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWellType() {
        return wellType;
    }

    public void setWellType(String wellType) {
        this.wellType = wellType;
    }

    public String getPumpModel() {
        return pumpModel;
    }

    public void setPumpModel(String pumpModel) {
        this.pumpModel = pumpModel;
    }

    public String getManifold() {
        return manifold;
    }

    public void setManifold(String manifold) {
        this.manifold = manifold;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public Integer getCampaignYear() {
        return campaignYear;
    }

    public void setCampaignYear(Integer campaignYear) {
        this.campaignYear = campaignYear;
    }

    public LocalDate getStartUpDate() {
        return startUpDate;
    }

    public void setStartUpDate(LocalDate startUpDate) {
        this.startUpDate = startUpDate;
    }

    public Boolean getIsInjector() {
        return isInjector;
    }

    public void setIsInjector(Boolean isInjector) {
        this.isInjector = isInjector;
    }

    public Double getStemTorque() {
        return stemTorque;
    }

    public void setStemTorque(Double stemTorque) {
        this.stemTorque = stemTorque;
    }

    public Double getPumpConstant() {
        return pumpConstant;
    }

    public void setPumpConstant(Double pumpConstant) {
        this.pumpConstant = pumpConstant;
    }

    public Double getStemRPM() {
        return stemRPM;
    }

    public void setStemRPM(Double stemRPM) {
        this.stemRPM = stemRPM;
    }

    public Double getVariatorFrequency() {
        return variatorFrequency;
    }

    public void setVariatorFrequency(Double variatorFrequency) {
        this.variatorFrequency = variatorFrequency;
    }

    public String getExtractionType() {
        return extractionType;
    }

    public void setExtractionType(String extractionType) {
        this.extractionType = extractionType;
    }

    public String getExtractionSubtype() {
        return extractionSubtype;
    }

    public void setExtractionSubtype(String extractionSubtype) {
        this.extractionSubtype = extractionSubtype;
    }

    public SaltWaterInjectionPlantDTO getSaltWaterInjectionPlant() {
        return saltWaterInjectionPlant;
    }

    public void setSaltWaterInjectionPlant(SaltWaterInjectionPlantDTO saltWaterInjectionPlant) {
        this.saltWaterInjectionPlant = saltWaterInjectionPlant;
    }

    public PetroleumPlantDTO getPetroleumPlant() {
        return petroleumPlant;
    }

    public void setPetroleumPlant(PetroleumPlantDTO petroleumPlant) {
        this.petroleumPlant = petroleumPlant;
    }

    public GasPlantDTO getGasPlant() {
        return gasPlant;
    }

    public void setGasPlant(GasPlantDTO gasPlant) {
        this.gasPlant = gasPlant;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public RigDTO getRig() {
        return rig;
    }

    public void setRig(RigDTO rig) {
        this.rig = rig;
    }

    public BatteryDTO getBattery() {
        return battery;
    }

    public void setBattery(BatteryDTO battery) {
        this.battery = battery;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public WellStatusDTO getWellStatus() {
        return wellStatus;
    }

    public void setWellStatus(WellStatusDTO wellStatus) {
        this.wellStatus = wellStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WellDTO)) {
            return false;
        }

        WellDTO wellDTO = (WellDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wellDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WellDTO{" +
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
            ", saltWaterInjectionPlant=" + getSaltWaterInjectionPlant() +
            ", petroleumPlant=" + getPetroleumPlant() +
            ", gasPlant=" + getGasPlant() +
            ", project=" + getProject() +
            ", rig=" + getRig() +
            ", battery=" + getBattery() +
            ", district=" + getDistrict() +
            ", wellStatus=" + getWellStatus() +
            "}";
    }
}

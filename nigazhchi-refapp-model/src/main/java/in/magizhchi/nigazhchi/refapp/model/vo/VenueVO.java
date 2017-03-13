package in.magizhchi.nigazhchi.refapp.model.vo;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VenueVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7666360893257208691L;

  @JsonProperty
  private String venueCode;

  @JsonProperty
  private String venueName;

  @JsonProperty
  private Set<String> venueAmenities;

  @JsonProperty
  private Set<String> venueFacilities;

  @JsonProperty
  private Set<String> venueRules;

  public String getVenueCode() {
    return venueCode;
  }

  public void setVenueCode(String venueCode) {
    this.venueCode = venueCode;
  }

  public String getVenueName() {
    return venueName;
  }

  public void setVenueName(String venueName) {
    this.venueName = venueName;
  }

  public Set<String> getVenueAmenities() {
    return venueAmenities;
  }

  public void setVenueAmenities(Set<String> venueAmenities) {
    this.venueAmenities = venueAmenities;
  }

  public Set<String> getVenueFacilities() {
    return venueFacilities;
  }

  public void setVenueFacilities(Set<String> venueFacilities) {
    this.venueFacilities = venueFacilities;
  }

  public Set<String> getVenueRules() {
    return venueRules;
  }

  public void setVenueRules(Set<String> venueRules) {
    this.venueRules = venueRules;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((venueCode == null) ? 0 : venueCode.hashCode());
    result = prime * result + ((venueName == null) ? 0 : venueName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    VenueVO other = (VenueVO) obj;
    if (venueCode == null) {
      if (other.venueCode != null) return false;
    } else if (!venueCode.equals(other.venueCode)) return false;
    if (venueName == null) {
      if (other.venueName != null) return false;
    } else if (!venueName.equals(other.venueName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "VenueVO [venueCode=" + venueCode + ", venueName=" + venueName + "]";
  }

  public VenueVO() {
    super();
    // TODO Auto-generated constructor stub
  }

  public VenueVO(String venueCode, String venueName) {
    super();
    this.venueCode = venueCode;
    this.venueName = venueName;
  }



}

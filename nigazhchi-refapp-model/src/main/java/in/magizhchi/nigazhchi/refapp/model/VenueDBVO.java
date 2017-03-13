package in.magizhchi.nigazhchi.refapp.model;

import in.magizhchi.nigazhchi.refapp.persistence.MagizhchiMongoModel;

import java.io.Serializable;
import java.util.Set;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VenueDBVO extends MagizhchiMongoModel implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1137858819732111334L;
  
  private static final String COLLECTION_NAME = "NIGAZHCHI_VENUES";

  @MongoId
  @MongoObjectId
  private ObjectId venueId;

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

  public VenueDBVO() {
    super();
  }

  public VenueDBVO(String venueCode, String venueName) {
    super();
    this.venueCode = venueCode;
    this.venueName = venueName;
  }

  public VenueDBVO(VenueDBVOBuilder venueDBVOBuilder) {
   this.venueCode = venueDBVOBuilder.venueCode;
   this.venueName = venueDBVOBuilder.venueName;
   this.venueFacilities = venueDBVOBuilder.venueFacilities;
   this.venueRules = venueDBVOBuilder.venueRules;
   this.venueAmenities = venueDBVOBuilder.venueAmenities;
  }

  @Override
  public String toString() {
    return "Venue [venueCode=" + venueCode + ", venueName=" + venueName + "]";
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
    VenueDBVO other = (VenueDBVO) obj;
    if (venueCode == null) {
      if (other.venueCode != null) return false;
    } else if (!venueCode.equals(other.venueCode)) return false;
    if (venueName == null) {
      if (other.venueName != null) return false;
    } else if (!venueName.equals(other.venueName)) return false;
    return true;
  }

  public ObjectId getVenueId() {
    return venueId;
  }

  public String getVenueCode() {
    return venueCode;
  }

  public String getVenueName() {
    return venueName;
  }
  
  public Set<String> getVenueAmenities() {
    return venueAmenities;
  }

  public Set<String> getVenueFacilities() {
    return venueFacilities;
  }

  public Set<String> getVenueRules() {
    return venueRules;
  }
  
  public static class VenueDBVOBuilder {

    private String venueCode;

    private String venueName;
    
    private Set<String> venueAmenities;
    
    private Set<String> venueFacilities;
    
    private Set<String> venueRules;

    public VenueDBVOBuilder(){
      
    }
    
    public VenueDBVOBuilder setVenueCode(String venueCode){
      this.venueCode = venueCode;
      return this;
    }
    
    public VenueDBVOBuilder setVenueName(String venueName){
      this.venueName = venueName;
      return this;
    }
    
    public VenueDBVOBuilder setVenueAmenities(Set<String> venueAmenities){
      this.venueAmenities = venueAmenities;
      return this;
    }
    
    public VenueDBVOBuilder setVenueFacilities(Set<String> venueFacilities){
      this.venueFacilities = venueFacilities;
      return this;
    }
    
    public VenueDBVOBuilder setVenueRules(Set<String> venueRules){
      this.venueRules = venueRules;
      return this;
    }
    
    public VenueDBVO buildVenueDBVO(){
      return new VenueDBVO(this);
    }
    
  }

  @Override
  public String getCollectionName() {
    return COLLECTION_NAME;
  }

}

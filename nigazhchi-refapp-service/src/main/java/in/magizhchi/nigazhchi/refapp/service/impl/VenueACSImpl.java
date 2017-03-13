package in.magizhchi.nigazhchi.refapp.service.impl;

import in.magizhchi.nigazhchi.refapp.model.VenueDBVO;
import in.magizhchi.nigazhchi.refapp.model.vo.VenueVO;
import in.magizhchi.nigazhchi.refapp.persistence.PersistenceManager;
import in.magizhchi.nigazhchi.refapp.persistence.PersistenceService;
import in.magizhchi.nigazhchi.refapp.service.intf.VenueACS;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

@Service
@Component(immediate = true, enabled = true)
public class VenueACSImpl implements VenueACS {

  private PersistenceManager<VenueDBVO> persistenceManager;

  @Override
  public VenueDBVO createVenueDBVO(VenueVO venueVO) {

    VenueDBVO.VenueDBVOBuilder builder = new VenueDBVO.VenueDBVOBuilder();

    builder.setVenueCode(venueVO.getVenueCode());

    builder.setVenueName(venueVO.getVenueName());

    builder.setVenueFacilities(venueVO.getVenueFacilities());

    builder.setVenueAmenities(venueVO.getVenueAmenities());

    builder.setVenueRules(venueVO.getVenueRules());

    VenueDBVO venueDBVO = builder.buildVenueDBVO();

    PersistenceService<VenueDBVO> persistenceService = persistenceManager.getPersistenceService(VenueDBVO.class);

    VenueDBVO managedVenueDBVO = persistenceService.create(venueDBVO);

    return managedVenueDBVO;

  }

  @Override
  public VenueDBVO getVenueDBVO(String venueCode) {
    // TODO Auto-generated method stub
    return null;
  }



}

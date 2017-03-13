package in.magizhchi.nigazhchi.refapp.service.intf;

import in.magizhchi.nigazhchi.refapp.model.VenueDBVO;
import in.magizhchi.nigazhchi.refapp.model.vo.VenueVO;


public interface VenueACS {

  VenueDBVO createVenueDBVO(VenueVO venueVO);
  
  VenueDBVO getVenueDBVO(String venueCode);

  
}

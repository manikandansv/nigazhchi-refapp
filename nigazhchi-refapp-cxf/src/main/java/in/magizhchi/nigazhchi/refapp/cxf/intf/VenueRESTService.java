package in.magizhchi.nigazhchi.refapp.cxf.intf;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import in.magizhchi.nigazhchi.refapp.model.vo.VenueVO;

@Path("venue")
public interface VenueRESTService {

  @POST
  @Path("create")
  void createVenue(String venueInfo);

  VenueVO getVenue(String venueCode);

}
